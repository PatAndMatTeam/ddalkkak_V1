import React from 'react';


import ListLayout from "../layouts/ListLayout";

function MainPage(props) {
    return (
        //basic레이아웃 안에 들어가있는게 {children} 으로 전달된다.
        <ListLayout>
            <div className={'text-3xl'}> Main Page</div>
        </ListLayout>
    );
}

export default MainPage;