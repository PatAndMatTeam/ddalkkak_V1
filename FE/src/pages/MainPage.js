import React from 'react';
import BasicLayout from "../layouts/BasicLayout";
import SearchBar from "../components/main/SearchBar";
import Mainboard from "../components/main/Mainboard";
import Ranking from "../components/main/Ranking";
import useCustomMove from "../hooks/useCustomMove";
import ListComponent from "../components/board/ListComponent";

function MainPage(props) {
    const { moveToMain } = useCustomMove();

    return (
        <BasicLayout>
            {/* 로고 */}
            <div style={{ display: 'flex', justifyContent: 'center', paddingTop: '50px' }}> {/* 여백 조정 */}
                <button onClick={moveToMain} style={{ border: 'none', background: 'none', padding: 0 }}>
                    <img
                        src="/임시로고.png"
                        alt="Logo"
                        style={{ width: '400px', height: '160px' }}
                    />
                </button>
            </div>

            {/* 검색창 */}
            <div style={{ display: 'flex', justifyContent: 'center', padding: '20px' }}>
                <SearchBar />
            </div>

            {/* 메인보드와 랭킹 섹션 */}
            <div style={{ display: 'flex', gap: '20px', padding: '20px', margin: '0 20%' }}>
                {/* 왼쪽 영역: 메인 게시판 */}
                <div style={{ flex: 3 }}>
                    {/*<ListComponent />*/}
                </div>

                {/* 오른쪽 영역: 랭킹 */}
                <div style={{ flex: 1 }}>
                    <Ranking />
                </div>
            </div>
        </BasicLayout>
    );
}

export default MainPage;
