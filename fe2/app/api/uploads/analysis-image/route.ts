import { NextRequest, NextResponse } from "next/server";
import { supabaseAdmin } from "@/lib/supabase/admin";

const MAX_FILE_SIZE = 2 * 1024 * 1024;

const ALLOWED_TYPES = ["image/jpeg", "image/png", "image/webp", "image/gif"];

function createSafeFileName(originalName: string) {
    const extension = originalName.split(".").pop()?.toLowerCase() || "png";
    const random = crypto.randomUUID();

    return `analysis/${random}.${extension}`;
}

export async function POST(request: NextRequest) {
    try {
        const formData = await request.formData();
        const file = formData.get("file");

        if (!(file instanceof File)) {
            return NextResponse.json(
                { message: "이미지 파일이 없습니다." },
                { status: 400 },
            );
        }

        if (!ALLOWED_TYPES.includes(file.type)) {
            return NextResponse.json(
                { message: "jpg, png, webp, gif 이미지만 업로드할 수 있습니다." },
                { status: 400 },
            );
        }

        if (file.size > MAX_FILE_SIZE) {
            return NextResponse.json(
                { message: "이미지는 최대 2MB까지만 업로드할 수 있습니다." },
                { status: 400 },
            );
        }

        const bucket = process.env.SUPABASE_STORAGE_BUCKET || "analysis-images";
        const filePath = createSafeFileName(file.name);
        const arrayBuffer = await file.arrayBuffer();
        const buffer = Buffer.from(arrayBuffer);

        const { error } = await supabaseAdmin.storage
            .from(bucket)
            .upload(filePath, buffer, {
                contentType: file.type,
                upsert: false,
            });

        if (error) {
            console.error(error);

            return NextResponse.json(
                { message: "이미지 업로드 중 오류가 발생했습니다." },
                { status: 500 },
            );
        }

        const { data } = supabaseAdmin.storage
            .from(bucket)
            .getPublicUrl(filePath);

        return NextResponse.json({
            url: data.publicUrl,
            path: filePath,
        });
    } catch (error) {
        console.error(error);

        return NextResponse.json(
            { message: "이미지 업로드 중 오류가 발생했습니다." },
            { status: 500 },
        );
    }
}