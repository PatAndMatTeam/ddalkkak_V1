import axios from "axios";

export  const API_SERVER_HOST = 'http://10.102.0.1:8082'
const prefix = `${API_SERVER_HOST}/api/board`   // 이 상수는 API 서버의 기본 경로를 나타냅니다

export  const getOne = async (tno) => {
    const res = await axios.get(`${prefix}/${tno}`)
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
        const res = await axios.get(`${prefix}/all`,  {params: {start, end},
            validateStatus: function (status) {
                return true;
            }
        });
        if (res.status === 302) {
            console.log(res.data);
        }
        return res.data;
};

export const postAdd = async (board, files) => {
    const formData = new FormData();

    // 게시글 정보 추가
    formData.append('category', board.category);
    formData.append('title', board.title);
    formData.append('content', board.content);
    formData.append('writer', board.writer);
    formData.append(`width`, 1);  // 파일의 너비 추가
    formData.append(`height`, 1);  // 파일의 높이 추가
    // 이미지 파일과 그에 따른 메타 데이터 추가
    files.forEach((fileObj, index) => {
        console.log("AAAAAAAAA" + index);
        console.log(fileObj.file);
        formData.append(`files`, fileObj.file);  // 파일 자체 추가
        /*formData.append(`width`, fileObj.width);  // 파일의 너비 추가
        formData.append(`height`, fileObj.height);  // 파일의 높이 추가*/
    });

    for (const [key, value] of formData.entries()) {
        console.log(key, value);
    };

    try {
        const response = await axios.post(`${prefix}/`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',  // 파일 업로드를 위한 헤더 설정
            }
        });
        return response.data;
    } catch (error) {
        // Axios 에러 로그 추가
        if (error.response) {
            console.log('Response data:', error.response.data);
            console.log('Response status:', error.response.status);
            console.log('Response headers:', error.response.headers);
        } else if (error.request) {
            console.log('Request data:', error.request);
        } else {
            console.log('Error message:', error.message);
        }
        console.error("Error adding post", error);
        throw error;
    }
};

export const deleteOne = async(tno) => {

    const res = await axios.delete(`${prefix}/${tno}`,tno)

    return res.data
}

export const putOne = async(todo) => {

    const res = await axios.put(`${prefix}/${todo.tno}`,todo)

    return res.data
}