import { PrismaClient, TopicSide } from "@prisma/client";

const prisma = new PrismaClient();

async function main() {
    await prisma.vote.deleteMany();
    await prisma.comment.deleteMany();
    await prisma.analysis.deleteMany();
    await prisma.topicOption.deleteMany();
    await prisma.topic.deleteMany();
    await prisma.category.deleteMany();

    const categories = await prisma.category.createMany({
        data: [
            { key: "sports", name: "스포츠", sortOrder: 1 },
            { key: "game", name: "게임", sortOrder: 2 },
            { key: "culture", name: "문화생활", sortOrder: 3 },
            { key: "hobby", name: "취미", sortOrder: 4 },
            { key: "entertainment", name: "인방", sortOrder: 5 },
        ],
    });

    console.log("created categories:", categories.count);

    const game = await prisma.category.findUniqueOrThrow({
        where: { key: "game" },
    });

    const sports = await prisma.category.findUniqueOrThrow({
        where: { key: "sports" },
    });

    const culture = await prisma.category.findUniqueOrThrow({
        where: { key: "culture" },
    });

    const topic1 = await prisma.topic.create({
        data: {
            slug: "iphone-vs-galaxy",
            categoryId: game.id,
            title: "아이폰 vs 갤럭시",
            description: "일상 사용성과 만족도를 기준으로 어떤 스마트폰을 더 선호하시나요?",
            writer: "관리자",
            viewCount: 1240,
            options: {
                create: [
                    { side: TopicSide.A, title: "아이폰" },
                    { side: TopicSide.B, title: "갤럭시" },
                ],
            },
            comments: {
                create: [
                    {
                        writer: "민수",
                        content: "연동성 때문에 아이폰이 편하다고 생각함.",
                        side: TopicSide.A,
                    },
                    {
                        writer: "지훈",
                        content: "가격이랑 자유도 생각하면 갤럭시가 더 낫지 않나.",
                        side: TopicSide.B,
                    },
                ],
            },
            analyses: {
                create: [
                    {
                        title: "왜 아이폰 선호가 꾸준한가",
                        writer: "에디터A",
                        summary: "생태계와 UX 일관성 관점에서 보는 분석",
                        content:
                            "아이폰은 기기 간 연동성과 일관된 사용자 경험에서 강점을 보인다. 특히 맥북, 아이패드, 애플워치를 함께 쓰는 사용자에게는 전환 비용이 높아 장기적인 선호로 이어지는 경향이 있다.",
                    },
                ],
            },
        },
    });

    const topic2 = await prisma.topic.create({
        data: {
            slug: "messi-vs-ronaldo",
            categoryId: sports.id,
            title: "메시 vs 호날두",
            description: "축구 역사상 더 위대한 선수는 누구라고 생각하시나요?",
            writer: "관리자",
            viewCount: 2310,
            options: {
                create: [
                    { side: TopicSide.A, title: "메시" },
                    { side: TopicSide.B, title: "호날두" },
                ],
            },
            comments: {
                create: [
                    {
                        writer: "축구팬",
                        content: "월드컵 우승 이후로는 메시 쪽으로 기운 듯.",
                        side: TopicSide.A,
                    },
                ],
            },
            analyses: {
                create: [
                    {
                        title: "기록과 임팩트로 보는 두 선수",
                        writer: "에디터B",
                        summary: "개인 기록, 우승 커리어, 플레이 스타일 비교",
                        content:
                            "메시와 호날두는 서로 다른 방식으로 축구의 기준을 바꿨다. 메시는 경기 영향력과 창의성에서, 호날두는 득점력과 자기관리에서 압도적인 평가를 받는다.",
                    },
                ],
            },
        },
    });

    const topic3 = await prisma.topic.create({
        data: {
            slug: "movie-theater-vs-ott",
            categoryId: culture.id,
            title: "영화관 vs OTT",
            description: "영화를 볼 때 영화관과 OTT 중 어느 쪽을 더 선호하시나요?",
            writer: "관리자",
            viewCount: 820,
            options: {
                create: [
                    { side: TopicSide.A, title: "영화관" },
                    { side: TopicSide.B, title: "OTT" },
                ],
            },
        },
    });

    await prisma.vote.createMany({
        data: [
            { topicId: topic1.id, side: TopicSide.A, voterKey: "seed-1" },
            { topicId: topic1.id, side: TopicSide.A, voterKey: "seed-2" },
            { topicId: topic1.id, side: TopicSide.B, voterKey: "seed-3" },
            { topicId: topic2.id, side: TopicSide.A, voterKey: "seed-4" },
            { topicId: topic2.id, side: TopicSide.B, voterKey: "seed-5" },
            { topicId: topic2.id, side: TopicSide.A, voterKey: "seed-6" },
            { topicId: topic3.id, side: TopicSide.B, voterKey: "seed-7" },
        ],
    });

    console.log("seed done");
}

main()
    .catch((error) => {
        console.error(error);
        process.exit(1);
    })
    .finally(async () => {
        await prisma.$disconnect();
    });