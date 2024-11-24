import React from "react";
import styled, { createGlobalStyle } from "styled-components";
import useCustomMove from "../../hooks/useCustomMove";

// Google Fonts를 추가하는 글로벌 스타일
const GlobalStyle = createGlobalStyle`
    @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;500&display=swap');
`;

// 카테고리 데이터
const categories = [
    {
        title: "스포츠",
        items: [
            { name: "축구", value: "football" },
            { name: "야구", value: "baseball" },
            { name: "농구", value: "basketball" },
            { name: "기타스포츠", value: "other_sports" }
        ]
    },
    {
        title: "게임",
        items: [
            { name: "롤", value: "lol" },
            { name: "스타", value: "star" },
            { name: "FPS", value: "fps" },
            { name: "RPG", value: "rpg" },
            { name: "모바일", value: "mobile" },
            { name: "기타게임", value: "other_game" }
        ]
    },
    {
        title: "문화생활",
        items: [
            { name: "배우&가수", value: "celebrity" },
            { name: "여자아이돌", value: "girlGroup" },
            { name: "남자아이돌", value: "boyGroup" },
            { name: "애니&만화", value: "cartoon" },
            { name: "웹툰", value: "webtoon" },
            { name: "캐릭터", value: "character" }
        ]
    },
    {
        title: "취미",
        items: [
            { name: "패션", value: "fashion" },
            { name: "운동&다이어트", value: "exercise" },
            { name: "영화&드라마", value: "movie" },
            { name: "음식&맛집", value: "food" },
            { name: "여행", value: "travel" },
            { name: "반려동물", value: "pet" },
            { name: "음악", value: "music" }
        ]
    },
    {
        title: "인방",
        items: [
            { name: "인터넷방송", value: "internet" },
            { name: "유튜버", value: "utuber" },
            { name: "버튜버", value: "vtuber" }
        ]
    },
];

// 카테고리 메뉴 컴포넌트
const CategoryMenu = () => {
    const { moveToCategory } = useCustomMove();

    return (
        <Wrapper>
            <Menu>
                {categories.map((category, categoryIndex) => (
                    <MenuItem key={categoryIndex}>
                        <button>{category.title}</button>
                        <SubMenu>
                            {category.items.map((item, itemIndex) => (
                                <li key={itemIndex}>
                                    <button onClick={() => moveToCategory(item.value)}>
                                        {item.name}
                                    </button>
                                </li>
                            ))}
                        </SubMenu>
                    </MenuItem>
                ))}
            </Menu>
        </Wrapper>
    );
};

export default CategoryMenu;

// 스타일 정의
// 카테고리 메뉴 스타일
const Wrapper = styled.div`
    width: 100%;
    background-color: #00796b;
    display: flex;
    justify-content: center;
`;

const Menu = styled.ul`
    width: 50%;
    margin: 0;
    display: flex;
    justify-content: space-between;
    padding: 0;
    z-index: 11;
`;

const MenuItem = styled.li`
    position: relative;
    flex-grow: 1;
    text-align: center;
    line-height: 30px;
    background-color: #00796b;
    transition: background-color 0.3s;
    font-size: 16px;
    font-weight: bold;
    color: #ffffff;
    padding: 0;
    margin: 0;
    z-index: 11;

    button {
        background: none;
        border: none;
        color: #ffffff;
        font-size: 16px;
        width: 100%;
        padding: 10px 0;
        cursor: pointer;
    }

    &:hover {
        background-color: #004d40;
    }
`;

const SubMenu = styled.ul`
    position: absolute;
    top: 100%;
    left: 0;
    width: 100%;
    height: 0;
    overflow: hidden;
    background-color: #00796b;
    border: 1px solid #ffffff;
    transition: height 0.5s ease;
    z-index: 11;

    li {
        line-height: 30px;
        padding: 0 10px;
        border-bottom: 1px dashed #004d40;

        &:last-child {
            border-bottom: none;
        }

        button {
            color: #ffffff;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 14px;
            font-family: 'Poppins', sans-serif;
            font-weight: 500;
            width: 100%;
            text-align: left;
            padding: 5px 0;
            transition: transform 0.3s, color 0.3s;
        }

        button:hover {
            background-color: #004d40;
            color: #f5f5f5;
            transform: scale(1.05);
        }
    }

    ${MenuItem}:hover & {
        height: auto;
        padding: 5px 0;
    }
`;
