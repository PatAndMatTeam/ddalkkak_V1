import SectionTitle from "@/components/common/SectionTitle";
import EmptyState from "@/components/common/EmptyState";
import TopicList from "@/components/topic/TopicList";
import { topics } from "@/lib/mock/topics";
import { categoryMap } from "@/lib/utils/category";

type CategoryPageProps = {
    params: Promise<{
        category: string;
    }>;
};

export default async function CategoryPage({ params }: CategoryPageProps) {
    const { category } = await params;

    const filteredTopics = topics.filter((topic) => topic.category === category);
    const categoryLabel = categoryMap[category] ?? category;

    return (
        <div className="page-container section-gap">
            <SectionTitle
                title={`${categoryLabel} 카테고리`}
                description={`${categoryLabel} 관련 VS 주제들을 모아봤어.`}
            />

            {filteredTopics.length > 0 ? (
                <TopicList topics={filteredTopics} />
            ) : (
                <EmptyState
                    title="이 카테고리에는 아직 주제가 없어"
                    description="나중에 새 주제가 추가되면 여기서 볼 수 있어."
                />
            )}
        </div>
    );
}