import React from 'react';
import AddComponent from "../../components/board/AddComponent";
import ListLayout from "../../layouts/ListLayout";
import {useParams} from "react-router-dom";


const AddPage = () => {
    const {category} = useParams()

    return (
        <ListLayout>
            <div className="p-4 w-full bg-white ">
                <div className="text-3xl font-extrabold">

                </div>
                <AddComponent category = {category} />
            </div>
        </ListLayout>
    );
}

export default AddPage;