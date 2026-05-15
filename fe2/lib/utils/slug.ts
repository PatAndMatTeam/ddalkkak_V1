export function createSlug(value: string) {
    return value
        .trim()
        .toLowerCase()
        .replace(/[^\w가-힣\s-]/g, "")
        .replace(/\s+/g, "-")
        .replace(/-+/g, "-")
        .replace(/^-|-$/g, "");
}

export function createFallbackSlug() {
    return `topic-${Date.now()}`;
}

export async function createUniqueSlug(
    title: string,
    exists: (slug: string) => Promise<boolean>,
) {
    const baseSlug = createSlug(title) || createFallbackSlug();

    let slug = baseSlug;
    let count = 2;

    while (await exists(slug)) {
        slug = `${baseSlug}-${count}`;
        count += 1;
    }

    return slug;
}