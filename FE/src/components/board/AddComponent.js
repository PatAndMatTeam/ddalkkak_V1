import React, { useState } from 'react';
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";
import { postAdd } from "../../api/todoApi";
import { getKoreanCategory } from "../../hooks/categoryUtils";
import '../analysis/FormStyles.css'; // 스타일 파일 추가

function AddComponent({category}) {
    const koreanCategory = getKoreanCategory(category);
    const initState = {
        category: category,
        title: '',
        content: '',
        writer: 'LJH',
    };

    const [board, setBoard] = useState({ ...initState });
    const [fileInfo, setFileInfo] = useState([]);  // 파일 정보는 배열로 관리
    const [result, setResult] = useState(null);
    const [files, setFiles] = useState([]);  // 이미지 파일 배열
    const [imageA, setImageA] = useState(null);
    const [imageB, setImageB] = useState(null);
    const [fileTitleA, setFileTitleA] = useState(''); // A 이름 상태 추가
    const [fileTitleB, setFileTitleB] = useState(''); // B 이름 상태 추가

    const { moveToCategory } = useCustomMove(); // 리스트로 이동하는 함수

    // 게시글 입력 값 처리
    const handleChangeTodo = (e) => {
        setBoard(prevBoard => ({
            ...prevBoard,
            [e.target.name]: e.target.value
        }));
    };

    // 글 등록 요청 처리
    const handleClickAdd = () => {

        // postAdd 호출 (board, files, fileInfo 전달)
        postAdd(board, files, fileInfo)
            .then(response => {
                if (response.status === 201) {
                    console.log('Data received:', response.data);
                    setBoard({ ...initState });  // 폼 초기화
                    setFiles([]);  // 파일 초기화
                    setImageA(null);
                    setImageB(null);
                    setFileInfo([]);  // fileInfo 초기화
                    setResult(response.status);  // 결과 상태 설정
                }
            })
            .catch(error => {
                console.error("Error adding post", error);
            });
    };

    const closeModal = () => {
        setResult(null);  // 모달 닫기
        moveToCategory(category);  // 리스트 화면으로 이동
    };

    // 이미지 업로드 처리 및 메타 정보 저장
    const handleImageUpload = (event, setImage, fileKey, fileTitle) => {
        const file = event.target.files[0];
        const img = new Image();
        const objectUrl = URL.createObjectURL(file);

        img.src = objectUrl;
        img.onload = () => {
            const newFile = {
                key: fileKey,
                file: file,
                fileTitle: fileTitle,  // A 또는 B 이름을 fileTitle로 설정
                width: img.width,
                height: img.height
            };

            const newFileInfo = {
                key: fileKey,
                fileTitle: fileTitle,  // A 또는 B 이름을 fileTitle로 설정
                width: img.width,
                height: img.height
            };

            setFiles(prevFiles => {
                const updatedFiles = prevFiles.filter(f => f.key !== fileKey);
                return [...updatedFiles, newFile];  // 파일 배열 업데이트
            });

            setFileInfo(prevFileInfo => {
                const updatedFileInfo = prevFileInfo.filter(f => f.key !== fileKey);
                return [...updatedFileInfo, newFileInfo];  // 파일 정보 배열 업데이트
            });

            setImage(file);  // 이미지 상태 업데이트
            URL.revokeObjectURL(objectUrl);  // 메모리 해제
        };
    };

    return (
        <div className="form-container">
            <h1 className="form-header">글 작성</h1>
            <div className="form-content">
                <div className="form-group">
                    <label className="form-label">카테고리</label>
                    <input
                        className="form-input"
                        name="category"
                        type="text"
                        value= {koreanCategory}
                        disabled
                    />
                </div>

                {/* 제목 입력 */}
                <div className="form-group">
                    <label className="form-label">제목</label>
                    <input
                        type="text"
                        className="form-input"
                        name="title"
                        placeholder="제목을 입력하세요"
                        value={board.title}
                        onChange={handleChangeTodo}
                    />
                </div>

                {/* 설명 입력 */}
                <div className="form-group">
                    <label className="form-label">설명</label>
                    <input
                        className="form-input"
                        placeholder="설명을 입력하세요"
                        name="content"
                        type="text"
                        value={board.content}
                        onChange={handleChangeTodo}
                    />
                </div>

                {/* A 이름 입력 */}
                <div className="form-group">
                    <label className="form-label">A 이름</label>
                    <input
                        className="form-input"
                        placeholder="A 이름을 입력하세요"
                        type="text"
                        value={fileTitleA}  // A 이름 상태값
                        onChange={(e) => setFileTitleA(e.target.value)}  // A 이름 업데이트
                    />
                </div>

                {/* A 이미지 업로드 */}
                <div className="form-group">
                    <label className="form-label">A의 메인 이미지</label>
                    <input
                        type="file"
                        className="form-input"
                        accept="image/*"
                        onChange={(e) => handleImageUpload(e, setImageA, 'A', fileTitleA)}  // A 이름을 fileTitle로 전달
                    />
                </div>
                {imageA && (
                    <img
                        src={URL.createObjectURL(imageA)}
                        alt="A의 메인 사진"
                        className="uploaded-image"
                    />
                )}

                {/* B 이름 입력 */}
                <div className="form-group">
                    <label className="form-label">B 이름</label>
                    <input
                        className="form-input"
                        placeholder="B 이름을 입력하세요"
                        type="text"
                        value={fileTitleB}  // B 이름 상태값
                        onChange={(e) => setFileTitleB(e.target.value)}  // B 이름 업데이트
                    />
                </div>

                {/* B 이미지 업로드 */}
                <div className="form-group">
                    <label className="form-label">B의 메인 이미지</label>
                    <input
                        type="file"
                        className="form-input"
                        accept="image/*"
                        onChange={(e) => handleImageUpload(e, setImageB, 'B', fileTitleB)}  // B 이름을 fileTitle로 전달
                    />
                </div>
                {imageB && (
                    <img
                        src={URL.createObjectURL(imageB)}
                        alt="B의 메인 사진"
                        className="uploaded-image"
                    />
                )}
                <button type="button" className="submit-button" onClick={handleClickAdd}>
                    등록
                </button>
            </div>
            {result && (
                <ResultModal
                    title={koreanCategory}
                    content={`새 글이 등록되었습니다.`}
                    callbackFn={closeModal}
                />
            )}
        </div>
    );
}

export default AddComponent;