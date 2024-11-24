import React from 'react';
import ListLayout from "../../layouts/ListLayout";
import { useParams, } from 'react-router-dom';
import AnalysisReadComponent from "../../components/analysis/AnalysisReadComponent";

const AnlyReadPage = () =>{
    const { category, tno ,anlyId} = useParams();  // URL 경로에서 category와 tno를 가져옴
    return (
        <ListLayout>
            <div className="p-4 w-full bg-white">
                <AnalysisReadComponent category={category} tno={tno} anlyId={anlyId} />
            </div>
        </ListLayout>
    );
};

export default AnlyReadPage;


