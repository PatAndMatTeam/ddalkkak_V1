import React from 'react';
import CategoryMenu from "../components/menus/CategoryMenu";
import LoginBarMenu from "../components/menus/LoginBarMenu";
import useCustomMove from "../hooks/useCustomMove";

const BasicLayout = ({ children }) => {
    const { moveToMain } = useCustomMove();

    return (
        <>
            {/* 상단 로그인과 로고 */}
            <div style={{ display: 'flex', alignItems: 'center', padding: '10px 20px', backgroundColor: '#00796b' }}>
                <div style={{flex: 1}}>
                    <button onClick={moveToMain} >
                        <img
                            src="/임시로고.png"
                            alt="Logo"
                            style={{width: '300px', height: '50px'}}
                        />
                    </button>

                </div>
                <LoginBarMenu/>
            </div>

            {/* 카테고리 메뉴 */}
            <CategoryMenu/>

            <div>
                <main>{children}</main>
            </div>
        </>
    );
};

export default BasicLayout;
