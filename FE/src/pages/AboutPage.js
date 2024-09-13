import React from 'react';
import {Link} from "react-router-dom";
import BasicLayout from "../layouts/BasicLayout";
import VsWriteLayout from "../layouts/vsWriteLayout";
import AddComponent from "../components/board/AddComponent";

function AboutPage(props) {
    return (
        <VsWriteLayout>
            <div className="p-4 w-full bg-white">
                <div className="text-3xl font-extrabold">
                    Add Page
                </div>
                <AddComponent/>
            </div>
        </VsWriteLayout>
    );

}

export default AboutPage;
