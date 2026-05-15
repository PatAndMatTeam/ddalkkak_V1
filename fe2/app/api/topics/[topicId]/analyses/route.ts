import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/prisma";

type RouteParams = {
    params: Promise<{
        topicId: string;
    }>;
};

export async function GET(_request: NextRequest, { params }: RouteParams) {
    const { topicId } = await params;
    const numericTopicId = Number(topicId);

    if (!Number.isInteger(numericTopicId)) {
        return NextResponse.json(
            { message: "잘못된 토론 ID입니다." },
            { status: 400 },
        );
    }

    const analyses = await prisma.analysis.findMany({
        where: {
            topicId: numericTopicId,
        },
        orderBy: {
            createdAt: "desc",
        },
    });

    return NextResponse.json({
        analyses: analyses.map((analysis) => ({
            id: analysis.id,
            topicId: analysis.topicId,
            title: analysis.title,
            writer: analysis.writer,
            summary: analysis.summary,
            createdAt: analysis.createdAt,
        })),
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

        const title = String(body.title ?? "").trim();
        const writer = String(body.writer ?? "관리자").trim() || "관리자";
        const summary = String(body.summary ?? "").trim();
        const content = String(body.content ?? "").trim();

        if (!title) {
            return NextResponse.json(
                { message: "분석글 제목을 입력해주세요." },
                { status: 400 },
            );
        }

        if (!summary) {
            return NextResponse.json(
                { message: "분석글 요약을 입력해주세요." },
                { status: 400 },
            );
        }

        if (!content) {
            return NextResponse.json(
                { message: "분석글 본문을 입력해주세요." },
                { status: 400 },
            );
        }

        const topic = await prisma.topic.findUnique({
            where: {
                id: numericTopicId,
            },
            select: {
                id: true,
                title: true,
                slug: true,
            },
        });

        if (!topic) {
            return NextResponse.json(
                { message: "토론 주제를 찾을 수 없습니다." },
                { status: 404 },
            );
        }

        const analysis = await prisma.analysis.create({
            data: {
                topicId: numericTopicId,
                title,
                writer,
                summary,
                content,
            },
        });

        return NextResponse.json(
            {
                analysis: {
                    id: analysis.id,
                    topicId: analysis.topicId,
                    topicTitle: topic.title,
                    topicSlug: topic.slug,
                    title: analysis.title,
                    writer: analysis.writer,
                    summary: analysis.summary,
                    content: analysis.content,
                    createdAt: analysis.createdAt,
                },
            },
            { status: 201 },
        );
    } catch (error) {
        console.error(error);

        return NextResponse.json(
            { message: "분석글 등록 중 오류가 발생했습니다." },
            { status: 500 },
        );
    }
}