import { cookies } from "next/headers";

const VOTER_COOKIE_NAME = "pickside_voter_key";

function createVoterKey() {
    return crypto.randomUUID();
}

export async function getOrCreateVoterKey() {
    const cookieStore = await cookies();
    const saved = cookieStore.get(VOTER_COOKIE_NAME)?.value;

    if (saved) {
        return saved;
    }

    const voterKey = createVoterKey();

    cookieStore.set(VOTER_COOKIE_NAME, voterKey, {
        httpOnly: true,
        sameSite: "lax",
        secure: process.env.NODE_ENV === "production",
        maxAge: 60 * 60 * 24 * 365,
        path: "/",
    });

    return voterKey;
}