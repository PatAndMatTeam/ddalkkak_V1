import React, { useEffect, useState } from 'react';
import { getAnlyOne ,incrementRecommend} from '../../api/todoApi';
import AnalysisComments from './AnalysisComments';

function AnalysisReadComponent({ category, tno, anlyId }) {
    const [analyDetailData, setAnalyDetailData] = useState(null);  // 게시글 데이터를 저장할 상태
    const [comments, setComments] = useState([]);  // 댓글을 저장할 상태

    const fetchData = () => {
        getAnlyOne({ category, tno, anlyId }).then(data => {
            setAnalyDetailData(data);  // 서버에서 받은 데이터를 상태에 저장
            setComments(data.comments || []);  // 댓글 데이터 설정
        });
    };

    useEffect(() => {
        fetchData();
    }, [category, tno]);

    const handleRecommend = () => {
        incrementRecommend({ anlyId  })
            .then(() => {
                fetchData(); // 추천수 증가 후 데이터 다시 로드
            })
            .catch((error) => {
                console.error("Failed to increment recommendation:", error);
                alert("추천에 실패했습니다. 다시 시도해 주세요.");
            });
    };


    if (!analyDetailData) {
        return <p>Loading...</p>;  // 데이터가 로드되기 전에는 로딩 메시지 표시
    }

    return (
        <div className="p-4 bg-white shadow-md rounded-md max-w-4xl mx-auto">
            {/* 게시글 제목 */}
            <h1 className="text-3xl font-bold mb-2 text-green-600" style={{ fontFamily: 'Arial, sans-serif' }}>
                {analyDetailData.title}
            </h1>

            {/* 구분선 */}
            <hr className="border-t-2 border-green-600 mb-4" />

            {/* 작성자 및 작성일 정보 */}
            <div className="flex justify-between items-center mb-2 text-gray-600 text-sm">
                <span>작성자: {analyDetailData.writer}</span>

                {/* 작성일, 조회수, 추천수, 댓글수 */}
                <div className="flex space-x-4 items-center text-gray-500">
                    <span>{new Date(analyDetailData.createDate).toLocaleDateString()}</span>
                    <span>조회 {analyDetailData.visited}</span>
                    <span>추천 {analyDetailData.recommend}</span>
                    <span>댓글 {comments.length}</span>
                </div>
            </div>

            {/* 게시글 내용 */}
            <div className="mb-4">
                <p dangerouslySetInnerHTML={{ __html: analyDetailData.content }}></p>
            </div>

            {/* 추천 버튼 */}
            <div className="flex justify-center mt-4">
                <button
                    className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600"
                    onClick={handleRecommend}
                >
                    <i className="fas fa-thumbs-up mr-2"></i> 추천하기
                </button>
            </div>

            {/* 댓글 섹션 */}
            <AnalysisComments comments={comments} anlyId={anlyId} refreshComments={fetchData} />
        </div>
    );
}

export default AnalysisReadComponent;
