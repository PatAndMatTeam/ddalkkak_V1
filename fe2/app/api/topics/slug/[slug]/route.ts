import { NextRequest, NextResponse } from "next/server";
import { getOrCreateVoterKey } from "@/lib/utils/voter";
import { getTopicBySlug, increaseTopicViewCount } from "@/lib/db/topics";

type RouteParams = {
    params: Promise<{
        slug: string;
    }>;
};

export async function GET(_request: NextRequest, { params }: RouteParams) {
    const { slug } = await params;
    const voterKey = await getOrCreateVoterKey();

    const topic = await getTopicBySlug(slug, voterKey);

    if (!topic) {
        return NextResponse.json(
            { message: "토론 주제를 찾을 수 없습니다." },
            { status: 404 },
        );
    }

    await increaseTopicViewCount(topic.id);

    return NextResponse.json({
        topic: {
            ...topic,
            viewCount: topic.viewCount + 1,
        },
    });
}