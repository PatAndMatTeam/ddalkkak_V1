import React, { useEffect, useState } from 'react';
import useCustomMove from "../../hooks/useCustomMove";
import { getList } from "../../api/todoApi";

const initState = {
    dtoList: [],
    pageNumList: [],
    pageRequestDTO: null,
    prev: false,
    next: false,
    totalCount: 0,
    prevPage: 0,
    nextPage: 0,
    totalPage: 0,
    current: 0
};

function ListComponent({category}) {
    const [serverData, setServerData] = useState(initState);
    const { page, size, refresh, moveToRead } = useCustomMove();

    useEffect(() => {
        getList({ page, size }).then(data => {
            setServerData(data);
        });
    }, [page, size, refresh]);

    return (
        <div className="border-2 border-blue-100 mt-10 mr-2 ml-2">
            <div className="flex flex-wrap mx-auto justify-center p-6 gap-4">
                {serverData.infos && serverData.infos.length > 0 ? (
                    serverData.infos.map((item, index) => (
                        <div
                            key={index}
                            className="flex flex-col items-center w-[18%] min-w-[180px] p-4 bg-gray-100 shadow-md rounded hover:bg-gray-200 cursor-pointer"
                            onClick={() => moveToRead(category,item.id)}
                        >
                            <img
                                src={item.thumbnail || "/placeholder.jpg"} // 썸네일이 없을 경우 placeholder 이미지 사용
                                alt={item.title}
                                className="w-full h-40 object-cover rounded"
                            />
                            <div className="font-semibold text-lg mt-2">{item.title}</div>
                        </div>
                    ))
                ) : (
                    <div>Loading...</div>
                )}
            </div>
        </div>
    );
}

export default ListComponent;