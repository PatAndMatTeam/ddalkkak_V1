

//함수를 만드는 이유는 이런저런 지저분한 코드를 만들어야 하는데 여기에다가 간단하게 만들어준다
import {createSearchParams, useNavigate, useSearchParams} from "react-router-dom";
import {useState} from "react";

//초기화 함수
const getNum = (param, defaultValue) => {

    if(!param) {
        return defaultValue
    }
    return parseInt(param)
}


const useCustomMove = () =>{
    const navigate = useNavigate()

    const [refresh,setRefresh] = useState(false)

    //read에서 list로 이동할때 수정이나 삭제에도 필요하고 page에서도 이동하는 기능이 중요하다.
    //이동하는 기능자체는 많이 필요하니까 로직을 묶어서 하나로 사용한다.

    const [queryParams] = useSearchParams()  //url &로 넘어오는 값

    const page = getNum(queryParams.get('page'),1)  //페이값 없으면 1 있으면 page값
    const size = getNum(queryParams.get('size'),10)  // 없으면 10 size있으면 size값
    console.log('page:', page); // 콘솔 로그 추가
    console.log('size:', size); // 콘솔 로그 추가

    //page=3&size=10
    const queryDefault = createSearchParams({page,size}).toString() //page=3&size=10 이런식으로 만들어준다.

    const moveToList =(pageParam) => {
        let queryStr = ""
        if(pageParam) {
            const pageNum = getNum(pageParam.page, 1)
            const sizeNum = getNum(pageParam.size, 10)
            console.log("있음")
             queryStr = createSearchParams({page:pageNum, size: sizeNum}).toString()
        }else {
            queryStr = queryDefault
        }
        console.log('Navigating to list with query:', queryStr); // 콘솔 로그 추가

        setRefresh(!refresh)

        navigate({pathname:'../list',search:queryStr})
    }


    const moveToModify = (num) => {
        console.log('Navigating to modify with number:', num); // 콘솔 로그 추가
        navigate({pathname:`../modify/${num}`,
            search:queryDefault})
    }


    //tno로 안받고 num으로 받는이유 다음에 어떤 게시판을 만들때
    //다 같이 쓸수있게
    const moveToRead = (num) => {
        navigate({pathname:`../read/${num}`,
            search:queryDefault})
    }





//이 구문은 객체를 반환합니다.
// 반환되는 객체는 moveTolist라는 프로퍼티를 가지고 있으며, 그 값은 moveTolist 함수입니다.
// 즉, 이 코드는 { moveTolist: moveTolist }와 같습니다.
    //만약 useCustomMove에서 다른 함수나 값을 함께 반환하고 싶다면 객체를 반환하는 것이 더 적합합니다.
    // 단순히 하나의 함수만 반환하고 사용할 계획이라면 함수 자체를 반환하는 것이 더 간단하고 직관적입니다.
    return {moveToList,moveToModify,moveToRead,page,size , refresh}
}




//외부에서 사용할 수 있게 useCustomMove함수르르 export default
export default useCustomMove