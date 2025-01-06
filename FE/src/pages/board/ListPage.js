import React from 'react';
import { useParams } from "react-router-dom";
import { getKoreanCategory } from "../../hooks/categoryUtils";
import ListComponent from "../../components/board/ListComponent";
import ListLayout from "../../layouts/ListLayout";
import useCustomMove from "../../hooks/useCustomMove";

const ListPage = () => {
    const {category} = useParams()
    const { moveToAdd } = useCustomMove();
    // 유틸리티 함수로 한글 카테고리명 가져오기
    const koreanCategory = getKoreanCategory(category);
    return (
        <ListLayout>
            <div className="p-6 max-w-7xl mx-auto bg-gray-50 rounded-lg">
                <div className="flex justify-between items-center mb-6">
                    <h1 className="text-4xl font-bold text-gray-800">
                        {koreanCategory}
                    </h1>
                    <button
                        className="bg-green-500 hover:bg-green-600 text-white font-semibold px-5 py-3 rounded-lg shadow-md transition duration-300"
                        onClick={() => moveToAdd(category)}>
                        글쓰기
                    </button>
                </div>
                <div className="border-t-2 border-gray-200 pt-6">
                    <ListComponent category={category}/>
                </div>
            </div>
        </ListLayout>


    );
}
export default ListPage;
