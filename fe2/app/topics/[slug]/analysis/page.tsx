import Link from "next/link";
import SectionTitle from "@/components/common/SectionTitle";
import EmptyState from "@/components/common/EmptyState";
import AnalysisList from "@/components/analysis/AnalysisList";
import { topics } from "@/lib/mock/topics";

type TopicAnalysisPageProps = {
    params: Promise<{
        slug: string;
    }>;
};

export default async function TopicAnalysisPage({
                                                    params,
                                                }: TopicAnalysisPageProps) {
    const { slug } = await params;

    const topic = topics.find((item) => item.slug === slug);

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

    return (
        <div className="page-container section-gap">
            <div>
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
            </div>

            <SectionTitle
                title={`${topic.title} 분석글`}
                description="이 주제를 더 깊게 다루는 해설과 분석을 모아봤어."
            />

            {topic.analyses.length > 0 ? (
                <AnalysisList topic={topic} />
            ) : (
                <EmptyState
                    title="아직 분석글이 없어"
                    description="나중에 첫 번째 분석글이 등록되면 여기 표시돼."
                />
            )}
        </div>
    );
}