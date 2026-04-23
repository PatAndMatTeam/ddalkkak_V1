type EmptyStateProps = {
    title: string;
    description?: string;
};

export default function EmptyState({
                                       title,
                                       description,
                                   }: EmptyStateProps) {
    return (
        <div
            className="section-card"
            style={{
                minHeight: "180px",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                textAlign: "center",
            }}
        >
            <div style={{ display: "flex", flexDirection: "column", gap: "8px" }}>
                <strong style={{ fontSize: "20px" }}>{title}</strong>
                {description ? <p className="muted-text">{description}</p> : null}
            </div>
        </div>
    );
}