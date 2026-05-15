export function formatDate(value: Date | string) {
    const date = typeof value === "string" ? new Date(value) : value;

    return new Intl.DateTimeFormat("ko-KR", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
    }).format(date);
}