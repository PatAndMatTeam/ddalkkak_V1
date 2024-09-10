import axios from "axios";

export  const API_SERVER_HOST = 'http://192.168.51.57:8082'
const prefix = `${API_SERVER_HOST}/api/board`   // 이 상수는 API 서버의 기본 경로를 나타냅니다

export  const getOne = async (tno) => {
    //axios.get을 사용하여 HTTP GET 요청을 보냅니다.
    //요청 URL은 prefix와 tno(todo 번호)를 결합하여 http://localhost:8080/api/todo/{tno}가 됩니다.
    //이 요청은 비동기적으로 처리되며, 응답이 res 변수에 저장됩니다.
    const res = await axios.get(`${prefix}/${tno}`)

    //res.data는 서버로부터 받은 응답 데이터를 반환합니다. 이 데이터는 일반적으로 Todo 항목의 세부 정보가 됩니다.
    return res.data
}


//axios 라이브러리를 사용하여 HTTP 요청을 보냅니다.
// API_SERVER_HOST는 API 서버의 기본 URL을 정의합니다.
// prefix는 API 경로를 포함한 URL을 정의합니다.
// getOne 함수는 주어진 tno에 대해 GET 요청을 보내고, 해당 Todo 항목 데이터를 반환합니


//파라미터가 여러개인경우 객체로 받는게 편하다
/*export const getList = async (pageParam) => {
    try {
        const {page, size} = pageParam;
        const start = 0;  // pageParam.page를 start로 사용
        const end = size;    // pageParam.size를 end로 사용


        const res = await axios.get(`${prefix}/all`, {
            params: {start, end},  // start와 end를 쿼리 파라미터로 보냄
            maxRedirects: 5 // 리다이렉션 허용 (최대 5번까지)
        });
        return res.data;
    }catch (error) {
        console.error("Error fetching data: ", error);
        throw error; // 오류를 상위 컴포넌트에서 처리할 수 있도록 전달
    }

};*/
export const getList = async (pageParam) => {
        const {page, size} = pageParam;
        const start = 0;  // pageParam.page를 start로 사용
        const end = size;    // pageParam.size를 end로 사용
  //  params: {start, end}
        const res = await axios.get(`${prefix}/all`,  {params: {start, end},
            validateStatus: function (status) {

                return true;
            }
        });
        if (res.status === 302) {
            // GET YOUR RESPONSE HERE
            console.log(res.data);
        }
    console.log("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
    console.log(res.data);
        return res.data;
};

export const postAdd = async (todoObj) => {

    //옛날에 제이슨 했으면 JSON.stringify(obj) 이런거 써야했는데
    //axios라이브러리 사용시 그냥 쓰면된다.
    const res = await axios.post(`${prefix}/`,todoObj)

    return res.data
}

export const deleteOne = async(tno) => {

    const res = await axios.delete(`${prefix}/${tno}`,tno)

    return res.data
}

export const putOne = async(todo) => {

    const res = await axios.put(`${prefix}/${todo.tno}`,todo)

    return res.data
}