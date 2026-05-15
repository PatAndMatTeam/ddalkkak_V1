import SectionTitle from "@/components/common/SectionTitle";
import EmptyState from "@/components/common/EmptyState";
import TopicList from "@/components/topic/TopicList";
import { getBaseUrl } from "@/lib/utils/server-url";
import { Topic } from "@/lib/types/topic";

async function getTopics() {
    const baseUrl = await getBaseUrl();

    const response = await fetch(`${baseUrl}/api/topics?sort=latest&page=1&size=20`, {
        cache: "no-store",
    });

    if (!response.ok) {
        return [];
    }

    const data = await response.json();

    return data.topics as Topic[];
}

export default async function TopicsPage() {
    const topics = await getTopics();

    return (
        <div className="page-container section-gap">
            <SectionTitle
                title="전체 주제"
                description="지금 등록된 모든 VS 토론 주제를 확인해봐."
            />

            {topics.length > 0 ? (
                <TopicList topics={topics} />
            ) : (
                <EmptyState
                    title="아직 등록된 주제가 없어"
                    description="첫 번째 VS 주제를 직접 만들어볼 수 있어."
                />
            )}
        </div>
    );
}