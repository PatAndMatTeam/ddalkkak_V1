import AnalysisDetail from "@/components/analysis/AnalysisDetail";
import EmptyState from "@/components/common/EmptyState";
import { getBaseUrl } from "@/lib/utils/server-url";
import { AnalysisPost, Topic } from "@/lib/types/topic";

type TopicAnalysisDetailPageProps = {
    params: Promise<{
        slug: string;
        id: string;
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

async function getAnalysis(topicId: number, analysisId: string) {
    const baseUrl = await getBaseUrl();

    const response = await fetch(`${baseUrl}/api/topics/${topicId}/analyses/${analysisId}`, {
        cache: "no-store",
    });

    if (!response.ok) {
        return null;
    }

    const data = await response.json();

    return data.analysis as AnalysisPost;
}

export default async function TopicAnalysisDetailPage({
                                                          params,
                                                      }: TopicAnalysisDetailPageProps) {
    const { slug, id } = await params;

    const topic = await getTopic(slug);

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

    const analysis = await getAnalysis(topic.id, id);

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