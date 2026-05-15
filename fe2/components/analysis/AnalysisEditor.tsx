"use client";

import { ChangeEvent, useCallback, useRef, useState } from "react";
import { EditorContent, useEditor } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import Image from "@tiptap/extension-image";
import Link from "@tiptap/extension-link";
import TextAlign from "@tiptap/extension-text-align";
import Color from "@tiptap/extension-color";
import TextStyle from "@tiptap/extension-text-style";
import Underline from "@tiptap/extension-underline";
import { Extension } from "@tiptap/core";

const FontSize = Extension.create({
    name: "fontSize",

    addGlobalAttributes() {
        return [
            {
                types: ["textStyle"],
                attributes: {
                    fontSize: {
                        default: null,
                        parseHTML: (element) => element.style.fontSize || null,
                        renderHTML: (attributes) => {
                            if (!attributes.fontSize) {
                                return {};
                            }

                            return {
                                style: `font-size: ${attributes.fontSize}`,
                            };
                        },
                    },
                },
            },
        ];
    },
});

type AnalysisEditorProps = {
    value: string;
    onChange: (value: string) => void;
};

type ToolbarButtonProps = {
    label: string;
    active?: boolean;
    disabled?: boolean;
    onClick: () => void;
};

async function uploadAnalysisImage(file: File) {
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch("/api/uploads/analysis-image", {
        method: "POST",
        body: formData,
    });

    const data = await response.json();

    if (!response.ok) {
        throw new Error(data.message ?? "이미지 업로드 실패");
    }

    return data.url as string;
}

export default function AnalysisEditor({ value, onChange }: AnalysisEditorProps) {
    const fileInputRef = useRef<HTMLInputElement | null>(null);
    const [uploading, setUploading] = useState(false);
    const [message, setMessage] = useState("");

    const editor = useEditor({
        immediatelyRender: false,
        extensions: [
            StarterKit.configure({
                heading: {
                    levels: [1, 2, 3],
                },
            }),
            TextStyle,
            FontSize,
            Color,
            Underline,
            Image.configure({
                inline: false,
                allowBase64: false,
            }),
            Link.configure({
                openOnClick: false,
                autolink: true,
                defaultProtocol: "https",
            }),
            TextAlign.configure({
                types: ["heading", "paragraph"],
            }),
        ],
        content: value || "<p></p>",
        editorProps: {
            attributes: {
                class: "analysis-editor-content",
            },
            handleDrop: (view, event) => {
                const files = Array.from(event.dataTransfer?.files ?? []);
                const imageFiles = files.filter((file) => file.type.startsWith("image/"));

                if (imageFiles.length === 0) {
                    return false;
                }

                event.preventDefault();

                imageFiles.forEach((file) => {
                    void insertImageFile(file);
                });

                return true;
            },
            handlePaste: (view, event) => {
                const files = Array.from(event.clipboardData?.files ?? []);
                const imageFiles = files.filter((file) => file.type.startsWith("image/"));

                if (imageFiles.length === 0) {
                    return false;
                }

                event.preventDefault();

                imageFiles.forEach((file) => {
                    void insertImageFile(file);
                });

                return true;
            },
        },
        onUpdate: ({ editor }) => {
            onChange(editor.getHTML());
        },
    });

    const insertImageFile = useCallback(
        async (file: File) => {
            if (!editor) return;

            setUploading(true);
            setMessage("");

            try {
                const url = await uploadAnalysisImage(file);

                editor
                    .chain()
                    .focus()
                    .setImage({
                        src: url,
                        alt: file.name,
                    })
                    .run();

                setMessage("이미지가 업로드됐어.");
            } catch (error) {
                setMessage(
                    error instanceof Error
                        ? error.message
                        : "이미지 업로드 중 오류가 발생했습니다.",
                );
            } finally {
                setUploading(false);
            }
        },
        [editor],
    );

    function handleFileChange(event: ChangeEvent<HTMLInputElement>) {
        const files = Array.from(event.target.files ?? []);

        files.forEach((file) => {
            void insertImageFile(file);
        });

        event.target.value = "";
    }

    function setLink() {
        if (!editor) return;

        const previousUrl = editor.getAttributes("link").href;
        const url = window.prompt("링크 URL을 입력해줘.", previousUrl || "https://");

        if (url === null) return;

        if (url.trim() === "") {
            editor.chain().focus().extendMarkRange("link").unsetLink().run();
            return;
        }

        editor
            .chain()
            .focus()
            .extendMarkRange("link")
            .setLink({ href: url.trim() })
            .run();
    }

    function setFontSize(size: string) {
        if (!editor) return;

        if (!size) {
            editor.chain().focus().setMark("textStyle", { fontSize: null }).run();
            return;
        }

        editor.chain().focus().setMark("textStyle", { fontSize: size }).run();
    }

    if (!editor) {
        return (
            <div
                style={{
                    border: "1px solid #d1d5db",
                    borderRadius: "16px",
                    padding: "18px",
                    backgroundColor: "#ffffff",
                }}
            >
                에디터 불러오는 중...
            </div>
        );
    }

    return (
        <div
            style={{
                border: "1px solid #d1d5db",
                borderRadius: "18px",
                overflow: "hidden",
                backgroundColor: "#ffffff",
            }}
        >
            <div
                style={{
                    display: "flex",
                    flexWrap: "wrap",
                    gap: "8px",
                    padding: "12px",
                    borderBottom: "1px solid #e5e7eb",
                    backgroundColor: "#f9fafb",
                }}
            >
                <ToolbarButton
                    label="굵게"
                    active={editor.isActive("bold")}
                    onClick={() => editor.chain().focus().toggleBold().run()}
                />

                <ToolbarButton
                    label="밑줄"
                    active={editor.isActive("underline")}
                    onClick={() => editor.chain().focus().toggleUnderline().run()}
                />

                <ToolbarButton
                    label="H2"
                    active={editor.isActive("heading", { level: 2 })}
                    onClick={() => editor.chain().focus().toggleHeading({ level: 2 }).run()}
                />

                <ToolbarButton
                    label="H3"
                    active={editor.isActive("heading", { level: 3 })}
                    onClick={() => editor.chain().focus().toggleHeading({ level: 3 }).run()}
                />

                <ToolbarButton
                    label="목록"
                    active={editor.isActive("bulletList")}
                    onClick={() => editor.chain().focus().toggleBulletList().run()}
                />

                <ToolbarButton
                    label="번호목록"
                    active={editor.isActive("orderedList")}
                    onClick={() => editor.chain().focus().toggleOrderedList().run()}
                />

                <ToolbarButton
                    label="왼쪽"
                    active={editor.isActive({ textAlign: "left" })}
                    onClick={() => editor.chain().focus().setTextAlign("left").run()}
                />

                <ToolbarButton
                    label="가운데"
                    active={editor.isActive({ textAlign: "center" })}
                    onClick={() => editor.chain().focus().setTextAlign("center").run()}
                />

                <ToolbarButton
                    label="오른쪽"
                    active={editor.isActive({ textAlign: "right" })}
                    onClick={() => editor.chain().focus().setTextAlign("right").run()}
                />

                <button
                    type="button"
                    onClick={setLink}
                    style={{
                        border: "1px solid #d1d5db",
                        backgroundColor: editor.isActive("link") ? "#111827" : "#ffffff",
                        color: editor.isActive("link") ? "#ffffff" : "#111827",
                        borderRadius: "10px",
                        padding: "8px 11px",
                        fontSize: "13px",
                        fontWeight: 800,
                        cursor: "pointer",
                    }}
                >
                    링크
                </button>

                <select
                    onChange={(event) => setFontSize(event.target.value)}
                    defaultValue=""
                    style={{
                        height: "36px",
                        border: "1px solid #d1d5db",
                        borderRadius: "10px",
                        backgroundColor: "#ffffff",
                        padding: "0 8px",
                        fontWeight: 800,
                    }}
                >
                    <option value="">크기</option>
                    <option value="14px">작게</option>
                    <option value="16px">기본</option>
                    <option value="20px">크게</option>
                    <option value="28px">아주 크게</option>
                </select>

                <input
                    type="color"
                    title="글자색"
                    onChange={(event) => {
                        editor.chain().focus().setColor(event.target.value).run();
                    }}
                    style={{
                        width: "38px",
                        height: "36px",
                        border: "1px solid #d1d5db",
                        borderRadius: "10px",
                        backgroundColor: "#ffffff",
                        padding: "4px",
                        cursor: "pointer",
                    }}
                />

                <input
                    ref={fileInputRef}
                    type="file"
                    accept="image/jpeg,image/png,image/webp,image/gif"
                    multiple
                    onChange={handleFileChange}
                    style={{ display: "none" }}
                />

                <button
                    type="button"
                    onClick={() => fileInputRef.current?.click()}
                    disabled={uploading}
                    style={{
                        border: "1px solid #111827",
                        backgroundColor: "#111827",
                        color: "#ffffff",
                        borderRadius: "10px",
                        padding: "8px 11px",
                        fontSize: "13px",
                        fontWeight: 800,
                        cursor: uploading ? "not-allowed" : "pointer",
                        opacity: uploading ? 0.7 : 1,
                    }}
                >
                    {uploading ? "업로드 중..." : "이미지"}
                </button>
            </div>

            <EditorContent editor={editor} />

            <div
                style={{
                    borderTop: "1px solid #e5e7eb",
                    padding: "10px 14px",
                    backgroundColor: "#f9fafb",
                    display: "flex",
                    justifyContent: "space-between",
                    gap: "12px",
                    flexWrap: "wrap",
                    fontSize: "13px",
                    color: "#6b7280",
                    fontWeight: 700,
                }}
            >
                <span>이미지는 드래그, 복붙, 파일 선택으로 넣을 수 있어.</span>
                <span>{message}</span>
            </div>

            <style jsx global>{`
                .analysis-editor-content {
                    min-height: 520px;
                    padding: 22px;
                    outline: none;
                    font-size: 16px;
                    line-height: 1.85;
                }

                .analysis-editor-content p {
                    margin: 10px 0;
                }

                .analysis-editor-content h1,
                .analysis-editor-content h2,
                .analysis-editor-content h3 {
                    line-height: 1.35;
                    margin: 24px 0 12px;
                }

                .analysis-editor-content h2 {
                    font-size: 28px;
                }

                .analysis-editor-content h3 {
                    font-size: 22px;
                }

                .analysis-editor-content ul,
                .analysis-editor-content ol {
                    padding-left: 24px;
                    margin: 12px 0;
                }

                .analysis-editor-content img {
                    display: block;
                    max-width: 100%;
                    max-height: 520px;
                    object-fit: contain;
                    border-radius: 16px;
                    border: 1px solid #e5e7eb;
                    margin: 18px auto;
                }

                .analysis-editor-content a {
                    color: #2563eb;
                    text-decoration: underline;
                }

                .analysis-editor-content blockquote {
                    border-left: 4px solid #111827;
                    background: #f9fafb;
                    margin: 16px 0;
                    padding: 12px 16px;
                    color: #374151;
                }
            `}</style>
        </div>
    );
}

function ToolbarButton({ label, active, disabled, onClick }: ToolbarButtonProps) {
    return (
        <button
            type="button"
            disabled={disabled}
            onClick={onClick}
            style={{
                border: "1px solid #d1d5db",
                backgroundColor: active ? "#111827" : "#ffffff",
                color: active ? "#ffffff" : "#111827",
                borderRadius: "10px",
                padding: "8px 11px",
                fontSize: "13px",
                fontWeight: 800,
                cursor: disabled ? "not-allowed" : "pointer",
                opacity: disabled ? 0.6 : 1,
            }}
        >
            {label}
        </button>
    );
}