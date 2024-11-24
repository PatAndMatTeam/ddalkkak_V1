import React, { useCallback } from "react";
import { createSearchParams, useNavigate, useParams, useSearchParams } from "react-router-dom";
import ReadComponent from "../../components/board/ReadComponent";
import ListLayout from "../../layouts/ListLayout";
import ListComponent from "../../components/board/ListComponent";

const ReadPage = () => {

    const {tno,category} = useParams()

    return (
        <ListLayout>
            <div className="p-4 w-full bg-white ">
                <div className="text-3xl font-extrabold">

                </div>
                <ReadComponent tno = {tno} category = {category} />
            </div>
        </ListLayout>


    );

}

export default ReadPage;
