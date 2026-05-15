"use client";

import { FormEvent, useEffect, useState } from "react";
import { DiscussionComment, Topic, TopicSide } from "@/lib/types/topic";

type DiscussionPanelProps = {
    topic: Topic;
};

export default function DiscussionPanel({ topic }: DiscussionPanelProps) {
    const [comments, setComments] = useState<DiscussionComment[]>([]);
    const [writer, setWriter] = useState("");
    const [content, setContent] = useState("");
    const [side, setSide] = useState<TopicSide | "">("");
    const [isLoading, setIsLoading] = useState(false);
    const [message, setMessage] = useState("");

    async function loadComments() {
        try {
            const response = await fetch(`/api/topics/${topic.id}/comments?page=1&size=50`, {
                cache: "no-store",
            });

            if (!response.ok) {
                return;
            }

            const data = await response.json();
            setComments(data.comments ?? []);
        } catch {
            setComments([]);
        }
    }

    useEffect(() => {
        loadComments();
    }, [topic.id]);

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        if (isLoading) return;

        const trimmedContent = content.trim();

        if (!trimmedContent) {
            setMessage("댓글 내용을 입력해줘.");
            return;
        }

        setIsLoading(true);
        setMessage("");

        try {
            const response = await fetch(`/api/topics/${topic.id}/comments`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    writer: writer.trim() || "익명",
                    content: trimmedContent,
                    side: side || null,
                }),
            });

            const data = await response.json();

            if (!response.ok) {
                setMessage(data.message ?? "댓글 등록 중 오류가 발생했습니다.");
                return;
            }

            setComments((prev) => [data.comment, ...prev]);
            setContent("");
            setMessage("댓글이 등록됐어.");
        } catch {
            setMessage("네트워크 오류가 발생했습니다.");
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <section className="section-card" style={{ display: "flex", flexDirection: "column", gap: "20px" }}>
            <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                <h2 className="section-title">토론 댓글</h2>
                <p className="muted-text">
                    선택한 입장을 표시해서 의견을 남길 수 있어.
                </p>
            </div>

            <div style={{ display: "flex", flexDirection: "column", gap: "14px" }}>
                {comments.length === 0 ? (
                    <div
                        style={{
                            border: "1px solid #e5e7eb",
                            borderRadius: "16px",
                            padding: "16px",
                            backgroundColor: "#ffffff",
                        }}
                    >
                        <p className="muted-text" style={{ margin: 0 }}>
                            아직 댓글이 없어. 첫 의견을 남겨보자.
                        </p>
                    </div>
                ) : (
                    comments.map((comment) => {
                        const isA = comment.side === "A";
                        const isB = comment.side === "B";

                        return (
                            <div
                                key={comment.id}
                                style={{
                                    border: "1px solid #e5e7eb",
                                    borderRadius: "16px",
                                    padding: "16px",
                                    backgroundColor: isA ? "#eef2ff" : isB ? "#fef2f2" : "#ffffff",
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

                                        {comment.side && (
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
                                        )}
                                    </div>

                                    <span className="muted-text" style={{ fontSize: "13px" }}>
                                        {new Date(comment.createdAt).toLocaleString("ko-KR")}
                                    </span>
                                </div>

                                <p style={{ margin: 0, lineHeight: 1.7 }}>{comment.content}</p>
                            </div>
                        );
                    })
                )}
            </div>

            <form
                onSubmit={handleSubmit}
                style={{
                    display: "flex",
                    flexDirection: "column",
                    gap: "12px",
                    borderTop: "1px solid #e5e7eb",
                    paddingTop: "16px",
                }}
            >
                <div className="grid-2">
                    <input
                        value={writer}
                        onChange={(event) => setWriter(event.target.value)}
                        placeholder="닉네임, 비우면 익명"
                        style={{
                            height: "44px",
                            border: "1px solid #d1d5db",
                            borderRadius: "12px",
                            padding: "0 12px",
                            outline: "none",
                            font: "inherit",
                        }}
                    />

                    <select
                        value={side}
                        onChange={(event) => setSide(event.target.value as TopicSide | "")}
                        style={{
                            height: "44px",
                            border: "1px solid #d1d5db",
                            borderRadius: "12px",
                            padding: "0 12px",
                            outline: "none",
                            font: "inherit",
                            backgroundColor: "#ffffff",
                        }}
                    >
                        <option value="">입장 선택 안 함</option>
                        <option value="A">{topic.optionA.title}</option>
                        <option value="B">{topic.optionB.title}</option>
                    </select>
                </div>

                <textarea
                    value={content}
                    onChange={(event) => setContent(event.target.value)}
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

                <div
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        gap: "12px",
                        flexWrap: "wrap",
                    }}
                >
                    <p className="muted-text" style={{ margin: 0, fontWeight: 700 }}>
                        {message}
                    </p>

                    <button
                        type="submit"
                        disabled={isLoading}
                        style={{
                            border: "none",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            padding: "12px 18px",
                            borderRadius: "12px",
                            fontWeight: 700,
                            cursor: isLoading ? "not-allowed" : "pointer",
                            opacity: isLoading ? 0.7 : 1,
                        }}
                    >
                        {isLoading ? "등록 중..." : "댓글 등록"}
                    </button>
                </div>
            </form>
        </section>
    );
}