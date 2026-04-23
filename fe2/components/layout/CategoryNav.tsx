import Link from "next/link";
import { categoryItems } from "@/lib/utils/category";

export default function CategoryNav() {
    return (
        <ul
            style={{
                display: "flex",
                alignItems: "center",
                gap: "12px",
                listStyle: "none",
                margin: 0,
                padding: 0,
            }}
        >
            {categoryItems.map((item) => (
                <li key={item.key}>
                    <Link
                        href={`/categories/${item.key}`}
                        style={{
                            display: "inline-flex",
                            alignItems: "center",
                            height: "40px",
                            padding: "0 14px",
                            borderRadius: "999px",
                            backgroundColor: "#f9fafb",
                            border: "1px solid #e5e7eb",
                            fontSize: "14px",
                            fontWeight: 600,
                            whiteSpace: "nowrap",
                        }}
                    >
                        {item.label}
                    </Link>
                </li>
            ))}
        </ul>
    );
}