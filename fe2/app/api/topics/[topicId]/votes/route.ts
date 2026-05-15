import { NextRequest, NextResponse } from "next/server";
import { TopicSide } from "@prisma/client";
import { prisma } from "@/lib/prisma";
import { getOrCreateVoterKey } from "@/lib/utils/voter";

type RouteParams = {
    params: Promise<{
        topicId: string;
    }>;
};

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
        const side = String(body.side ?? "");

        if (side !== TopicSide.A && side !== TopicSide.B) {
            return NextResponse.json(
                { message: "투표 값은 A 또는 B만 가능합니다." },
                { status: 400 },
            );
        }

        const topic = await prisma.topic.findUnique({
            where: { id: numericTopicId },
            include: {
                options: true,
            },
        });

        if (!topic) {
            return NextResponse.json(
                { message: "토론 주제를 찾을 수 없습니다." },
                { status: 404 },
            );
        }

        const voterKey = await getOrCreateVoterKey();

        await prisma.vote.upsert({
            where: {
                topicId_voterKey: {
                    topicId: numericTopicId,
                    voterKey,
                },
            },
            update: {
                side,
            },
            create: {
                topicId: numericTopicId,
                voterKey,
                side,
            },
        });

        const [voteA, voteB] = await Promise.all([
            prisma.vote.count({
                where: {
                    topicId: numericTopicId,
                    side: TopicSide.A,
                },
            }),
            prisma.vote.count({
                where: {
                    topicId: numericTopicId,
                    side: TopicSide.B,
                },
            }),
        ]);

        const optionA = topic.options.find((option) => option.side === TopicSide.A);
        const optionB = topic.options.find((option) => option.side === TopicSide.B);

        return NextResponse.json({
            topicId: numericTopicId,
            myVote: side,
            optionA: {
                id: "A",
                title: optionA?.title ?? "",
                voteCount: voteA,
            },
            optionB: {
                id: "B",
                title: optionB?.title ?? "",
                voteCount: voteB,
            },
            totalVotes: voteA + voteB,
        });
    } catch (error) {
        console.error(error);

        return NextResponse.json(
            { message: "투표 처리 중 오류가 발생했습니다." },
            { status: 500 },
        );
    }
}