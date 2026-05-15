import { NextRequest, NextResponse } from "next/server";
import { TopicSide } from "@prisma/client";
import { prisma } from "@/lib/prisma";
import { getTopicList } from "@/lib/db/topics";
import { createUniqueSlug } from "@/lib/utils/slug";

export async function GET(request: NextRequest) {
    const searchParams = request.nextUrl.searchParams;

    const category = searchParams.get("category") ?? undefined;
    const sort = searchParams.get("sort") ?? "latest";
    const page = Number(searchParams.get("page") ?? 1);
    const size = Number(searchParams.get("size") ?? 10);

    const result = await getTopicList({
        category,
        sort,
        page,
        size,
    });

    return NextResponse.json(result);
}

export async function POST(request: NextRequest) {
    try {
        const body = await request.json();

        const category = String(body.category ?? "").trim();
        const title = String(body.title ?? "").trim();
        const description = String(body.description ?? "").trim();
        const writer = String(body.writer ?? "익명").trim() || "익명";
        const optionATitle = String(body.optionA?.title ?? "").trim();
        const optionBTitle = String(body.optionB?.title ?? "").trim();
        const optionAImage = body.optionA?.image
            ? String(body.optionA.image).trim()
            : null;
        const optionBImage = body.optionB?.image
            ? String(body.optionB.image).trim()
            : null;

        if (!category) {
            return NextResponse.json(
                { message: "카테고리를 선택해주세요." },
                { status: 400 },
            );
        }

        if (!title) {
            return NextResponse.json(
                { message: "제목을 입력해주세요." },
                { status: 400 },
            );
        }

        if (!description) {
            return NextResponse.json(
                { message: "설명을 입력해주세요." },
                { status: 400 },
            );
        }

        if (!optionATitle || !optionBTitle) {
            return NextResponse.json(
                { message: "A/B 선택지를 모두 입력해주세요." },
                { status: 400 },
            );
        }

        const categoryRow = await prisma.category.findUnique({
            where: { key: category },
        });

        if (!categoryRow) {
            return NextResponse.json(
                { message: "존재하지 않는 카테고리입니다." },
                { status: 400 },
            );
        }

        const slug = await createUniqueSlug(title, async (candidate) => {
            const exists = await prisma.topic.findUnique({
                where: { slug: candidate },
                select: { id: true },
            });

            return Boolean(exists);
        });

        const topic = await prisma.topic.create({
            data: {
                slug,
                categoryId: categoryRow.id,
                title,
                description,
                writer,
                options: {
                    create: [
                        {
                            side: TopicSide.A,
                            title: optionATitle,
                            imageUrl: optionAImage,
                        },
                        {
                            side: TopicSide.B,
                            title: optionBTitle,
                            imageUrl: optionBImage,
                        },
                    ],
                },
            },
            include: {
                category: true,
                options: {
                    orderBy: { side: "asc" },
                },
            },
        });

        return NextResponse.json(
            {
                topic: {
                    id: topic.id,
                    slug: topic.slug,
                    category: topic.category.key,
                    title: topic.title,
                    description: topic.description,
                    writer: topic.writer,
                    createdAt: topic.createdAt,
                    optionA: topic.options.find((option) => option.side === TopicSide.A),
                    optionB: topic.options.find((option) => option.side === TopicSide.B),
                },
            },
            { status: 201 },
        );
    } catch (error) {
        console.error(error);

        return NextResponse.json(
            { message: "토론 주제 등록 중 오류가 발생했습니다." },
            { status: 500 },
        );
    }
}