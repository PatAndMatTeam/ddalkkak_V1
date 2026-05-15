import { NextResponse } from "next/server";
import { getCategories } from "@/lib/db/topics";

export async function GET() {
    const categories = await getCategories();

    return NextResponse.json({
        categories: categories.map((category) => ({
            id: category.id,
            key: category.key,
            name: category.name,
            sortOrder: category.sortOrder,
        })),
    });
}