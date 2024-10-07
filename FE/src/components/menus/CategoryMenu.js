import React, { useState } from "react";
import styled from "styled-components";
import useCustomMove from "../../hooks/useCustomMove";

const categories = [
    {
        title: "MENU1",
        items: [
            { name: "롤", value: "lol" },
            { name: "정치", value: "politics" },
            { name: "축구", value: "football" },
            { name: "submenu04", value: "submenu04" },
            { name: "submenu05", value: "submenu05" }
        ]
    },
    {
        title: "MENU2",
        items: [
            { name: "롤", value: "lol" },
            { name: "정치", value: "politics" },
            { name: "축구", value: "football" },
            { name: "submenu04", value: "submenu04" },
            { name: "submenu05", value: "submenu05" }
        ]
    },
    {
        title: "MENU3",
        items: [
            { name: "롤", value: "lol" },
            { name: "정치", value: "politics" },
            { name: "축구", value: "football" },
            { name: "submenu04", value: "submenu04" },
            { name: "submenu05", value: "submenu05" }
        ]
    },
    {
        title: "MENU4",
        items: [
            { name: "롤", value: "lol" },
            { name: "정치", value: "politics" },
            { name: "축구", value: "football" },
            { name: "submenu04", value: "submenu04" },
            { name: "submenu05", value: "submenu05" }
        ]
    },
    {
        title: "MENU5",
        items: ["submenu01", "submenu02", "submenu03", "submenu04", "submenu05"]
    }
];

const CategoryMenu = () => {


    const { moveToCategory } = useCustomMove();

    return (
        <Wrapper>
            <Menu>
                {categories.map((category, categoryIndex) => (
                    <MenuItem key={categoryIndex}>
                        <div>{category.title}</div>
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

// styled-components 사용
const Wrapper = styled.div`
    width: 100%;
    background-color: #f0f0f0;
    padding: 20px 0;
`;

const Menu = styled.ul`
  width: 800px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
`;

const MenuItem = styled.li`
    position: relative;
    width: 20%;
    text-align: center;
    line-height: 40px;
    background-color: #5778ff;
    transition: background-color 0.5s;

    &:hover {
        background-color: #94a9ff;

        ul {
            height: 250px;
            transition: height 1s;
        }
    }

    a {
        color: #fff;
        display: block;
        padding: 10px;
    }
`;

const SubMenu = styled.ul`
    position: absolute;
    top: 100%;
    left: 0;
    width: 100%;
    height: 0;
    overflow: hidden;
    background-color: #94a9ff;
    transition: height 1s;

    li {
        line-height: 50px;

        a {
            color: #fff;
            display: block;
        }
    }
`;