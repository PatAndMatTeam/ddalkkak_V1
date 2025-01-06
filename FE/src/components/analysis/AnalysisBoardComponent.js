import React, { useEffect, useState } from 'react';
import useCustomMove from "../../hooks/useCustomMove";
import { getAnlyList, increaseVisitCount } from "../../api/todoApi";

const AnalysisBoardComponent = ({ category, tno, title }) => {
    const [analyData, setAnalyData] = useState([]);
    const [totalPages, setTotalPages] = useState(1);
    const [currentPage, setCurrentPage] = useState(1);
    const [searchType, setSearchType] = useState("title");
    const [keyword, setKeyword] = useState("");
    const { moveToAnlyAdd, moveToAnlyRead } = useCustomMove();
    const pageSize = 5;

    const fetchData = async (params = {}) => {
        try {
            const data = await getAnlyList(params);

            // `data`와 `data.infos`가 정의되었는지 확인
            const items = data?.infos?.childs || []; // 안전하게 `childs` 참조
            setTotalPages(Math.ceil(items.length / pageSize));
            setAnalyData(items);
        } catch (error) {
            console.error("Error fetching data:", error);
            // 필요에 따라 에러 메시지를 사용자에게 표시할 수 있습니다.
        }
    };

    useEffect(() => {
        fetchData({ category, tno });
    }, [category, tno]);

    const handleTitleClick = async (category, tno, anlyId) => {
        const success = await increaseVisitCount(anlyId);
        if (success) {
            moveToAnlyRead(category, tno, anlyId);
        } else {
            alert("조회수 증가에 실패했습니다.");
        }
    };

    const handleSearchSubmit = (e) => {
        e.preventDefault();
        const searchParams = { category, tno };

        if (searchType === "title") searchParams.title = keyword;
        else if (searchType === "content") searchParams.content = keyword;
        else if (searchType === "titleAndContent") {
            searchParams.title = keyword;
            searchParams.content = keyword;
        }
        fetchData(searchParams);
    };

    const paginatedData = analyData.slice((currentPage - 1) * pageSize, currentPage * pageSize);

    return (
        <div className="mt-10 mr-2 ml-2">
            <div className="flex justify-between p-4 bg-gray-50 border-b">
                <h2 className="text-2xl font-bold text-green-600">전체 게시글</h2>
                <div>
                    <button className="text-blue-500 font-semibold">인기순</button> |
                    <button className="text-blue-500 font-semibold ml-2">최신순</button> |
                    <button className="text-blue-500 font-semibold ml-2">추천순</button>
                </div>
            </div>

            <hr className="border-t-2 border-green-600 mb-4" />

            <section id="boardList">
                <table className="min-w-full bg-white text-xs text-left border border-gray-200">
                    <thead className="bg-gray-100 border-b-2 border-green-600">
                    <tr className="text-gray-700">
                        <th className="py-2 px-4 text-center w-1/12">번호</th>
                        <th className="py-2 px-4 text-left w-1/2">제목</th>
                        <th className="py-2 px-4 text-center w-1/10">글쓴이</th>
                        <th className="py-2 px-4 text-center w-1/10">작성일</th>
                        <th className="py-2 px-4 text-center w-1/12">조회</th>
                        <th className="py-2 px-4 text-center w-1/12">추천</th>
                    </tr>
                    </thead>
                    <tbody>
                    {paginatedData.length > 0 ? (
                        paginatedData.map((child, index) => (
                            <tr key={child.id} className="border-b border-gray-200 hover:bg-gray-50">
                                <td className="py-2 px-4 text-center">{index + 1}</td>
                                <td className="py-2 px-4">
                                    <span
                                        className="font-bold hover:underline cursor-pointer"
                                        onClick={() => handleTitleClick(category, tno, child.id)}
                                    >
                                        {child.title}
                                    </span>
                                </td>
                                <td className="py-2 px-4 text-center">{child.writer}</td>
                                <td className="py-2 px-4 text-center">{new Date(child.modifyDate).toLocaleDateString()}</td>
                                <td className="py-2 px-4 text-center">{child.visited}</td>
                                <td className="py-2 px-4 text-center">{child.recommend}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="6" className="py-4 text-center text-gray-600">게시글이 없습니다.</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </section>

            {/* 페이지네이션 */}
            <section className="pagination flex justify-center my-6 space-x-2">
                <button onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))} className="text-blue-500" disabled={currentPage === 1}>
                    이전 페이지
                </button>
                {Array.from({ length: totalPages }, (_, idx) => idx + 1).map(pageNum => (
                    <button
                        key={pageNum}
                        onClick={() => setCurrentPage(pageNum)}
                        className={`mx-1 ${pageNum === currentPage ? 'font-bold text-green-500' : 'text-blue-500'}`}
                    >
                        {pageNum}
                    </button>
                ))}
                <button onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))} className="text-blue-500" disabled={currentPage === totalPages}>
                    다음 페이지
                </button>
            </section>

            <div className="searchAndWrite flex justify-between items-center p-4">
                <form className="flex space-x-2" onSubmit={handleSearchSubmit}>
                    <select
                        name="searchType"
                        className="border p-2 rounded-md text-xs"
                        value={searchType}
                        onChange={(e) => setSearchType(e.target.value)}
                    >
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                        <option value="titleAndContent">제목 + 내용</option>
                    </select>
                    <input
                        type="text"
                        name="keyword"
                        maxLength="50"
                        className="border p-2 rounded-md text-xs"
                        placeholder="검색어 입력"
                        value={keyword}
                        onChange={(e) => setKeyword(e.target.value)}
                    />
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded-md text-xs">검색</button>
                </form>
                <button className="bg-green-500 text-white px-4 py-2 rounded-md text-xs" onClick={() => moveToAnlyAdd(category, tno, title)}>
                    글쓰기
                </button>
            </div>
        </div>
    );
};

export default AnalysisBoardComponent;
