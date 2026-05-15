"use client";

import { FormEvent, useState } from "react";
import { useRouter } from "next/navigation";
import { categoryItems } from "@/lib/utils/category";

export default function TopicWritePage() {
    const router = useRouter();

    const [category, setCategory] = useState("");
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [optionA, setOptionA] = useState("");
    const [optionB, setOptionB] = useState("");
    const [writer, setWriter] = useState("");
    const [message, setMessage] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        if (isLoading) return;

        setMessage("");

        if (!category) {
            setMessage("카테고리를 선택해줘.");
            return;
        }

        if (!title.trim()) {
            setMessage("주제 제목을 입력해줘.");
            return;
        }

        if (!description.trim()) {
            setMessage("주제 설명을 입력해줘.");
            return;
        }

        if (!optionA.trim() || !optionB.trim()) {
            setMessage("A/B 선택지를 모두 입력해줘.");
            return;
        }

        setIsLoading(true);

        try {
            const response = await fetch("/api/topics", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    category,
                    title: title.trim(),
                    description: description.trim(),
                    writer: writer.trim() || "익명",
                    optionA: {
                        title: optionA.trim(),
                    },
                    optionB: {
                        title: optionB.trim(),
                    },
                }),
            });

            const data = await response.json();

            if (!response.ok) {
                setMessage(data.message ?? "주제 등록 중 오류가 발생했습니다.");
                return;
            }

            router.push(`/topics/${data.topic.slug}`);
            router.refresh();
        } catch {
            setMessage("네트워크 오류가 발생했습니다.");
        } finally {
            setIsLoading(false);
        }
    }

    return (
        <div className="page-container">
            <form
                onSubmit={handleSubmit}
                className="section-card"
                style={{
                    maxWidth: "840px",
                    margin: "0 auto",
                    display: "flex",
                    flexDirection: "column",
                    gap: "28px",
                }}
            >
                <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                    <h1 className="page-title">새 주제 만들기</h1>
                    <p className="muted-text" style={{ lineHeight: 1.7 }}>
                        사람들이 투표하고 토론할 수 있는 새로운 VS 주제를 작성해보자.
                    </p>
                </div>

                <div style={{ display: "flex", flexDirection: "column", gap: "18px" }}>
                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <label style={{ fontWeight: 700 }}>카테고리</label>
                        <select
                            value={category}
                            onChange={(event) => setCategory(event.target.value)}
                            style={{
                                height: "48px",
                                borderRadius: "12px",
                                border: "1px solid #d1d5db",
                                padding: "0 14px",
                                outline: "none",
                                backgroundColor: "#ffffff",
                            }}
                        >
                            <option value="">카테고리 선택</option>
                            {categoryItems.map((item) => (
                                <option key={item.key} value={item.key}>
                                    {item.label}
                                </option>
                            ))}
                        </select>
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <label style={{ fontWeight: 700 }}>작성자</label>
                        <input
                            type="text"
                            value={writer}
                            onChange={(event) => setWriter(event.target.value)}
                            placeholder="비우면 익명"
                            style={{
                                height: "48px",
                                borderRadius: "12px",
                                border: "1px solid #d1d5db",
                                padding: "0 14px",
                                outline: "none",
                            }}
                        />
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <label style={{ fontWeight: 700 }}>주제 제목</label>
                        <input
                            type="text"
                            value={title}
                            onChange={(event) => setTitle(event.target.value)}
                            placeholder="예: 아이폰 vs 갤럭시"
                            style={{
                                height: "48px",
                                borderRadius: "12px",
                                border: "1px solid #d1d5db",
                                padding: "0 14px",
                                outline: "none",
                            }}
                        />
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <label style={{ fontWeight: 700 }}>주제 설명</label>
                        <textarea
                            value={description}
                            onChange={(event) => setDescription(event.target.value)}
                            rows={4}
                            placeholder="이 주제로 어떤 토론을 하고 싶은지 설명해줘"
                            style={{
                                width: "100%",
                                resize: "vertical",
                                borderRadius: "12px",
                                border: "1px solid #d1d5db",
                                padding: "14px",
                                outline: "none",
                                font: "inherit",
                            }}
                        />
                    </div>

                    <div className="grid-2">
                        <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                            <label style={{ fontWeight: 700 }}>선택지 A</label>
                            <input
                                type="text"
                                value={optionA}
                                onChange={(event) => setOptionA(event.target.value)}
                                placeholder="예: 아이폰"
                                style={{
                                    height: "48px",
                                    borderRadius: "12px",
                                    border: "1px solid #c7d2fe",
                                    backgroundColor: "#eef2ff",
                                    padding: "0 14px",
                                    outline: "none",
                                }}
                            />
                        </div>

                        <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                            <label style={{ fontWeight: 700 }}>선택지 B</label>
                            <input
                                type="text"
                                value={optionB}
                                onChange={(event) => setOptionB(event.target.value)}
                                placeholder="예: 갤럭시"
                                style={{
                                    height: "48px",
                                    borderRadius: "12px",
                                    border: "1px solid #fecaca",
                                    backgroundColor: "#fef2f2",
                                    padding: "0 14px",
                                    outline: "none",
                                }}
                            />
                        </div>
                    </div>
                </div>

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
                        {isLoading ? "등록 중..." : "주제 등록"}
                    </button>
                </div>
            </form>
        </div>
    );
}