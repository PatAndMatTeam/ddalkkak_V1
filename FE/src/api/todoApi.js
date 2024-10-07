import axios from "axios";

export  const API_SERVER_HOST = 'http://192.168.51.57:8082'
const prefix = `${API_SERVER_HOST}/api/board`   // 이 상수는 API 서버의 기본 경로를 나타냅니다

export  const getOne = async (tno) => {
    //const res = await axios.get(`${prefix}/${tno}`);

    const res = await axios.get(`${prefix}/${tno}`, {
        validateStatus: function (status) {
            return true;
        }
    });

    // 302 상태일 때 데이터 처리
    if (res.status === 302) {
        console.log(res.data); // 원하는 대로 데이터를 로그에 출력합니다
    }

    // 데이터만 반환
    return res.data;
}

// API 호출 후 응답 처리2
export const getList = async (pageParam) => {
    const { page, size } = pageParam;
    const start = 0;
    const end = size;
    const res = await axios.get(`${prefix}/all`, {
        params: { start, end },
        validateStatus: function (status) {
            return true;
        }
    });

    // 302 상태일 때 데이터 처리
    if (res.status === 302) {
        //console.log(res.data); // 원하는 대로 데이터를 로그에 출력합니다
    }

    // 데이터만 반환
    return res.data;
};

export const postAdd = async (board, files) => {
    const formData = new FormData();

    // 게시글 정보 추가 - JSON 문자열로 변환 후 board라는 이름으로 추가
    formData.append('board', new Blob([JSON.stringify(board)], { type: "application/json" }));

    // 이미지 파일과 그에 따른 메타 데이터 추가
    files.forEach((fileObj, index) => {
        formData.append(`files`, fileObj.file);  // 파일 자체 추가
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