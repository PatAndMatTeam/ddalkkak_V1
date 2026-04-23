import { Topic } from "@/lib/types/topic";

type DiscussionPanelProps = {
    topic: Topic;
};

export default function DiscussionPanel({ topic }: DiscussionPanelProps) {
    return (
        <section className="section-card" style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
            <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                <h2 className="section-title">토론 댓글</h2>
                <p className="muted-text">
                    추후에는 선택한 입장에 따라 댓글 스타일이나 필터를 더 세분화할 수 있어.
                </p>
            </div>

            <div style={{ display: "flex", flexDirection: "column", gap: "14px" }}>
                {topic.discussions.map((comment) => {
                    const isA = comment.side === "A";

                    return (
                        <div
                            key={comment.id}
                            style={{
                                border: "1px solid #e5e7eb",
                                borderRadius: "16px",
                                padding: "16px",
                                backgroundColor: isA ? "#eef2ff" : "#fef2f2",
                            }}
                        >
                            <div
                                style={{
                                    display: "flex",
                                    justifyContent: "space-between",
                                    gap: "12px",
                                    flexWrap: "wrap",
                                    marginBottom: "10px",
                                }}
                            >
                                <div style={{ display: "flex", alignItems: "center", gap: "10px" }}>
                                    <strong>{comment.writer}</strong>
                                    <span
                                        style={{
                                            display: "inline-flex",
                                            alignItems: "center",
                                            padding: "4px 8px",
                                            borderRadius: "999px",
                                            fontSize: "12px",
                                            fontWeight: 800,
                                            backgroundColor: isA ? "#c7d2fe" : "#fecaca",
                                            color: "#111827",
                                        }}
                                    >
                    {isA ? topic.optionA.title : topic.optionB.title}
                  </span>
                                </div>

                                <span className="muted-text" style={{ fontSize: "13px" }}>
                  {comment.createdAt}
                </span>
                            </div>

                            <p style={{ margin: 0, lineHeight: 1.7 }}>{comment.content}</p>
                        </div>
                    );
                })}
            </div>

            <div
                style={{
                    display: "flex",
                    flexDirection: "column",
                    gap: "12px",
                    borderTop: "1px solid #e5e7eb",
                    paddingTop: "16px",
                }}
            >
        <textarea
            placeholder="의견을 남겨보자"
            rows={4}
            style={{
                width: "100%",
                resize: "vertical",
                border: "1px solid #d1d5db",
                borderRadius: "14px",
                padding: "14px",
                outline: "none",
                font: "inherit",
            }}
        />

                <div style={{ display: "flex", justifyContent: "flex-end" }}>
                    <button
                        type="button"
                        style={{
                            border: "none",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            padding: "12px 18px",
                            borderRadius: "12px",
                            fontWeight: 700,
                            cursor: "pointer",
                        }}
                    >
                        댓글 등록
                    </button>
                </div>
            </div>
        </section>
    );
}