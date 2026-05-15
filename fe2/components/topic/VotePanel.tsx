"use client";

import { useState } from "react";
import { Topic, TopicSide } from "@/lib/types/topic";

type VotePanelProps = {
    topic: Topic;
};

export default function VotePanel({ topic }: VotePanelProps) {
    const [optionA, setOptionA] = useState(topic.optionA);
    const [optionB, setOptionB] = useState(topic.optionB);
    const [myVote, setMyVote] = useState<TopicSide | null>(topic.myVote ?? null);
    const [isLoading, setIsLoading] = useState(false);
    const [message, setMessage] = useState("");

    const totalVotes = optionA.voteCount + optionB.voteCount;

    const optionAPercent =
        totalVotes === 0 ? 0 : Math.round((optionA.voteCount / totalVotes) * 100);

    const optionBPercent =
        totalVotes === 0 ? 0 : Math.round((optionB.voteCount / totalVotes) * 100);

    async function handleVote(side: TopicSide) {
        if (isLoading) return;

        setIsLoading(true);
        setMessage("");

        try {
            const response = await fetch(`/api/topics/${topic.id}/votes`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ side }),
            });

            const data = await response.json();

            if (!response.ok) {
                setMessage(data.message ?? "투표 처리 중 오류가 발생했습니다.");
                return;
            }

            setOptionA({
                ...optionA,
                voteCount: data.optionA.voteCount,
            });

            setOptionB({
                ...optionB,
                voteCount: data.optionB.voteCount,
            });

            setMyVote(data.myVote);
            setMessage("투표가 반영됐어.");
        } catch {
            setMessage("네트워크 오류가 발생했습니다.");
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <section className="section-card" style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
            <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                <h2 className="section-title">투표 현황</h2>
                <p className="muted-text">
                    원하는 쪽을 선택하면 실제 DB에 투표가 저장돼.
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
                    onClick={() => handleVote("A")}
                    disabled={isLoading}
                    style={{
                        border: myVote === "A" ? "2px solid #4f46e5" : "1px solid #c7d2fe",
                        backgroundColor: "#eef2ff",
                        borderRadius: "18px",
                        padding: "20px",
                        cursor: isLoading ? "not-allowed" : "pointer",
                        opacity: isLoading ? 0.7 : 1,
                    }}
                >
                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <strong style={{ fontSize: "20px" }}>{optionA.title}</strong>
                        <span className="muted-text">{optionA.voteCount}표</span>
                        <span style={{ fontSize: "28px", fontWeight: 900 }}>{optionAPercent}%</span>
                        {myVote === "A" && (
                            <span style={{ fontSize: "13px", fontWeight: 800 }}>내 선택</span>
                        )}
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
                    onClick={() => handleVote("B")}
                    disabled={isLoading}
                    style={{
                        border: myVote === "B" ? "2px solid #dc2626" : "1px solid #fecaca",
                        backgroundColor: "#fef2f2",
                        borderRadius: "18px",
                        padding: "20px",
                        cursor: isLoading ? "not-allowed" : "pointer",
                        opacity: isLoading ? 0.7 : 1,
                    }}
                >
                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <strong style={{ fontSize: "20px" }}>{optionB.title}</strong>
                        <span className="muted-text">{optionB.voteCount}표</span>
                        <span style={{ fontSize: "28px", fontWeight: 900 }}>{optionBPercent}%</span>
                        {myVote === "B" && (
                            <span style={{ fontSize: "13px", fontWeight: 800 }}>내 선택</span>
                        )}
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
                <span>작성일: {new Date(topic.createdAt).toLocaleDateString("ko-KR")}</span>
            </div>

            {message && (
                <p className="muted-text" style={{ margin: 0, fontWeight: 700 }}>
                    {message}
                </p>
            )}
        </section>
    );
}