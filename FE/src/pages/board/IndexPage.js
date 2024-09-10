import React from "react";
import {Outlet, useNavigate} from "react-router-dom";
import {useCallback} from "react";
import ListLayout from "../../layouts/ListLayout";


function IndexPage(props) {

    const navigate = useNavigate()



    return (
        <ListLayout>
            <div className="flex flex-wrap w-full"> <Outlet/> </div>
        </ListLayout>
    );
}
export default IndexPage;