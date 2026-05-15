export type CategoryKey =
    | "sports"
    | "game"
    | "culture"
    | "hobby"
    | "entertainment";

export type CategoryItem = {
    key: CategoryKey;
    name: string;
    label: string;
    href: string;
};

export const categoryItems: CategoryItem[] = [
    {
        key: "sports",
        name: "스포츠",
        label: "스포츠",
        href: "/categories/sports",
    },
    {
        key: "game",
        name: "게임",
        label: "게임",
        href: "/categories/game",
    },
    {
        key: "culture",
        name: "문화생활",
        label: "문화생활",
        href: "/categories/culture",
    },
    {
        key: "hobby",
        name: "취미",
        label: "취미",
        href: "/categories/hobby",
    },
    {
        key: "entertainment",
        name: "인방",
        label: "인방",
        href: "/categories/entertainment",
    },
];

export const categoryMap: Record<string, string> = {
    sports: "스포츠",
    game: "게임",
    culture: "문화생활",
    hobby: "취미",
    entertainment: "인방",
};

export const CATEGORY_LABELS = categoryMap;

export function getCategoryLabel(category: string) {
    return categoryMap[category] ?? category;
}

export function getCategoryHref(category: string) {
    return `/categories/${category}`;
}

export function getCategoryItems() {
    return categoryItems;
}