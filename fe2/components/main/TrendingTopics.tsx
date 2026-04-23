import Link from "next/link";
import SectionTitle from "@/components/common/SectionTitle";
import { topics } from "@/lib/mock/topics";
import { categoryMap } from "@/lib/utils/category";

export default function TrendingTopics() {
    const trending = [...topics]
        .sort((a, b) => b.viewCount - a.viewCount)
        .slice(0, 3);

    return (
        <section className="section-gap">
            <SectionTitle
                title="지금 뜨는 주제"
                description="조회수가 높은 인기 VS 토론 주제들이야."
            />

            <div className="grid-3">
                {trending.map((topic, index) => {
                    const categoryLabel = categoryMap[topic.category] ?? topic.category;

                    return (
                        <Link
                            key={topic.id}
                            href={`/topics/${topic.slug}`}
                            className="section-card"
                            style={{
                                display: "flex",
                                flexDirection: "column",
                                gap: "16px",
                                minHeight: "220px",
                            }}
                        >
                            <div
                                style={{
                                    width: "36px",
                                    height: "36px",
                                    borderRadius: "999px",
                                    backgroundColor: "#111827",
                                    color: "#ffffff",
                                    display: "flex",
                                    alignItems: "center",
                                    justifyContent: "center",
                                    fontWeight: 800,
                                }}
                            >
                                {index + 1}
                            </div>

                            <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
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

                                <h3
                                    style={{
                                        margin: 0,
                                        fontSize: "22px",
                                        lineHeight: 1.4,
                                        fontWeight: 800,
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
                                    marginTop: "auto",
                                    display: "flex",
                                    justifyContent: "space-between",
                                    fontSize: "14px",
                                    fontWeight: 600,
                                    color: "#4b5563",
                                }}
                            >
                                <span>{topic.optionA.title}</span>
                                <span>VS</span>
                                <span>{topic.optionB.title}</span>
                            </div>
                        </Link>
                    );
                })}
            </div>
        </section>
    );
}