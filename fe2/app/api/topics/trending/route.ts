import { NextRequest, NextResponse } from "next/server";
import { getTrendingTopics } from "@/lib/db/topics";

export async function GET(request: NextRequest) {
    const limit = Number(request.nextUrl.searchParams.get("limit") ?? 3);
    const topics = await getTrendingTopics(limit);

    return NextResponse.json({
        topics,
    });
}