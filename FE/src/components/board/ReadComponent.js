import React, {useEffect, useState} from 'react';
import {getOne} from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";

const initState = {
    tno:0,
    title:'',
    writer:'',
    dueDate:'',
    complete: false
}
                        //읽을 페이지
function ReadComponent({tno}) {

    //기본값을 주고시작하는게 좋지 그리고 데이터 들어오면 useEffect로 처리
    //비동기로 바뀐다는건 상태처리
    //리엑트의 컴포넌트는 상태가 변경되면 자동으로 렌더링 된다는 사실을 기억하셔야 되고
    //리엑트 컴포넌트는 이 useState는 원래 함수형 컴포넌트가 상태를 유지할수 없었자나요
    //근데 이제 함수형 컴포넌트가 상태를 유지할 때 쓰는거죠 이때 이제 useEffect랑 같이 결합해서 쓴다는거죠

    //   기존todo값 ,저장할todo값(바뀐값)   ( 초기값)
    const [todo, setTodo] = useState(initState);

//hooks으로 한줄로 빼버리면 편하다 이런거 할때는 커스텀 훅으로 빼주는게 좋다
    const {moveToList,moveToModify} = useCustomMove()


    //tno을 가져와서 번호가 안바뀌면 데이터가 바뀌었더라도 이 함수를 호출하지 않는거지 그래서 여러번 호출을 막는다
    //비동기 호출을 막는다
    //즉 tno을 가져와서 이값이 바뀐값으면 호출
    useEffect(() => {
        getOne(tno).then(data => {
            console.log("A"+ data)
            setTodo(data)
        })

    }, [tno]);



    return (
            <div className= "border-2 border-sky-200 mt-10 m-2 p-4">
                {makeDiv('Tno',todo.tno)}
                {makeDiv('Writer',todo.writer)}
                {makeDiv('Title',todo.title)}
                {makeDiv('Due Date',todo.dueDate)}
                {makeDiv('Complete',todo.complete ? 'Completed' : 'Not Yet')}

                {/* buttons .... start */}
                <div className="flex justify-end p-4">

                    <button type="button"
                            className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500"
                            onClick={() => moveToList()}
                    >
                        List
                    </button>

                    <button type="button"
                            className="rounded p-4 m-2 text-xl w-32 text-white bg-red-500"
                            onClick={() => moveToModify(todo.tno)}
                    >
                        Modify
                    </button>

                </div>
            </div>
    );
}

const makeDiv = (title, value) =>
    <div className='flex justify-center'>
        <div className='relative mb-4 flex w-full flex-wrap items-stretch'>
            <div className="w-1/5 p-6 text-right font-bold">{title}</div>
            <div className="w-4/5 p-6 rounded-r border border-solid shadow-md">
                {value}
            </div>
        </div>
    </div>

export default ReadComponent;