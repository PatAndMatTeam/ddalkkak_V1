import React from 'react';
import BasicMenu from "../components/menus/BasicMenu";

// <div className="bg-white w-full flex justify-center">
// <main className="bg-sky-300 w-full max-w-screen-md px-2 py-5">

const ListLayout = ({ children }) => {
    return (
        <>
            {/* 기존 헤더 대신 BasicMenu */}
            <BasicMenu />
            {/* 상단 여백 my-5 제거, 좌우 여백 없이 중앙 정렬 */}
            <div className="bg-white w-full flex justify-center">
                <main className="bg-sky-300 w-full max-w-none px-0 py-5">
                    {children}
                </main>
            </div>
        </>
    );
};

export default ListLayout;

