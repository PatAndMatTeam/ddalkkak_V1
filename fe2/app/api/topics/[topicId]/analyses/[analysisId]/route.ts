import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/prisma";

type RouteParams = {
    params: Promise<{
        topicId: string;
        analysisId: string;
    }>;
};

export async function GET(_request: NextRequest, { params }: RouteParams) {
    const { topicId, analysisId } = await params;

    const numericTopicId = Number(topicId);
    const numericAnalysisId = Number(analysisId);

    if (!Number.isInteger(numericTopicId) || !Number.isInteger(numericAnalysisId)) {
        return NextResponse.json(
            { message: "잘못된 요청입니다." },
            { status: 400 },
        );
    }

    const analysis = await prisma.analysis.findFirst({
        where: {
            id: numericAnalysisId,
            topicId: numericTopicId,
        },
        include: {
            topic: {
                select: {
                    title: true,
                    slug: true,
                },
            },
        },
    });

    if (!analysis) {
        return NextResponse.json(
            { message: "분석글을 찾을 수 없습니다." },
            { status: 404 },
        );
    }

    return NextResponse.json({
        analysis: {
            id: analysis.id,
            topicId: analysis.topicId,
            topicTitle: analysis.topic.title,
            topicSlug: analysis.topic.slug,
            title: analysis.title,
            writer: analysis.writer,
            summary: analysis.summary,
            content: analysis.content,
            createdAt: analysis.createdAt,
        },
    });
}