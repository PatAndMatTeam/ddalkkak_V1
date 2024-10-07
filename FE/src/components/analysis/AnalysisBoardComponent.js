import React, { useEffect, useState } from 'react';
import useCustomMove from "../../hooks/useCustomMove";
import { getList } from "../../api/todoApi";

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
    current: 0
};

function AnalysisBoardComponent(props) {
    const [serverData, setServerData] = useState(initState);
    const { page, size, refresh, moveToList, moveToRead, moveToAnlyRead } = useCustomMove();

    useEffect(() => {
        getList({ page, size }).then(data => {
            setServerData(data);
        });
    }, [page, size, refresh]);

    return (
        <div className="border-2 border-blue-100 mt-10 mr-2 ml-2">
            {/* Categories */}
           {/* <section className="categories flex justify-center space-x-4 my-4">
                <div className="category"><a href="#" className="font-bold">인기🔥</a></div>
                <div className="category current"><a href="#" className="font-bold">전체</a></div>
                <div className="category"><a href="#" title="자유">자유</a></div>
                <div className="category"><a href="#" title="국축">국축</a></div>
                <div className="category"><a href="#" title="해축">해축</a></div>
            </section>*/}

            {/* Board List */}
            <section id="boardList" className="p-4">
                {/*    {serverData.dtoList.map(post => (
                    <a key={post.tno} href={`/football/${post.tno}`}
                       className="item flex items-center p-4 mb-4 border-b">
                        <div className="image w-16 h-16 mr-4">
                            <img
                                src={post.thumbnailUrl || "/assets/empty.png"}
                                alt={post.title}
                                className="w-full h-full object-cover"
                            />
                        </div>
                        <div className="info flex-1">
                            <div className="titleContainer flex items-center justify-between">
                                <span className="subCategory">{post.category}</span>
                                <span className="title text-lg font-bold">{post.title}</span>
                                <span className="commentCount text-sm">({post.comments})</span>
                            </div>
                            <div className="etc flex items-center text-gray-500 text-sm mt-1">
                                <span className="nickName">{post.nickName}</span>
                                <span className="dot mx-2">•</span>
                                <span className="datetime">{post.date}</span>
                                <span className="dot mx-2">•</span>
                                <span className="viewCount"><i className="fa-regular fa-eye"></i> {post.views}</span>
                                <span className="dot mx-2">•</span>
                                <span className="likeCount"><i
                                    className="fa-regular fa-thumbs-up"></i> {post.likes}</span>
                            </div>
                        </div>
                    </a>
                ))}*/}

                <a className="item flex items-center p-4 mb-4 border-b">
                    <div className="image w-16 h-16 mr-4">
                        <img
                            src="https://i.namu.wiki/i/a6qqiwy-VyqwqSZmumu15J9Njn79fWY85wkWZ79ZzhbY_GZMLRy6EoxKlEpZus4xuXc6llrbug0_WOonmJgh1Q.svg"
                            alt="사우샘프턴 로고"
                        />
                    </div>
                    <div className="info flex-1">
                        <div className="titleContainer flex items-center justify-between">
                            <span className="subCategory">스포츠</span>
                            <span className="title text-lg font-bold">글제목</span>
                            <span className="commentCount text-sm">(댓글수)</span>
                        </div>
                        <div className="etc flex items-center text-gray-500 text-sm mt-1">
                            <span className="nickName">닉네임</span>
                            <span className="dot mx-2">•</span>
                            <span className="datetime">1시간전</span>
                            <span className="dot mx-2">•</span>
                            <span className="viewCount"><i className="fa-regular fa-eye"></i> 조회수</span>
                            <span className="dot mx-2">•</span>
                            <span className="likeCount"><i className="fa-regular fa-thumbs-up"></i> LikeCount</span>
                        </div>
                    </div>
                </a>
                <a className="item flex items-center p-4 mb-4 border-b">
                    <div className="image w-16 h-16 mr-4">
                        <img

                        />
                    </div>
                    <div className="info flex-1">
                        <div className="titleContainer flex items-center justify-between">
                            <span className="subCategory">스포츠</span>
                            <span className="title text-lg font-bold">글제목</span>
                            <span className="commentCount text-sm">(댓글수)</span>
                        </div>
                        <div className="etc flex items-center text-gray-500 text-sm mt-1">
                            <span className="nickName">닉네임</span>
                            <span className="dot mx-2">•</span>
                            <span className="datetime">1시간전</span>
                            <span className="dot mx-2">•</span>
                            <span className="viewCount"><i className="fa-regular fa-eye"></i> 조회수</span>
                            <span className="dot mx-2">•</span>
                            <span className="likeCount"><i className="fa-regular fa-thumbs-up"></i> LikeCount</span>
                        </div>
                    </div>
                </a>
                <a className="item flex items-center p-4 mb-4 border-b">
                    <div className="image w-16 h-16 mr-4">
                        <img

                        />
                    </div>
                    <div className="info flex-1">
                        <div className="titleContainer flex items-center justify-between">
                            <span className="subCategory">스포츠</span>
                            <span className="title text-lg font-bold">글제목</span>
                            <span className="commentCount text-sm">(댓글수)</span>
                        </div>
                        <div className="etc flex items-center text-gray-500 text-sm mt-1">
                            <span className="nickName">닉네임</span>
                            <span className="dot mx-2">•</span>
                            <span className="datetime">1시간전</span>
                            <span className="dot mx-2">•</span>
                            <span className="viewCount"><i className="fa-regular fa-eye"></i> 조회수</span>
                            <span className="dot mx-2">•</span>
                            <span className="likeCount"><i className="fa-regular fa-thumbs-up"></i> LikeCount</span>
                        </div>
                    </div>
                </a>
            </section>


            {/* Pagination */}
            <section className="pagination flex justify-center my-6">
                <div className="prev mr-2">

                </div>
                {/* <div className="number flex space-x-2">
                    {serverData.pageNumList.map(pageNum => (
                        <a key={pageNum} href={`?page=${pageNum}`}
                           className={`px-2 ${pageNum === serverData.current ? 'selected' : 'notSelected'}`}>
                            {pageNum}
                        </a>
                    ))}
                </div>*/}
                {/*  <div className="next ml-2">
                    {serverData.next && <a href={`?page=${serverData.nextPage}`}>다음</a>}
                </div>*/}
            </section>

            {/* Search and Write */}
            <div className="searchAndWrite flex justify-between items-center p-4">
                <form action="/football" className="flex space-x-2">
                    <select name="searchType" className="border p-2">
                        <option value="title">제목</option>
                        <option value="titleAndContent">제목 + 내용</option>
                        <option value="nickName">글쓴이</option>
                    </select>
                    <input type="text" name="keyword" maxLength="50" className="border p-2" placeholder="검색어 입력"/>
                    <button type="submit" className="bg-blue-500 text-white px-4 py-2">검색</button>
                </form>
                <div className="write">
                    <button className="bg-green-500 text-white px-4 py-2">글쓰기</button>
                </div>
            </div>
        </div>
    );
}

export default AnalysisBoardComponent;