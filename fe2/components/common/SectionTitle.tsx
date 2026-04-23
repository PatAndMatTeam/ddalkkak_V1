type SectionTitleProps = {
    title: string;
    description?: string;
};

export default function SectionTitle({
                                         title,
                                         description,
                                     }: SectionTitleProps) {
    return (
        <div style={{ display: "flex", flexDirection: "column", gap: "6px" }}>
            <h2 className="section-title">{title}</h2>
            {description ? <p className="muted-text">{description}</p> : null}
        </div>
    );
}