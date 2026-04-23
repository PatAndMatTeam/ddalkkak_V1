import "./globals.css";
import Header from "@/components/layout/Header";

export const metadata = {
    title: "ddalkkak",
    description: "VS 토론 커뮤니티",
};

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="ko">
        <body>
        <Header />
        <main className="site-main">{children}</main>
        </body>
        </html>
    );
}