import Link from "next/link";
import { Topic } from "@/lib/types/topic";
import { categoryMap } from "@/lib/utils/category";

type TopicCardProps = {
    topic: Topic;
};

export default function TopicCard({ topic }: TopicCardProps) {
    const totalVotes = topic.optionA.voteCount + topic.optionB.voteCount;
    const categoryLabel = categoryMap[topic.category] ?? topic.category;

    return (
        <Link
            href={`/topics/${topic.slug}`}
            className="section-card"
            style={{
                display: "flex",
                flexDirection: "column",
                gap: "16px",
                minHeight: "240px",
            }}
        >
            <div style={{ display: "flex", justifyContent: "space-between", gap: "12px" }}>
        <span
            style={{
                display: "inline-flex",
                alignItems: "center",
                width: "fit-content",
                padding: "6px 10px",
                borderRadius: "999px",
                backgroundColor: "#f3f4f6",
                fontSize: "13px",
                fontWeight: 700,
            }}
        >
          {categoryLabel}
        </span>

                <span
                    style={{
                        fontSize: "13px",
                        color: "#6b7280",
                        fontWeight: 600,
                    }}
                >
          조회 {topic.viewCount}
        </span>
            </div>

            <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                <h3
                    style={{
                        margin: 0,
                        fontSize: "24px",
                        lineHeight: 1.35,
                        fontWeight: 800,
                        letterSpacing: "-0.03em",
                    }}
                >
                    {topic.title}
                </h3>

                <p className="muted-text" style={{ margin: 0, lineHeight: 1.6 }}>
                    {topic.description}
                </p>
            </div>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns: "1fr auto 1fr",
                    alignItems: "center",
                    gap: "10px",
                    marginTop: "auto",
                }}
            >
                <div
                    style={{
                        borderRadius: "14px",
                        backgroundColor: "#eef2ff",
                        padding: "14px 12px",
                        textAlign: "center",
                    }}
                >
                    <strong style={{ display: "block", fontSize: "16px" }}>
                        {topic.optionA.title}
                    </strong>
                    <span className="muted-text" style={{ fontSize: "13px" }}>
            {topic.optionA.voteCount}표
          </span>
                </div>

                <div
                    style={{
                        fontWeight: 900,
                        fontSize: "18px",
                        color: "#111827",
                    }}
                >
                    VS
                </div>

                <div
                    style={{
                        borderRadius: "14px",
                        backgroundColor: "#fef2f2",
                        padding: "14px 12px",
                        textAlign: "center",
                    }}
                >
                    <strong style={{ display: "block", fontSize: "16px" }}>
                        {topic.optionB.title}
                    </strong>
                    <span className="muted-text" style={{ fontSize: "13px" }}>
            {topic.optionB.voteCount}표
          </span>
                </div>
            </div>

            <div
                style={{
                    display: "flex",
                    justifyContent: "space-between",
                    gap: "12px",
                    fontSize: "14px",
                    color: "#4b5563",
                    fontWeight: 600,
                }}
            >
                <span>작성자 {topic.writer}</span>
                <span>총 투표 {totalVotes}</span>
            </div>
        </Link>
    );
}