import React, { useState } from 'react';
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";
import { postAdd } from "../../api/todoApi";

const initState = {
    category: '정치',  // 카테고리 기본값 '스포츠'
    title: '',
    content: '',
    writer: 'LJH',
    //dueDate: ''
}

function AddComponent(props) {
    const [board, setBoard] = useState({ ...initState });
    const [result, setResult] = useState(null);

    // 이미지 파일과 크기를 저장할 배열
    const [files, setFiles] = useState([]);
    const [imageA, setImageA] = useState(null);
    const [imageB, setImageB] = useState(null);

    const { moveToList } = useCustomMove();

    const handleChangeTodo = (e) => {
        board[e.target.name] = e.target.value;
        setBoard({ ...board });
    }

    const handleClickAdd = () => {
        // 게시글 데이터와 함께 파일 배열을 전송
        postAdd(board, files).then(data => {
            setBoard({ ...initState });
            setFiles([]);  // 파일 배열 초기화
            setImageA(null);
            setImageB(null);
        });
    }

    const closeModal = () => {
        setResult(null);
        moveToList();
    }

    // 이미지 업로드 핸들러: 파일과 크기를 files 배열에 추가
    const handleImageUpload = (event, setImage, fileKey) => {
        const file = event.target.files[0];
        const img = new Image();
        const objectUrl = URL.createObjectURL(file);

        img.src = objectUrl;
        img.onload = () => {
            const newFile = {
                key: fileKey,  // 파일을 구분하기 위한 키 값 (A or B)
                file: file,
                width: img.width,
                height: img.height
            };

            // 해당 key에 해당하는 파일을 업데이트하거나 새로 추가
            setFiles(prevFiles => {
                const updatedFiles = prevFiles.filter(f => f.key !== fileKey);
                return [...updatedFiles, newFile];
            });

            setImage(file); // 미리보기를 위해 이미지 파일 저장
            URL.revokeObjectURL(objectUrl);  // 메모리 해제
        };
    };

    return (
        <div className="border-2 border-sky-200 mt-10 m-2 p-6 shadow-lg rounded-lg bg-white">
            <div className="flex justify-center mb-6">
                <div className="w-full max-w-md">
                    {/* 카테고리 표시 */}
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

                    {/* 제목 입력 */}
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

                    {/* 설명 입력 */}
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

                    {/* A 이미지 업로드 */}
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

                    {/* B 이미지 업로드 */}
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

                    {/* 추가 버튼 */}
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

            {result ? (
                <ResultModal
                    title={'Add Result'}
                    content={`New ${result} Added`}
                    callbackFn={closeModal}
                />
            ) : null}
        </div>
    );
}

export default AddComponent;