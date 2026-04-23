import TopicDetail from "@/components/topic/TopicDetail";
import EmptyState from "@/components/common/EmptyState";
import { topics } from "@/lib/mock/topics";

type TopicPageProps = {
    params: Promise<{
        slug: string;
    }>;
};

export default async function TopicPage({ params }: TopicPageProps) {
    const { slug } = await params;

    const topic = topics.find((item) => item.slug === slug);

    if (!topic) {
        return (
            <div className="page-container">
                <EmptyState
                    title="주제를 찾을 수 없어"
                    description="존재하지 않거나 삭제된 주제일 수 있어."
                />
            </div>
        );
    }

    return <TopicDetail topic={topic} />;
}