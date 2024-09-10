import React from 'react';
import {useSearchParams} from "react-router-dom";
import ListComponent from "../../components/board/ListComponent";

function ListPage(props) {
/*
    const [queryParams] = useSearchParams() //url 검색창에 따라오는 & ? 검색에 캐치하는 함수

    //url 매개변수내용 가져오기
    const page = queryParams.get('page') ? parseInt(queryParams.get('page')) : 1
    const size = queryParams.get('size') ? parseInt(queryParams.get('page')) : 10*/


    return (
        <div className="p-4 w-full bg-white ">
            <div className="text-3xl font-extrabold">
                 List Page Component
            </div>

            <ListComponent/>
        </div>
    );
}
export default ListPage;
