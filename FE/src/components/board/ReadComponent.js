import React, {useEffect, useState} from 'react';
import {getOne} from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import AnalysisBoardComponent from "../analysis/AnalysisBoardComponent";

const initState = {
    tno:0,
    title:'',
    writer:'',
    dueDate:'',
    complete: false
}
                        //읽을 페이지
function ReadComponent({postId }) {

    //기본값을 주고시작하는게 좋지 그리고 데이터 들어오면 useEffect로 처리
    //비동기로 바뀐다는건 상태처리
    //리엑트의 컴포넌트는 상태가 변경되면 자동으로 렌더링 된다는 사실을 기억하셔야 되고
    //리엑트 컴포넌트는 이 useState는 원래 함수형 컴포넌트가 상태를 유지할수 없었자나요
    //근데 이제 함수형 컴포넌트가 상태를 유지할 때 쓰는거죠 이때 이제 useEffect랑 같이 결합해서 쓴다는거죠

    //   기존todo값 ,저장할todo값(바뀐값)   ( 초기값)
    const [todo, setTodo] = useState(initState);
    const [postData, setPostData] = useState(null);

    //hooks으로 한줄로 빼버리면 편하다 이런거 할때는 커스텀 훅으로 빼주는게 좋다
    const {moveToList,moveToModify} = useCustomMove()


/*
    useEffect(() => {
        // 해당 게시물의 세부 정보를 가져오는 API 호출
        getOne(postId).then(data => {
            setPostData(data);
        });
    }, [postId]);

    if (!postData) {
        return <div>Loading...</div>;
    }*/

/*    useEffect(() => {
        // 해당 게시물의 세부 정보를 가져오는 API 호출
        getOne(postId).then(data => {
            setPostData(data);
        });
    }, [postId]);

    if (!postData) {
        return <div>Loading...</div>;
    }*/


    return (
        <div className="flex justify-between mt-10 p-6">
            {/* 왼쪽: 게시물 내용 */}
            <div className="w-3/4 p-4">


                {/* 게시물 제목 */}
                <h1 className="text-3xl font-bold mb-4">{/*  {postData.title}*/} </h1>


                <div className="flex justify-center items-center space-x-4">
                    {/* 왼쪽 팀 */}
                    <div className="flex items-center bg-red-500 text-white  w-1/3 h-20 rounded-lg">
                        <img
                            src="https://i.namu.wiki/i/a6qqiwy-VyqwqSZmumu15J9Njn79fWY85wkWZ79ZzhbY_GZMLRy6EoxKlEpZus4xuXc6llrbug0_WOonmJgh1Q.svg"
                            alt="사우샘프턴 로고"
                            className="w-100 h-16"
                        />
                        <div className="ml-2 text-center">
                            <div className="font-bold">사우샘프턴</div>
                            <div>38,030</div>
                        </div>
                    </div>

                    {/* 가운데 VS */}
                    <div className="flex items-center justify-center text-gray-700 font-bold">
                        <span className="text-2xl">VS</span>
                    </div>

                    {/* 오른쪽 팀 */}
                    <div className="flex items-center bg-red-600 text-white w-1/3 h-20  rounded-lg">
                        <img
                            src="https://i.namu.wiki/i/Pv9NYWGfEhphXAKEef1jRvyPlp_d1DJ2ZeQVY5zSo7b8rfHh7RzVGgggNHPthCd5zITHV9m4d9ZjZhOK4I6Mbw.svg"
                            alt="맨유 로고"
                            className="w-100 h-16"
                        />
                        <div className="ml-2 text-center">
                            <div className="font-bold">맨유</div>
                            <div>41,202</div>
                        </div>
                    </div>
                </div>




                {/* 설명 */}
                <div className="mb-6">
                    <h2 className="text-xl font-semibold mb-2">설명</h2>
                    <p className="text-gray-700">{/*{postData.description}*/}</p>
                </div>

                {/* 분석글 */}
                <div className="mb-6">
                    <h2 className="text-xl font-semibold mb-2">분석</h2>
                    {/* <p className="text-gray-700">{postData.analysis}</p>*/}
                    {/*<AnalysisBoardComponent analysisList={postData.analysisList} />*/}
                    <AnalysisBoardComponent />

                </div>
            </div>

            {/* 오른쪽: 실시간 댓글창 */}
            <div className="w-1/4 p-4 bg-gray-100 rounded-lg shadow-lg">
                <h2 className="text-xl font-semibold mb-4">실시간 댓글</h2>
                <div className="h-96 overflow-y-auto">
                    댓글을 보여줄 곳
                    {/*  {postData.comments && postData.comments.length > 0 ? (
                        postData.comments.map((comment, index) => (
                            <div key={index} className="mb-2">
                                <div className="font-bold">{comment.writer}</div>
                                <div className="text-gray-600">{comment.text}</div>
                            </div>
                        ))
                    ) : (
                        <div>댓글이 없습니다.</div>
                    )}*/}
                </div>

                댓글 입력창
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
        </div>
    );
}


export default ReadComponent;