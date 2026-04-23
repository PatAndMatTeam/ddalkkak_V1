import Link from "next/link";
import { Topic } from "@/lib/types/topic";

type AnalysisListProps = {
    topic: Topic;
};

export default function AnalysisList({ topic }: AnalysisListProps) {
    return (
        <div className="section-gap">
            {topic.analyses.map((analysis) => (
                <Link
                    key={analysis.id}
                    href={`/topics/${topic.slug}/analysis/${analysis.id}`}
                    className="section-card"
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        gap: "12px",
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
                    backgroundColor: "#f3f4f6",
                    fontSize: "13px",
                    fontWeight: 700,
                }}
            >
              분석글
            </span>

                        <span className="muted-text" style={{ fontSize: "14px" }}>
              {analysis.createdAt}
            </span>
                    </div>

                    <h2
                        style={{
                            margin: 0,
                            fontSize: "24px",
                            lineHeight: 1.4,
                            fontWeight: 800,
                        }}
                    >
                        {analysis.title}
                    </h2>

                    <p className="muted-text" style={{ margin: 0, lineHeight: 1.7 }}>
                        {analysis.summary}
                    </p>

                    <div
                        style={{
                            display: "flex",
                            justifyContent: "space-between",
                            gap: "12px",
                            flexWrap: "wrap",
                            marginTop: "6px",
                            fontSize: "14px",
                            fontWeight: 600,
                            color: "#4b5563",
                        }}
                    >
                        <span>작성자 {analysis.writer}</span>
                        <span>상세 보기</span>
                    </div>
                </Link>
            ))}
        </div>
    );
}