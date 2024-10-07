import React from 'react';

import CategoryMenu from "../components/menus/CategoryMenu";
import LoginBarMenu from "../components/menus/LoginBarMenu";


const BasicLayout = ({ children }) => {
    return (
        <>
            {/* 상단 로그인 */}
            <LoginBarMenu/>

            {/* 카테고리 메뉴 */}
            <CategoryMenu/>

            <div>
                {/* 상단 여백 py-40 변경 flex 제거 */}
                <main >{children}</main>
            </div>
        </>
    );
};

export default BasicLayout;

