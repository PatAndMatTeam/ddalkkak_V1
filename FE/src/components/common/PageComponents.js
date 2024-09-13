import React from 'react';
                        //받을 데이터, 이동할 페이지
const PageComponent = ({serverData, movePage}) => {

    return (
        //severData.prev,pageNumList, next
        <div className="m-6 flex justify-center">

            {serverData.prev ?
                <div
                    className="m-2 p-2 w-16 text-center  font-bold text-blue-400 "
                    onClick={() => {
                        console.log('Prev clicked');
                        movePage({ page: serverData.prevPage });
                    }}>
                    Prev </div> : <></>}

            {serverData.pageNumList.map(pageNum =>
                <div
                    key={pageNum}
                    className={ `m-2 p-2 w-12  text-center rounded shadow-md text-white ${serverData.current === pageNum? 'bg-gray-500':'bg-blue-400'}`}
                    onClick={() => {
                        console.log('Page number clicked', pageNum);
                        movePage({ page: pageNum });
                    }}
                >
                    {pageNum}
                </div>

            )}

            {serverData.next ?
                <div
                    className="m-2 p-2 w-16 text-center font-bold text-blue-400"
                    onClick={() => {
                        console.log('Next clicked');
                        movePage({ page: serverData.nextPage });
                    }}
                >
                    Next
                </div> : <></>}

        </div>



    );
}

export default PageComponent;
