import AnalysisDetail from "@/components/analysis/AnalysisDetail";
import EmptyState from "@/components/common/EmptyState";
import { topics } from "@/lib/mock/topics";

type TopicAnalysisDetailPageProps = {
    params: Promise<{
        slug: string;
        id: string;
    }>;
};

export default async function TopicAnalysisDetailPage({
                                                          params,
                                                      }: TopicAnalysisDetailPageProps) {
    const { slug, id } = await params;

    const topic = topics.find((item) => item.slug === slug);

    if (!topic) {
        return (
            <div className="page-container">
                <EmptyState
                    title="주제를 찾을 수 없어"
                    description="분석글을 표시할 대상 주제가 존재하지 않아."
                />
            </div>
        );
    }

    const analysis = topic.analyses.find((item) => item.id === Number(id));

    if (!analysis) {
        return (
            <div className="page-container">
                <EmptyState
                    title="분석글을 찾을 수 없어"
                    description="존재하지 않거나 삭제된 분석글일 수 있어."
                />
            </div>
        );
    }

    return <AnalysisDetail topic={topic} analysis={analysis} />;
}