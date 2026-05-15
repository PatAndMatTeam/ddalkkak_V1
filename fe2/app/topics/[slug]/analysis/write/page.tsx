"use client";

import { FormEvent, useEffect, useState } from "react";
import dynamic from "next/dynamic";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";

const AnalysisEditor = dynamic(
    () => import("@/components/analysis/AnalysisEditor"),
    {
        ssr: false,
    },
);

type Topic = {
    id: number;
    slug: string;
    title: string;
    description: string;
};

export default function AnalysisWritePage() {
    const router = useRouter();
    const params = useParams<{
        slug: string;
    }>();

    const slug = params.slug;

    const [topic, setTopic] = useState<Topic | null>(null);
    const [isTopicLoading, setIsTopicLoading] = useState(true);

    const [title, setTitle] = useState("");
    const [writer, setWriter] = useState("");
    const [summary, setSummary] = useState("");
    const [content, setContent] = useState("");

    const [message, setMessage] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        async function loadTopic() {
            try {
                const response = await fetch(`/api/topics/slug/${slug}`, {
                    cache: "no-store",
                });

                if (!response.ok) {
                    setTopic(null);
                    return;
                }

                const data = await response.json();
                setTopic(data.topic);
            } catch {
                setTopic(null);
            } finally {
                setIsTopicLoading(false);
            }
        }

        loadTopic();
    }, [slug]);

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        if (!topic || isSubmitting) return;

        setMessage("");

        const trimmedTitle = title.trim();
        const trimmedSummary = summary.trim();
        const trimmedContent = content.trim();
        const trimmedWriter = writer.trim() || "관리자";

        if (!trimmedTitle) {
            setMessage("분석글 제목을 입력해줘.");
            return;
        }

        if (!trimmedSummary) {
            setMessage("요약을 입력해줘.");
            return;
        }

        if (!trimmedContent || trimmedContent === "<p></p>") {
            setMessage("본문을 입력해줘.");
            return;
        }

        setIsSubmitting(true);

        try {
            const response = await fetch(`/api/topics/${topic.id}/analyses`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    title: trimmedTitle,
                    writer: trimmedWriter,
                    summary: trimmedSummary,
                    content: trimmedContent,
                }),
            });

            const data = await response.json();

            if (!response.ok) {
                setMessage(data.message ?? "분석글 등록 중 오류가 발생했습니다.");
                return;
            }

            router.push(`/topics/${topic.slug}/analysis/${data.analysis.id}`);
            router.refresh();
        } catch {
            setMessage("네트워크 오류가 발생했습니다.");
        } finally {
            setIsSubmitting(false);
        }
    }

    if (isTopicLoading) {
        return (
            <div className="page-container">
                <div className="section-card">
                    <p className="muted-text" style={{ margin: 0 }}>
                        주제 정보를 불러오는 중...
                    </p>
                </div>
            </div>
        );
    }

    if (!topic) {
        return (
            <div className="page-container">
                <div className="section-card">
                    <h1 className="section-title">주제를 찾을 수 없어</h1>
                    <p className="muted-text">
                        분석글을 작성할 대상 주제가 존재하지 않아.
                    </p>
                    <Link
                        href="/topics"
                        style={{
                            display: "inline-flex",
                            marginTop: "16px",
                            padding: "11px 16px",
                            borderRadius: "12px",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            fontWeight: 800,
                        }}
                    >
                        전체 주제 보기
                    </Link>
                </div>
            </div>
        );
    }

    return (
        <div className="page-container section-gap">
            <div>
                <Link
                    href={`/topics/${topic.slug}/analysis`}
                    style={{
                        display: "inline-flex",
                        alignItems: "center",
                        gap: "6px",
                        fontWeight: 700,
                        color: "#4b5563",
                    }}
                >
                    ← 분석글 목록으로
                </Link>
            </div>

            <form
                onSubmit={handleSubmit}
                className="section-card"
                style={{
                    maxWidth: "1180px",
                    margin: "0 auto",
                    display: "flex",
                    flexDirection: "column",
                    gap: "26px",
                }}
            >
                <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                    <span
                        style={{
                            display: "inline-flex",
                            width: "fit-content",
                            padding: "6px 10px",
                            borderRadius: "999px",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            fontSize: "13px",
                            fontWeight: 800,
                        }}
                    >
                        {topic.title}
                    </span>

                    <h1 className="page-title">분석글 작성</h1>

                    <p className="muted-text" style={{ lineHeight: 1.7 }}>
                        이미지 드래그/복붙, 글씨 크기, 색상, 정렬을 지원하는 에디터야.
                    </p>
                </div>

                <div
                    style={{
                        display: "grid",
                        gridTemplateColumns: "180px 1fr",
                        gap: "14px",
                    }}
                >
                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <label style={{ fontWeight: 800 }}>작성자</label>
                        <input
                            type="text"
                            value={writer}
                            onChange={(event) => setWriter(event.target.value)}
                            placeholder="비우면 관리자"
                            style={{
                                height: "48px",
                                borderRadius: "12px",
                                border: "1px solid #d1d5db",
                                padding: "0 14px",
                                outline: "none",
                                font: "inherit",
                            }}
                        />
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <label style={{ fontWeight: 800 }}>제목</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(event) => setTitle(event.target.value)}
                            placeholder="예: 왜 아이폰 선호가 꾸준한가"
                            style={{
                                height: "48px",
                                borderRadius: "12px",
                                border: "1px solid #d1d5db",
                                padding: "0 14px",
                                outline: "none",
                                font: "inherit",
                            }}
                        />
                    </div>
                </div>

                <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                    <label style={{ fontWeight: 800 }}>요약</label>
                    <textarea
                        value={summary}
                        onChange={(event) => setSummary(event.target.value)}
                        rows={3}
                        placeholder="목록에 표시될 짧은 요약을 입력해줘"
                        style={{
                            width: "100%",
                            resize: "vertical",
                            borderRadius: "12px",
                            border: "1px solid #d1d5db",
                            padding: "14px",
                            outline: "none",
                            font: "inherit",
                            lineHeight: 1.6,
                        }}
                    />
                </div>

                <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                    <label style={{ fontWeight: 800 }}>본문</label>
                    <AnalysisEditor value={content} onChange={setContent} />
                </div>

                <div
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                        gap: "12px",
                        flexWrap: "wrap",
                        borderTop: "1px solid #e5e7eb",
                        paddingTop: "18px",
                    }}
                >
                    <p className="muted-text" style={{ margin: 0, fontWeight: 700 }}>
                        {message}
                    </p>

                    <button
                        type="submit"
                        disabled={isSubmitting}
                        style={{
                            border: "none",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            padding: "13px 22px",
                            borderRadius: "12px",
                            fontWeight: 900,
                            cursor: isSubmitting ? "not-allowed" : "pointer",
                            opacity: isSubmitting ? 0.7 : 1,
                        }}
                    >
                        {isSubmitting ? "등록 중..." : "분석글 등록"}
                    </button>
                </div>
            </form>
        </div>
    );
}