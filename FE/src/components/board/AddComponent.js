import React, {useState} from 'react';
import useCustomMove from "../../hooks/useCustomMove";
import ResultModal from "../common/ResultModal";
import {postAdd} from "../../api/todoApi";

const initState = {
    title: '',
    writer: '',
    dueDate: ''
}

function AddComponent(props) {
    const [todo, setTodo] = useState({...initState})
    const [result, setResult] = useState(null);
    const [imageA, setImageA] = useState(null);
    const [imageB, setImageB] = useState(null);

    const {moveToList} = useCustomMove()

    const handleChangeTodo = (e) => {
        todo[e.target.name] = e.target.value
        setTodo({...todo})
    }

    const handleClickAdd = () => {
        postAdd(todo).then(data => {
            setResult(result.TNO)
            setTodo({...initState})
        })
    }

    const closeModal = () => {
        setResult(null)
        moveToList()
    }

    const handleImageUpload = (event, setImage) => {
        const file = event.target.files[0];
        setImage(file);
    };

    return (
        <div className="border-2 border-sky-200 mt-10 m-2 p-6 shadow-lg rounded-lg bg-white">
            <div className="flex justify-center mb-6">
                <div className="w-full max-w-md">
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">제목</label>
                        <input className="w-full p-3 rounded border border-gray-300 shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
                               name="title"
                               type="text"
                               value={todo.title}
                               onChange={handleChangeTodo}/>
                    </div>

                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">설명</label>
                        <input className="w-full p-3 rounded border border-gray-300 shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
                               name="writer"
                               type="text"
                               value={todo.writer}
                               onChange={handleChangeTodo}/>
                    </div>

                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">Due Date</label>
                        <input className="w-full p-3 rounded border border-gray-300 shadow-sm focus:ring-2 focus:ring-blue-500 focus:outline-none"
                               name="dueDate"
                               type="date"
                               value={todo.dueDate}
                               onChange={handleChangeTodo}/>
                    </div>

                    {/* Image Upload for A */}
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">A의 메인 이미지</label>
                        <input type="file" accept="image/*" onChange={(e) => handleImageUpload(e, setImageA)}/>
                        {imageA && <img src={URL.createObjectURL(imageA)} alt="A의 메인 사진"
                                        className="mt-2 w-full h-64 object-cover rounded shadow-md"/>}
                    </div>

                    {/* Image Upload for B */}
                    <div className="mb-4">
                        <label className="block text-gray-700 font-bold mb-2">B의 메인 이미지</label>
                        <input type="file" accept="image/*" onChange={(e) => handleImageUpload(e, setImageB)}/>
                        {imageB && <img src={URL.createObjectURL(imageB)} alt="B의 메인 사진"
                                        className="mt-2 w-full h-64 object-cover rounded shadow-md"/>}
                    </div>

                    {/* Add Button */}
                    <div className="flex justify-end">
                        <button type="button"
                                className="px-6 py-3 bg-blue-500 text-white rounded-lg shadow-md hover:bg-blue-600 transition duration-300"
                                onClick={handleClickAdd}>
                            ADD
                        </button>
                    </div>
                </div>
            </div>

            {result ? <ResultModal
                    title={'Add Result'}
                    content={`New ${result} Added`}
                    callbackFn={closeModal}/>
                : null}
        </div>
    );
}

export default AddComponent;