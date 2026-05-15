import { TopicSide } from "@prisma/client";
import { prisma } from "@/lib/prisma";

export async function getCategories() {
    return prisma.category.findMany({
        orderBy: [{ sortOrder: "asc" }, { id: "asc" }],
    });
}

export async function getTopicList(params?: {
    category?: string;
    sort?: string;
    page?: number;
    size?: number;
}) {
    const page = params?.page && params.page > 0 ? params.page : 1;
    const size = params?.size && params.size > 0 ? params.size : 10;
    const skip = (page - 1) * size;

    const where = params?.category
        ? {
            category: {
                key: params.category,
            },
        }
        : {};

    const orderBy =
        params?.sort === "votes"
            ? [{ votes: { _count: "desc" as const } }, { createdAt: "desc" as const }]
            : params?.sort === "popular"
                ? [{ viewCount: "desc" as const }, { createdAt: "desc" as const }]
                : [{ createdAt: "desc" as const }];

    const [topics, totalCount] = await Promise.all([
        prisma.topic.findMany({
            where,
            skip,
            take: size,
            orderBy,
            include: {
                category: true,
                options: {
                    orderBy: { side: "asc" },
                },
                _count: {
                    select: {
                        votes: true,
                        comments: true,
                        analyses: true,
                    },
                },
            },
        }),
        prisma.topic.count({ where }),
    ]);

    const topicIds = topics.map((topic) => topic.id);

    const voteGroups =
        topicIds.length > 0
            ? await prisma.vote.groupBy({
                by: ["topicId", "side"],
                where: {
                    topicId: {
                        in: topicIds,
                    },
                },
                _count: {
                    _all: true,
                },
            })
            : [];

    const voteCountMap = new Map<string, number>();

    voteGroups.forEach((group) => {
        voteCountMap.set(`${group.topicId}-${group.side}`, group._count._all);
    });

    return {
        topics: topics.map((topic) => mapTopicSummary(topic, voteCountMap)),
        page,
        size,
        totalCount,
        totalPages: Math.ceil(totalCount / size),
    };
}

export async function getTrendingTopics(limit = 3) {
    const topics = await prisma.topic.findMany({
        take: limit,
        orderBy: [{ votes: { _count: "desc" } }, { viewCount: "desc" }],
        include: {
            category: true,
            options: {
                orderBy: { side: "asc" },
            },
            _count: {
                select: {
                    votes: true,
                    comments: true,
                    analyses: true,
                },
            },
        },
    });

    const topicIds = topics.map((topic) => topic.id);

    const voteGroups =
        topicIds.length > 0
            ? await prisma.vote.groupBy({
                by: ["topicId", "side"],
                where: {
                    topicId: {
                        in: topicIds,
                    },
                },
                _count: {
                    _all: true,
                },
            })
            : [];

    const voteCountMap = new Map<string, number>();

    voteGroups.forEach((group) => {
        voteCountMap.set(`${group.topicId}-${group.side}`, group._count._all);
    });

    return topics.map((topic) => mapTopicSummary(topic, voteCountMap));
}

export async function getTopicBySlug(slug: string, voterKey?: string) {
    const topic = await prisma.topic.findUnique({
        where: { slug },
        include: {
            category: true,
            options: {
                orderBy: { side: "asc" },
            },
            votes: voterKey
                ? {
                    where: { voterKey },
                    take: 1,
                }
                : false,
            _count: {
                select: {
                    votes: true,
                    comments: true,
                    analyses: true,
                },
            },
        },
    });

    if (!topic) {
        return null;
    }

    const optionA = topic.options.find((option) => option.side === TopicSide.A);
    const optionB = topic.options.find((option) => option.side === TopicSide.B);

    const [voteA, voteB] = await Promise.all([
        prisma.vote.count({
            where: {
                topicId: topic.id,
                side: TopicSide.A,
            },
        }),
        prisma.vote.count({
            where: {
                topicId: topic.id,
                side: TopicSide.B,
            },
        }),
    ]);

    return {
        id: topic.id,
        slug: topic.slug,
        category: topic.category.key,
        categoryName: topic.category.name,
        title: topic.title,
        description: topic.description,
        writer: topic.writer,
        createdAt: topic.createdAt,
        viewCount: topic.viewCount,
        optionA: {
            id: "A",
            title: optionA?.title ?? "",
            imageUrl: optionA?.imageUrl ?? null,
            voteCount: voteA,
        },
        optionB: {
            id: "B",
            title: optionB?.title ?? "",
            imageUrl: optionB?.imageUrl ?? null,
            voteCount: voteB,
        },
        totalVotes: voteA + voteB,
        commentCount: topic._count.comments,
        analysisCount: topic._count.analyses,
        myVote: topic.votes?.[0]?.side ?? null,
    };
}

export async function increaseTopicViewCount(topicId: number) {
    return prisma.topic.update({
        where: { id: topicId },
        data: {
            viewCount: {
                increment: 1,
            },
        },
    });
}

function mapTopicSummary(
    topic: {
        id: number;
        slug: string;
        title: string;
        description: string;
        writer: string;
        createdAt: Date;
        viewCount: number;
        category: {
            key: string;
            name: string;
        };
        options: {
            side: TopicSide;
            title: string;
            imageUrl: string | null;
        }[];
        _count: {
            votes: number;
            comments: number;
            analyses: number;
        };
    },
    voteCountMap: Map<string, number>,
) {
    const optionA = topic.options.find((option) => option.side === TopicSide.A);
    const optionB = topic.options.find((option) => option.side === TopicSide.B);

    const voteA = voteCountMap.get(`${topic.id}-${TopicSide.A}`) ?? 0;
    const voteB = voteCountMap.get(`${topic.id}-${TopicSide.B}`) ?? 0;

    return {
        id: topic.id,
        slug: topic.slug,
        category: topic.category.key,
        categoryName: topic.category.name,
        title: topic.title,
        description: topic.description,
        writer: topic.writer,
        createdAt: topic.createdAt,
        viewCount: topic.viewCount,
        optionA: {
            id: "A",
            title: optionA?.title ?? "",
            imageUrl: optionA?.imageUrl ?? null,
            voteCount: voteA,
        },
        optionB: {
            id: "B",
            title: optionB?.title ?? "",
            imageUrl: optionB?.imageUrl ?? null,
            voteCount: voteB,
        },
        totalVotes: voteA + voteB,
        commentCount: topic._count.comments,
        analysisCount: topic._count.analyses,
    };
}