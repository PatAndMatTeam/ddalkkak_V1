import { Topic } from "@/lib/types/topic";

type VotePanelProps = {
    topic: Topic;
};

export default function VotePanel({ topic }: VotePanelProps) {
    const totalVotes = topic.optionA.voteCount + topic.optionB.voteCount;

    const optionAPercent =
        totalVotes === 0 ? 0 : Math.round((topic.optionA.voteCount / totalVotes) * 100);

    const optionBPercent =
        totalVotes === 0 ? 0 : Math.round((topic.optionB.voteCount / totalVotes) * 100);

    return (
        <section className="section-card" style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
            <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                <h2 className="section-title">투표 현황</h2>
                <p className="muted-text">
                    지금은 목데이터 기준으로 보여주고 있어. 이후 실제 투표 API를 붙이면 돼.
                </p>
            </div>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns: "1fr auto 1fr",
                    gap: "14px",
                    alignItems: "stretch",
                }}
            >
                <button
                    type="button"
                    style={{
                        border: "1px solid #c7d2fe",
                        backgroundColor: "#eef2ff",
                        borderRadius: "18px",
                        padding: "20px",
                        cursor: "pointer",
                    }}
                >
                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <strong style={{ fontSize: "20px" }}>{topic.optionA.title}</strong>
                        <span className="muted-text">{topic.optionA.voteCount}표</span>
                        <span style={{ fontSize: "28px", fontWeight: 900 }}>{optionAPercent}%</span>
                    </div>
                </button>

                <div
                    style={{
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        fontSize: "20px",
                        fontWeight: 900,
                        color: "#111827",
                    }}
                >
                    VS
                </div>

                <button
                    type="button"
                    style={{
                        border: "1px solid #fecaca",
                        backgroundColor: "#fef2f2",
                        borderRadius: "18px",
                        padding: "20px",
                        cursor: "pointer",
                    }}
                >
                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <strong style={{ fontSize: "20px" }}>{topic.optionB.title}</strong>
                        <span className="muted-text">{topic.optionB.voteCount}표</span>
                        <span style={{ fontSize: "28px", fontWeight: 900 }}>{optionBPercent}%</span>
                    </div>
                </button>
            </div>

            <div
                style={{
                    width: "100%",
                    height: "18px",
                    borderRadius: "999px",
                    overflow: "hidden",
                    backgroundColor: "#e5e7eb",
                    display: "flex",
                }}
            >
                <div
                    style={{
                        width: `${optionAPercent}%`,
                        backgroundColor: "#6366f1",
                        transition: "0.3s ease",
                    }}
                />
                <div
                    style={{
                        width: `${optionBPercent}%`,
                        backgroundColor: "#ef4444",
                        transition: "0.3s ease",
                    }}
                />
            </div>

            <div
                style={{
                    display: "flex",
                    justifyContent: "space-between",
                    gap: "12px",
                    flexWrap: "wrap",
                    fontSize: "14px",
                    fontWeight: 600,
                    color: "#4b5563",
                }}
            >
                <span>총 투표 수: {totalVotes}</span>
                <span>작성일: {topic.createdAt}</span>
            </div>
        </section>
    );
}