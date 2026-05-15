import SectionTitle from "@/components/common/SectionTitle";
import EmptyState from "@/components/common/EmptyState";
import TopicList from "@/components/topic/TopicList";
import { categoryMap } from "@/lib/utils/category";
import { getBaseUrl } from "@/lib/utils/server-url";
import { Topic } from "@/lib/types/topic";

type CategoryPageProps = {
    params: Promise<{
        category: string;
    }>;
};

async function getCategoryTopics(category: string) {
    const baseUrl = await getBaseUrl();

    const response = await fetch(
        `${baseUrl}/api/topics?category=${category}&page=1&size=20`,
        {
            cache: "no-store",
        },
    );

    if (!response.ok) {
        return [];
    }

    const data = await response.json();

    return data.topics as Topic[];
}

export default async function CategoryPage({ params }: CategoryPageProps) {
    const { category } = await params;

    const topics = await getCategoryTopics(category);
    const categoryLabel = categoryMap[category] ?? category;

    return (
        <div className="page-container section-gap">
            <SectionTitle
                title={`${categoryLabel} 카테고리`}
                description={`${categoryLabel} 관련 VS 주제들을 모아봤어.`}
            />

            {topics.length > 0 ? (
                <TopicList topics={topics} />
            ) : (
                <EmptyState
                    title="이 카테고리에는 아직 주제가 없어"
                    description="나중에 새 주제가 추가되면 여기서 볼 수 있어."
                />
            )}
        </div>
    );
}