import React, { useEffect, useState } from 'react';
import { getOne } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import AnalysisBoardComponent from "../analysis/AnalysisBoardComponent";
import { Tab, Tabs } from '@mui/material'; // MUI에서 탭 컴포넌트 가져오기

const initState = {
    dtoList: [],
    pageNumList: [],
    pageRequestDTO: null,
    prev: false,
    next: false,
    totalCount: 0,
    prevPage: 0,
    nextPage: 0,
    totalPage: 0,
    current: 0,
    infos: [] // 추가한 데이터 필드
};

function ReadComponent({ tno }) {
    const [serverData, setServerData] = useState(null);  // 객체로 변경
    const { page, size, refresh, moveToRead } = useCustomMove();
    const [activeTab, setActiveTab] = useState(0); // 0: 분석글, 1: 실시간 채팅

    useEffect(() => {
        getOne(tno).then(data => {
            console.log(data);
            setServerData(data);
        });
    }, [page, size, refresh]);

    // 탭 변경 핸들러
    const handleTabChange = (event, newValue) => {
        setActiveTab(newValue);
    };

    if (!serverData) {
        return <div>Loading...</div>;
    }

    const base64Image0 = serverData.files && serverData.files[0]
        ? `data:image/jpeg;base64,${serverData.files[0].data}`
        : "/placeholder.jpg"; // 썸네일이 없을 경우 placeholder 이미지 사용

    const base64Image1 = serverData.files && serverData.files[1]
        ? `data:image/jpeg;base64,${serverData.files[1].data}`
        : "/placeholder.jpg"; // 썸네일이 없을 경우 placeholder 이미지 사용

    return (
        <div className="flex justify-center items-center mt-10 p-6">
            {/* 왼쪽: 게시물 내용 */}
            <div className="w-3/4 p-4">

                {/* 게시물 제목 */}
                <h1 className="text-3xl font-bold mb-4">{serverData.title || "제목 없음"}</h1>

                {/* 이미지 표시 */}
                <div className="flex justify-center items-center space-x-4">
                    <div className="flex w-full h-40 mt-4">
                        {serverData.files && serverData.files.length > 1 ? (
                            <>
                                <img
                                    src={base64Image0} // 첫 번째 이미지
                                    alt={serverData.title}
                                    className="w-1/2 object-cover rounded-l"
                                />
                                <img
                                    src={base64Image1} // 두 번째 이미지
                                    alt={serverData.title}
                                    className="w-1/2 object-cover rounded-r"
                                />
                            </>
                        ) : (
                            <img
                                src={base64Image0}
                                alt={serverData.title}
                                className="w-full h-40 object-cover rounded"
                            />
                        )}
                    </div>
                </div>

                {/* 설명 */}
                <div className="mb-6">
                    <h2 className="text-xl font-semibold mb-2">설명</h2>
                    <p className="text-gray-700">{serverData.content || "설명 없음"}</p>
                </div>

                <div className="flex flex-col mt-10 p-6">
                    {/* MUI의 Tabs 컴포넌트 사용 */}
                    <Tabs value={activeTab} onChange={handleTabChange}>
                        <Tab label="분석글" />
                        <Tab label="실시간 채팅" />
                    </Tabs>

                    {/* 분석글 탭 */}
                    {activeTab === 0 && (
                        <div className="p-4">
                            <h2 className="text-xl font-semibold mb-2">분석글</h2>
                            <AnalysisBoardComponent />
                        </div>
                    )}

                    {/* 실시간 채팅 탭 */}
                    {activeTab === 1 && (
                        <div className="p-4 bg-gray-100 rounded-lg shadow-lg">
                            <h2 className="text-xl font-semibold mb-4">실시간 채팅</h2>
                            <div className="h-96 overflow-y-auto">
                                {/* 여기에 댓글을 표시 */}
                                댓글을 보여줄 곳
                            </div>
                            <div className="mt-4">
                                <textarea
                                    className="w-full p-2 border border-gray-300 rounded-lg"
                                    placeholder="댓글을 입력하세요..."
                                />
                                <button className="w-full mt-2 bg-blue-500 text-white p-2 rounded-lg">
                                    댓글 달기
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}

export default ReadComponent;