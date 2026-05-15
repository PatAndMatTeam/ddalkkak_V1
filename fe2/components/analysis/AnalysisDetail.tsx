import Link from "next/link";
import { AnalysisPost, Topic } from "@/lib/types/topic";

type AnalysisDetailProps = {
    topic: Topic;
    analysis: AnalysisPost;
};

export default function AnalysisDetail({ topic, analysis }: AnalysisDetailProps) {
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

            <article
                className="section-card"
                style={{
                    maxWidth: "960px",
                    margin: "0 auto",
                    display: "flex",
                    flexDirection: "column",
                    gap: "24px",
                }}
            >
                <div
                    style={{
                        display: "flex",
                        justifyContent: "space-between",
                        gap: "12px",
                        flexWrap: "wrap",
                    }}
                >
                    <span
                        style={{
                            display: "inline-flex",
                            width: "fit-content",
                            padding: "6px 10px",
                            borderRadius: "999px",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            fontSize: "13px",
                            fontWeight: 700,
                        }}
                    >
                        {topic.title}
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
                        <span>작성자 {analysis.writer}</span>
                        <span>{new Date(analysis.createdAt).toLocaleDateString("ko-KR")}</span>
                    </div>
                </div>

                <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
                    <h1 className="page-title">{analysis.title}</h1>
                    <p className="muted-text" style={{ fontSize: "16px", lineHeight: 1.7 }}>
                        {analysis.summary}
                    </p>
                </div>

                <div
                    className="analysis-content-view"
                    dangerouslySetInnerHTML={{ __html: analysis.content ?? "" }}
                />
            </article>
        </div>
    );
}