import { headers } from "next/headers";

export async function getBaseUrl() {
    if (process.env.NEXT_PUBLIC_APP_URL) {
        return process.env.NEXT_PUBLIC_APP_URL;
    }

    const headerStore = await headers();
    const host = headerStore.get("host");

    if (!host) {
        return "http://localhost:3000";
    }

    const protocol =
        headerStore.get("x-forwarded-proto") ??
        (process.env.NODE_ENV === "production" ? "https" : "http");

    return `${protocol}://${host}`;
}