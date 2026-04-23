import Link from "next/link";
import VotePanel from "./VotePanel";
import DiscussionPanel from "./DiscussionPanel";
import { Topic } from "@/lib/types/topic";
import { categoryMap } from "@/lib/utils/category";

type TopicDetailProps = {
    topic: Topic;
};

export default function TopicDetail({ topic }: TopicDetailProps) {
    const categoryLabel = categoryMap[topic.category] ?? topic.category;

    return (
        <div className="page-container section-gap">
            <section
                className="section-card"
                style={{ display: "flex", flexDirection: "column", gap: "20px" }}
            >
                <div
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        gap: "16px",
                        flexWrap: "wrap",
                    }}
                >
          <span
              style={{
                  display: "inline-flex",
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

                    <div
                        style={{
                            display: "flex",
                            gap: "14px",
                            flexWrap: "wrap",
                            color: "#6b7280",
                            fontSize: "14px",
                            fontWeight: 600,
                        }}
                    >
                        <span>작성자 {topic.writer}</span>
                        <span>조회 {topic.viewCount}</span>
                        <span>{topic.createdAt}</span>
                    </div>
                </div>

                <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
                    <h1 className="page-title">{topic.title}</h1>
                    <p className="muted-text" style={{ fontSize: "16px", lineHeight: 1.7 }}>
                        {topic.description}
                    </p>
                </div>

                <div
                    style={{
                        display: "grid",
                        gridTemplateColumns: "1fr auto 1fr",
                        gap: "16px",
                        alignItems: "stretch",
                    }}
                >
                    <div
                        style={{
                            borderRadius: "18px",
                            padding: "22px",
                            backgroundColor: "#eef2ff",
                            border: "1px solid #c7d2fe",
                        }}
                    >
                        <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                            <span style={{ fontSize: "13px", fontWeight: 800 }}>OPTION A</span>
                            <strong style={{ fontSize: "28px", lineHeight: 1.3 }}>
                                {topic.optionA.title}
                            </strong>
                        </div>
                    </div>

                    <div
                        style={{
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "center",
                            fontWeight: 900,
                            fontSize: "26px",
                        }}
                    >
                        VS
                    </div>

                    <div
                        style={{
                            borderRadius: "18px",
                            padding: "22px",
                            backgroundColor: "#fef2f2",
                            border: "1px solid #fecaca",
                        }}
                    >
                        <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                            <span style={{ fontSize: "13px", fontWeight: 800 }}>OPTION B</span>
                            <strong style={{ fontSize: "28px", lineHeight: 1.3 }}>
                                {topic.optionB.title}
                            </strong>
                        </div>
                    </div>
                </div>

                <div style={{ display: "flex", gap: "12px", flexWrap: "wrap" }}>
                    <Link
                        href={`/topics/${topic.slug}/analysis`}
                        style={{
                            display: "inline-flex",
                            alignItems: "center",
                            justifyContent: "center",
                            padding: "12px 16px",
                            borderRadius: "12px",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            fontWeight: 700,
                        }}
                    >
                        분석글 보러가기
                    </Link>

                    <Link
                        href="/topics"
                        style={{
                            display: "inline-flex",
                            alignItems: "center",
                            justifyContent: "center",
                            padding: "12px 16px",
                            borderRadius: "12px",
                            backgroundColor: "#f3f4f6",
                            color: "#111827",
                            fontWeight: 700,
                        }}
                    >
                        전체 주제 보기
                    </Link>
                </div>
            </section>

            <VotePanel topic={topic} />
            <DiscussionPanel topic={topic} />
        </div>
    );
}