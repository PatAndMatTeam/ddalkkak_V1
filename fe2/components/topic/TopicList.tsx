import TopicCard from "./TopicCard";
import { Topic } from "@/lib/types/topic";

type TopicListProps = {
    topics: Topic[];
};

export default function TopicList({ topics }: TopicListProps) {
    return (
        <div className="grid-3">
            {topics.map((topic) => (
                <TopicCard key={topic.id} topic={topic} />
            ))}
        </div>
    );
}