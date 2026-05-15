import Link from "next/link";
import SectionTitle from "@/components/common/SectionTitle";
import EmptyState from "@/components/common/EmptyState";
import AnalysisList from "@/components/analysis/AnalysisList";
import { getBaseUrl } from "@/lib/utils/server-url";
import { AnalysisPost, Topic } from "@/lib/types/topic";

type TopicAnalysisPageProps = {
    params: Promise<{
        slug: string;
    }>;
};

async function getTopic(slug: string) {
    const baseUrl = await getBaseUrl();

    const response = await fetch(`${baseUrl}/api/topics/slug/${slug}`, {
        cache: "no-store",
    });

    if (!response.ok) {
        return null;
    }

    const data = await response.json();

    return data.topic as Topic;
}

async function getAnalyses(topicId: number) {
    const baseUrl = await getBaseUrl();

    const response = await fetch(`${baseUrl}/api/topics/${topicId}/analyses`, {
        cache: "no-store",
    });

    if (!response.ok) {
        return [];
    }

    const data = await response.json();

    return data.analyses as AnalysisPost[];
}

export default async function TopicAnalysisPage({
                                                    params,
                                                }: TopicAnalysisPageProps) {
    const { slug } = await params;

    const topic = await getTopic(slug);

    if (!topic) {
        return (
            <div className="page-container">
                <EmptyState
                    title="주제를 찾을 수 없어"
                    description="분석글을 불러올 대상 주제가 존재하지 않아."
                />
            </div>
        );
    }

    const analyses = await getAnalyses(topic.id);

    return (
        <div className="page-container section-gap">
            <div
                style={{
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                    gap: "12px",
                    flexWrap: "wrap",
                }}
            >
                <Link
                    href={`/topics/${topic.slug}`}
                    style={{
                        display: "inline-flex",
                        alignItems: "center",
                        gap: "6px",
                        fontWeight: 700,
                        color: "#4b5563",
                    }}
                >
                    ← 주제 상세로
                </Link>

                <Link
                    href={`/topics/${topic.slug}/analysis/write`}
                    style={{
                        display: "inline-flex",
                        alignItems: "center",
                        justifyContent: "center",
                        padding: "11px 16px",
                        borderRadius: "12px",
                        backgroundColor: "#111827",
                        color: "#ffffff",
                        fontWeight: 800,
                        fontSize: "14px",
                    }}
                >
                    분석글 작성
                </Link>
            </div>

            <SectionTitle
                title={`${topic.title} 분석글`}
                description="이 주제를 더 깊게 다루는 해설과 분석을 모아봤어."
            />

            {analyses.length > 0 ? (
                <AnalysisList topic={topic} analyses={analyses} />
            ) : (
                <EmptyState
                    title="아직 분석글이 없어"
                    description="첫 번째 분석글을 직접 작성해볼 수 있어."
                />
            )}
        </div>
    );
}