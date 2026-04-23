import Link from "next/link";

export default function HeroSection() {
    return (
        <section
            className="section-card"
            style={{
                padding: "40px 32px",
                display: "flex",
                flexDirection: "column",
                gap: "20px",
                background:
                    "linear-gradient(135deg, rgba(17,24,39,1) 0%, rgba(31,41,55,1) 50%, rgba(55,65,81,1) 100%)",
                color: "#ffffff",
            }}
        >
            <div style={{ display: "flex", flexDirection: "column", gap: "12px" }}>
                <p
                    style={{
                        margin: 0,
                        fontSize: "14px",
                        fontWeight: 700,
                        opacity: 0.85,
                        letterSpacing: "0.04em",
                    }}
                >
                    VS DEBATE COMMUNITY
                </p>

                <h1
                    style={{
                        margin: 0,
                        fontSize: "42px",
                        fontWeight: 900,
                        lineHeight: 1.15,
                        letterSpacing: "-0.04em",
                    }}
                >
                    주제를 던지고,
                    <br />
                    선택하고,
                    <br />
                    토론하자.
                </h1>

                <p
                    style={{
                        margin: 0,
                        maxWidth: "720px",
                        fontSize: "17px",
                        lineHeight: 1.7,
                        color: "rgba(255,255,255,0.85)",
                    }}
                >
                    ddalkkak은 하나의 주제를 두고 서로 다른 선택지를 비교하며
                    의견을 나누는 VS 토론 커뮤니티야. 가볍게 투표할 수도 있고,
                    깊게 분석글을 읽을 수도 있어.
                </p>
            </div>

            <div style={{ display: "flex", gap: "12px", flexWrap: "wrap" }}>
                <Link
                    href="/topics"
                    style={{
                        padding: "14px 18px",
                        borderRadius: "999px",
                        backgroundColor: "#ffffff",
                        color: "#111827",
                        fontWeight: 800,
                    }}
                >
                    전체 주제 보러가기
                </Link>

                <Link
                    href="/topics/write"
                    style={{
                        padding: "14px 18px",
                        borderRadius: "999px",
                        border: "1px solid rgba(255,255,255,0.3)",
                        color: "#ffffff",
                        fontWeight: 700,
                    }}
                >
                    새 주제 만들기
                </Link>
            </div>
        </section>
    );
}