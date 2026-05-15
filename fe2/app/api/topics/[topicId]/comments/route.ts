import { NextRequest, NextResponse } from "next/server";
import { TopicSide } from "@prisma/client";
import { prisma } from "@/lib/prisma";

type RouteParams = {
    params: Promise<{
        topicId: string;
    }>;
};

export async function GET(request: NextRequest, { params }: RouteParams) {
    const { topicId } = await params;
    const numericTopicId = Number(topicId);

    if (!Number.isInteger(numericTopicId)) {
        return NextResponse.json(
            { message: "잘못된 토론 ID입니다." },
            { status: 400 },
        );
    }

    const page = Number(request.nextUrl.searchParams.get("page") ?? 1);
    const size = Number(request.nextUrl.searchParams.get("size") ?? 20);
    const skip = (page - 1) * size;

    const [comments, totalCount] = await Promise.all([
        prisma.comment.findMany({
            where: {
                topicId: numericTopicId,
            },
            skip,
            take: size,
            orderBy: {
                createdAt: "desc",
            },
        }),
        prisma.comment.count({
            where: {
                topicId: numericTopicId,
            },
        }),
    ]);

    return NextResponse.json({
        comments: comments.map((comment) => ({
            id: comment.id,
            topicId: comment.topicId,
            writer: comment.writer,
            content: comment.content,
            side: comment.side,
            createdAt: comment.createdAt,
        })),
        page,
        size,
        totalCount,
        totalPages: Math.ceil(totalCount / size),
    });
}

export async function POST(request: NextRequest, { params }: RouteParams) {
    try {
        const { topicId } = await params;
        const numericTopicId = Number(topicId);

        if (!Number.isInteger(numericTopicId)) {
            return NextResponse.json(
                { message: "잘못된 토론 ID입니다." },
                { status: 400 },
            );
        }

        const body = await request.json();

        const writer = String(body.writer ?? "익명").trim() || "익명";
        const content = String(body.content ?? "").trim();
        const side = body.side ? String(body.side) : null;

        if (!content) {
            return NextResponse.json(
                { message: "댓글 내용을 입력해주세요." },
                { status: 400 },
            );
        }

        if (side && side !== TopicSide.A && side !== TopicSide.B) {
            return NextResponse.json(
                { message: "댓글 선택지는 A 또는 B만 가능합니다." },
                { status: 400 },
            );
        }

        const topic = await prisma.topic.findUnique({
            where: { id: numericTopicId },
            select: { id: true },
        });

        if (!topic) {
            return NextResponse.json(
                { message: "토론 주제를 찾을 수 없습니다." },
                { status: 404 },
            );
        }

        const comment = await prisma.comment.create({
            data: {
                topicId: numericTopicId,
                writer,
                content,
                side: side as TopicSide | null,
            },
        });

        return NextResponse.json(
            {
                comment: {
                    id: comment.id,
                    topicId: comment.topicId,
                    writer: comment.writer,
                    content: comment.content,
                    side: comment.side,
                    createdAt: comment.createdAt,
                },
            },
            { status: 201 },
        );
    } catch (error) {
        console.error(error);

        return NextResponse.json(
            { message: "댓글 등록 중 오류가 발생했습니다." },
            { status: 500 },
        );
    }
}