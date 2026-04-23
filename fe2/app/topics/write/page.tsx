export default function TopicWritePage() {
    return (
        <div className="page-container">
            <div
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
                        지금은 UI 초안 단계라서 저장 기능은 아직 연결하지 않았어.
                    </p>
                </div>

                <div style={{ display: "flex", flexDirection: "column", gap: "18px" }}>
                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <label style={{ fontWeight: 700 }}>카테고리</label>
                        <select
                            style={{
                                height: "48px",
                                borderRadius: "12px",
                                border: "1px solid #d1d5db",
                                padding: "0 14px",
                                outline: "none",
                                backgroundColor: "#ffffff",
                            }}
                        >
                            <option>카테고리 선택</option>
                            <option>스포츠</option>
                            <option>게임</option>
                            <option>문화생활</option>
                            <option>취미</option>
                            <option>인방</option>
                        </select>
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                        <label style={{ fontWeight: 700 }}>주제 제목</label>
                        <input
                            type="text"
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
                        justifyContent: "flex-end",
                        gap: "12px",
                        flexWrap: "wrap",
                    }}
                >
                    <button
                        type="button"
                        style={{
                            border: "1px solid #d1d5db",
                            backgroundColor: "#ffffff",
                            color: "#111827",
                            padding: "12px 16px",
                            borderRadius: "12px",
                            fontWeight: 700,
                            cursor: "pointer",
                        }}
                    >
                        임시 저장
                    </button>

                    <button
                        type="button"
                        style={{
                            border: "none",
                            backgroundColor: "#111827",
                            color: "#ffffff",
                            padding: "12px 18px",
                            borderRadius: "12px",
                            fontWeight: 700,
                            cursor: "pointer",
                        }}
                    >
                        주제 등록
                    </button>
                </div>
            </div>
        </div>
    );
}