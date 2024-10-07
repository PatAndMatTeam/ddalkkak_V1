import React, { useState } from 'react';
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";
import { postAdd } from "../../api/todoApi";

const initState = {
    category: '정치',  // 카테고리 기본값 설정
    title: '',
    content: '',
    writer: 'LJH',
}

function AddComponent(props) {
    const [board, setBoard] = useState({ ...initState });
    const [result, setResult] = useState(null);
    const [files, setFiles] = useState([]);
    const [imageA, setImageA] = useState(null);
    const [imageB, setImageB] = useState(null);

    const { moveToList } = useCustomMove();

    const handleChangeTodo = (e) => {
        board[e.target.name] = e.target.value;
        setBoard({ ...board });
    }

    const handleClickAdd = () => {
        postAdd(board, files).then(data => {
            setBoard({ ...initState });
            setFiles([]);
            setImageA(null);
            setImageB(null);
            setResult(data);  // 결과 값 설정
        }).catch(error => {
            console.error("Error adding post", error);
        });
    }

    const closeModal = () => {
        setResult(null);
        moveToList();  // 리스트로 이동
    }

    const handleImageUpload = (event, setImage, fileKey) => {
        const file = event.target.files[0];
        const img = new Image();
        const objectUrl = URL.createObjectURL(file);

        img.src = objectUrl;
        img.onload = () => {
            const newFile = {
                key: fileKey,
                file: file,
                width: img.width,
                height: img.height
            };

            setFiles(prevFiles => {
                const updatedFiles = prevFiles.filter(f => f.key !== fileKey);
                return [...updatedFiles, newFile];
            });

            setImage(file);
            URL.revokeObjectURL(objectUrl);  // 메모리 해제
        };
    };

    return (
        <div className="border-2 border-sky-200 mt-10 m-2 p-6 shadow-lg rounded-lg bg-white">
            <div className="flex justify-center mb-6">
                <div className="w-full max-w-md">
                    <div className="mb-4 flex items-center">
                        <label className="block text-gray-700 font-bold w-1/3 text-right mr-4">카테고리</label>
                        <input
                            className="w-2/3 p-3 rounded border border-gray-300 shadow-sm bg-gray-100 text-gray-500"
                            name="category"
                            type="text"
                            value={board.category}
                            disabled
                        />
                    </div>

                    <div className="mb-4 flex items-center">
                        <label className="block text-gray-700 font-bold w-1/3 text-right mr-4">제목</label>
                        <input
                            className="w-2/3 p-3 rounded border border-gray-300 shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
                            name="title"
                            type="text"
                            value={board.title}
                            onChange={handleChangeTodo}
                        />
                    </div>

                    <div className="mb-4 flex items-center">
                        <label className="block text-gray-700 font-bold w-1/3 text-right mr-4">설명</label>
                        <input
                            className="w-2/3 p-3 rounded border border-gray-300 shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
                            name="content"
                            type="text"
                            value={board.content}
                            onChange={handleChangeTodo}
                        />
                    </div>

                    <div className="mb-4 flex items-center">
                        <label className="block text-gray-700 font-bold w-1/3 text-right mr-4">A 이름 </label>
                        <input
                            className="w-2/3 p-3 rounded border border-gray-300 shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
                            //name="content"
                            type="text"
                          //  value={board.content}
                         //   onChange={handleChangeTodo}
                        />
                    </div>
                    <div className="mb-4 flex items-center">
                        <label className="block text-gray-700 font-bold w-1/3 text-right mr-4">A의 메인 이미지</label>
                        <input
                            className="w-2/3"
                            type="file"
                            accept="image/*"
                            onChange={(e) => handleImageUpload(e, setImageA, 'A')}
                        />
                    </div>
                    {imageA && (
                        <img
                            src={URL.createObjectURL(imageA)}
                            alt="A의 메인 사진"
                            className="mt-2 w-full h-64 object-cover rounded shadow-md"
                        />
                    )}
                    <div className="mb-4 flex items-center">
                        <label className="block text-gray-700 font-bold w-1/3 text-right mr-4">B 이름 </label>
                        <input
                            className="w-2/3 p-3 rounded border border-gray-300 shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
                         //   name="content"
                            type="text"
                          //  value={board.content}
                          //  onChange={handleChangeTodo}
                        />
                    </div>
                    <div className="mb-4 flex items-center">
                        <label className="block text-gray-700 font-bold w-1/3 text-right mr-4">B의 메인 이미지</label>
                        <input
                            className="w-2/3"
                            type="file"
                            accept="image/*"
                            onChange={(e) => handleImageUpload(e, setImageB, 'B')}
                        />
                    </div>
                    {imageB && (
                        <img
                            src={URL.createObjectURL(imageB)}
                            alt="B의 메인 사진"
                            className="mt-2 w-full h-64 object-cover rounded shadow-md"
                        />
                    )}

                    <div className="flex justify-end">
                        <button
                            type="button"
                            className="px-6 py-3 bg-blue-500 text-white rounded-lg shadow-md hover:bg-blue-600 transition duration-300"
                            onClick={handleClickAdd}
                        >
                            ADD
                        </button>
                    </div>
                </div>
            </div>

            {result && (
                <ResultModal
                    title={'Add Result'}
                    content={`새 게시글이 추가되었습니다: ${result}`}
                    callbackFn={closeModal}
                />
            )}
        </div>
    );
}

export default AddComponent;