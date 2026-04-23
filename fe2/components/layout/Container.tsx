export default function Container({
                                      children,
                                  }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <div
            style={{
                maxWidth: "1200px",
                margin: "0 auto",
                paddingLeft: "20px",
                paddingRight: "20px",
            }}
        >
            {children}
        </div>
    );
}