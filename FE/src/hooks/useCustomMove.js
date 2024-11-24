

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
    const title = queryParams.get('title')  // 없으면 10 size있으면 size값
/*    console.log('page:', page); // 콘솔 로그 추가
    console.log('size:', size); // 콘솔 로그 추가*/

    //page=3&size=10
    const queryDefault = createSearchParams({page,size}).toString() //page=3&size=10 이런식으로 만들어준다.
    const atitle = createSearchParams({title}).toString() //page=3&size=10 이런식으로 만들어준다.

    const moveToList =(category) => {
/*        let queryStr = ""
        if(pageParam) {
            const pageNum = getNum(pageParam.page, 1)
            const sizeNum = getNum(pageParam.size, 10)
            console.log("있음")
             //queryStr = createSearchParams({page:pageNum, size: sizeNum}).toString()
             queryStr = createSearchParams().toString()
        }else {
            queryStr = queryDefault
        }
        console.log('Navigating to list with query:', queryStr); // 콘솔 로그 추가

        setRefresh(!refresh)*/

        navigate({pathname:'../list'})
    }


   /* 메뉴bar에서 카테고리 이동 */
    const moveToCategory = (category) => {
        navigate({pathname:`../${category}`})
    }


    /* 메뉴bar에서 카테고리 이동 */
    const moveToMain = () => {
        navigate({pathname:`../`})
    }


    const moveToModify = (num) => {
        console.log('Navigating to modify with number:', num); // 콘솔 로그 추가
        navigate({pathname:`../modify/${num}`,
            search:queryDefault})
    }
    const moveToRead = (category,num) => {
        navigate({pathname:`../${category}/read/${num}`})
    }
    //List에서 글쓰기
    const moveToAdd = (category) => {
        navigate({pathname:`../${category}/add`})
    }
    const moveToAnlyAdd = (category,num,title) => {
        navigate({pathname:`../${category}/read/${num}/add`,
            search: `?title=${encodeURIComponent(title)}` });
    }
    const moveToAnlyRead = (category,tno,anlyId) => {
        navigate({pathname:`../${category}/read/${tno}/${anlyId}`})
    }


    const moveToSignup = () => {
        navigate({pathname:`../signup`});
    };

    const moveToLogin = () => {
        navigate({pathname:`../login`});
    };

    return {moveToMain,moveToList, moveToModify, moveToRead, moveToAnlyAdd, moveToAnlyRead,
        page,size , refresh ,moveToCategory ,moveToAdd,moveToLogin,moveToSignup}
}




//외부에서 사용할 수 있게 useCustomMove함수르르 export default
export default useCustomMove