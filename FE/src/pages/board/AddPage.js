import React from 'react';
import AddComponent from "../../components/board/AddComponent";
import ReadComponent from "../../components/board/ReadComponent";
import ListLayout from "../../layouts/ListLayout";

function AddPage(props) {
    return (
        <ListLayout>
            <div className="p-4 w-full bg-white ">
                <div className="text-3xl font-extrabold">

                </div>
                <AddComponent />
            </div>
        </ListLayout>
    );
}

export default AddPage;