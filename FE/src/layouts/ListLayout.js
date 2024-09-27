import React from 'react';

import CategoryMenu from "../components/menus/CategoryMenu";
import LoginBarMenu from "../components/menus/LoginBarMenu";


const ListLayout = ({ children }) => {
    return (
        <>
            {/* 상단 로그인 */}
            <LoginBarMenu/>

            {/* 카테고리 메뉴 */}
            <CategoryMenu/>
aaa
            <div>
                <main >{children}</main>
            </div>
        </>
    );
};

export default ListLayout;

