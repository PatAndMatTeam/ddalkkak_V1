import Link from "next/link";
import { Topic, AnalysisPost } from "@/lib/types/topic";

type AnalysisDetailProps = {
    topic: Topic;
    analysis: AnalysisPost;
};

export default function AnalysisDetail({
                                           topic,
                                           analysis,
                                       }: AnalysisDetailProps) {
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
                style={{ display: "flex", flexDirection: "column", gap: "24px" }}
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
                        <span>{analysis.createdAt}</span>
                    </div>
                </div>

                <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
                    <h1 className="page-title">{analysis.title}</h1>
                    <p className="muted-text" style={{ fontSize: "16px", lineHeight: 1.7 }}>
                        {analysis.summary}
                    </p>
                </div>

                <div
                    style={{
                        borderTop: "1px solid #e5e7eb",
                        paddingTop: "24px",
                        display: "flex",
                        flexDirection: "column",
                        gap: "18px",
                        lineHeight: 1.9,
                        fontSize: "16px",
                    }}
                >
                    <p style={{ margin: 0 }}>{analysis.content}</p>
                    <p style={{ margin: 0 }}>
                        지금은 목데이터 기반이라 내용이 짧지만, 나중에는 마크다운이나 에디터
                        기반 본문으로 확장할 수 있어.
                    </p>
                    <p style={{ margin: 0 }}>
                        또 향후에는 찬성/반대 의견 통계, 댓글 분위기, 최근 투표 흐름 같은
                        보조 지표도 같이 붙일 수 있어.
                    </p>
                </div>
            </article>
        </div>
    );
}