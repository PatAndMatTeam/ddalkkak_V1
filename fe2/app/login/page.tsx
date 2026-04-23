export default function LoginPage() {
    return (
        <div className="page-container">
            <div
                className="section-card"
                style={{
                    maxWidth: "520px",
                    margin: "40px auto 0",
                    display: "flex",
                    flexDirection: "column",
                    gap: "24px",
                }}
            >
                <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                    <h1 className="page-title" style={{ fontSize: "34px" }}>
                        로그인
                    </h1>
                    <p className="muted-text">
                        지금은 UI 뼈대만 먼저 만들고 있어. 이후에 카카오 로그인이나 일반
                        로그인 연동을 붙이면 돼.
                    </p>
                </div>

                <div style={{ display: "flex", flexDirection: "column", gap: "14px" }}>
                    <input
                        type="text"
                        placeholder="이메일"
                        style={{
                            height: "48px",
                            padding: "0 14px",
                            borderRadius: "12px",
                            border: "1px solid #d1d5db",
                            outline: "none",
                        }}
                    />

                    <input
                        type="password"
                        placeholder="비밀번호"
                        style={{
                            height: "48px",
                            padding: "0 14px",
                            borderRadius: "12px",
                            border: "1px solid #d1d5db",
                            outline: "none",
                        }}
                    />

                    <button
                        style={{
                            height: "48px",
                            border: "none",
                            borderRadius: "12px",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            fontWeight: 700,
                            cursor: "pointer",
                        }}
                    >
                        로그인
                    </button>
                </div>
            </div>
        </div>
    );
}