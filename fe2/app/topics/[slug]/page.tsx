import TopicDetail from "@/components/topic/TopicDetail";
import EmptyState from "@/components/common/EmptyState";
import { getBaseUrl } from "@/lib/utils/server-url";
import { Topic } from "@/lib/types/topic";

type TopicPageProps = {
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

export default async function TopicPage({ params }: TopicPageProps) {
    const { slug } = await params;

    const topic = await getTopic(slug);

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