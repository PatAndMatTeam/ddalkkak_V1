import Link from "next/link";
import CategoryNav from "./CategoryNav";
import Container from "./Container";

export default function Header() {
    return (
        <header
            style={{
                position: "sticky",
                top: 0,
                zIndex: 50,
                backgroundColor: "#ffffff",
                borderBottom: "1px solid #e5e7eb",
            }}
        >
            <Container>
                <div
                    style={{
                        height: "72px",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "space-between",
                        gap: "20px",
                    }}
                >
                    <div style={{ display: "flex", alignItems: "center", gap: "28px" }}>
                        <Link
                            href="/"
                            style={{
                                fontSize: "24px",
                                fontWeight: 900,
                                letterSpacing: "-0.03em",
                            }}
                        >
                            ddalkkak
                        </Link>

                        <nav className="desktop-nav">
                            <CategoryNav />
                        </nav>
                    </div>

                    <div style={{ display: "flex", alignItems: "center", gap: "12px" }}>
                        <Link
                            href="/topics"
                            style={{
                                padding: "10px 14px",
                                borderRadius: "999px",
                                fontWeight: 600,
                                backgroundColor: "#f3f4f6",
                            }}
                        >
                            전체 주제
                        </Link>

                        <Link
                            href="/login"
                            style={{
                                padding: "10px 14px",
                                borderRadius: "999px",
                                fontWeight: 600,
                                backgroundColor: "#111827",
                                color: "#ffffff",
                            }}
                        >
                            로그인
                        </Link>
                    </div>
                </div>
            </Container>
        </header>
    );
}