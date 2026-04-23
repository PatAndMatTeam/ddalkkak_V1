import Link from "next/link";
import SectionTitle from "@/components/common/SectionTitle";
import { categoryItems } from "@/lib/utils/category";

export default function CategorySection() {
    return (
        <section className="section-gap">
            <SectionTitle
                title="카테고리 둘러보기"
                description="관심 있는 분야의 토론 주제를 골라 들어가봐."
            />

            <div className="grid-3">
                {categoryItems.map((item) => (
                    <Link
                        key={item.key}
                        href={`/categories/${item.key}`}
                        className="section-card"
                        style={{
                            minHeight: "140px",
                            display: "flex",
                            flexDirection: "column",
                            justifyContent: "space-between",
                        }}
                    >
                        <div
                            style={{
                                display: "inline-flex",
                                width: "fit-content",
                                padding: "6px 10px",
                                borderRadius: "999px",
                                backgroundColor: "#111827",
                                color: "#ffffff",
                                fontSize: "12px",
                                fontWeight: 800,
                            }}
                        >
                            CATEGORY
                        </div>

                        <div style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
                            <strong style={{ fontSize: "24px", fontWeight: 800 }}>
                                {item.label}
                            </strong>
                            <p className="muted-text" style={{ margin: 0 }}>
                                {item.label} 관련 VS 주제를 모아볼 수 있어.
                            </p>
                        </div>
                    </Link>
                ))}
            </div>
        </section>
    );
}