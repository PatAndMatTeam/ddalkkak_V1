import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import useCustomMove from "../../hooks/useCustomMove";
import {getList, increaseVisitCount} from "../../api/todoApi";

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

function ListComponent({ category }) {
    const [serverData, setServerData] = useState(initState);
    const { refresh, moveToRead } = useCustomMove();

    useEffect(() => {
        getList({category}).then(data => {
            setServerData(data);
        });
    }, [refresh, category]);  // 의존성 배열에 'category' 추가

    //조회수증가
    const handleTitleClick = async (category, anlyId ) => {
        const success = await increaseVisitCount(anlyId);
        if (success) {
            moveToRead(category, anlyId);
        } else {
            alert("조회수 증가에 실패했습니다.");
        }
    };

    return (
        <div className="border-2 mt-10 mr-2 ml-2">
            <StyledTable>
                <thead>
                <tr>
                    <StyledHeader colSpan="5">전체 게시글</StyledHeader>
                </tr>
                </thead>
                <tbody>
                {serverData.infos && serverData.infos.length > 0 ? (
                    serverData.infos.map((item, index) => {
                        const base64Image0 = item.files && item.files.length > 0
                            ? `data:image/jpeg;base64,${item.files[0].data}`
                            : "/placeholder.jpg";
                        const base64Image1 = item.files && item.files.length > 1
                            ? `data:image/jpeg;base64,${item.files[1].data}`
                            : "/placeholder.jpg";

                        return (
                            <StyledRow
                                key={index}
                                onClick={() => handleTitleClick(category, item.id)
                            }
                            >
                                <StyledTdImage>
                                    <div className="flex items-center justify-center relative">
                                        <StyledImage
                                            src={base64Image0}
                                            alt="Image A"
                                            className="w-20 h-20 object-cover rounded-l shadow-md"
                                        />
                                        <StyledVsIcon>
                                            VS
                                        </StyledVsIcon>
                                        <StyledImage
                                            src={base64Image1}
                                            alt="Image B"
                                            className="w-20 h-20 object-cover rounded-r shadow-md"
                                        />
                                    </div>
                                </StyledTdImage>
                                <StyledTd>
                                    <div>
                                        <StyledTitle>{item.title}</StyledTitle>
                                        <StyledMetaData>
                                            작성자: {item.writer} &nbsp;•&nbsp;
                                            조회수: {item.visited || "0"} &nbsp;•&nbsp;
                                            작성일: {new Date(item.modifyDate).toLocaleDateString() || "???"}
                                        </StyledMetaData>
                                    </div>
                                </StyledTd>
                            </StyledRow>
                        );
                    })
                ) : (
                    <tr>
                        <StyledTd colSpan="5" className="text-center p-4">Loading...</StyledTd>
                    </tr>
                )}
                </tbody>
            </StyledTable>
        </div>
    );
}

export default ListComponent;

// Styled-components 추가
const StyledTable = styled.table`
    width: 100%;
    border-collapse: collapse;
    font-family: 'Roboto', sans-serif;
`;

const StyledHeader = styled.th`
    background-color: #f3f4f6;
    padding: 16px;
    text-align: left;
    font-size: 1.5rem;
    font-weight: 700;
    font-family: 'Roboto', sans-serif;
`;

const StyledRow = styled.tr`
    cursor: pointer;
    transition: background-color 0.3s ease-in-out; /* 배경색 전환 효과 추가 */

    &:hover {
        background-color: #efeaea; /* 마우스 올리면 배경색 변경 */
    }
`;

const StyledTd = styled.td`
    padding: 12px 16px;
    font-size: 1rem;
    font-weight: 400;
    text-align: left;
    font-family: 'Roboto', sans-serif;
`;

const StyledTdImage = styled.td`
    padding: 12px 16px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
`;

const StyledImage = styled.img`
    width: 150px; /* 사진의 크기 */
    height: 100px;
    object-fit: cover;
    border-radius: 8px;
    margin-right: 5px; /* 이미지 사이 간격 */
`;

const StyledVsIcon = styled.div`
    position: absolute; /* VS 아이콘을 이미지 위에 겹치게 */
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 1.1rem;
    font-weight: bold;
    color: rgba(0, 0, 0, 0.7);
    background-color: #fff; /* VS 아이콘 배경을 살짝 투명하게 */
    padding: 5px 10px;
    border-radius: 50%; /* 둥근 아이콘 모양 */
    z-index: 10; /* 다른 요소보다 위에 오도록 설정 */
`;

const StyledTitle = styled.div`
    font-weight: bold;
    font-size: 1.1rem;
    color: #111;
`;

const StyledMetaData = styled.div`
    margin-top: 5px;
    font-size: 0.85rem;
    color: #777;
    display: flex;
    align-items: center;
`;