import axios from "axios";
//import axios from './axiosConfig';

//export  const API_SERVER_HOST = 'http://10.102.0.1:8082'
//export  const API_SERVER_HOST = 'http://192.168.51.57:8082'
export  const API_SERVER_HOST = 'http://localhost:8082'
const prefix = `${API_SERVER_HOST}/api/board/v2`   // 이 상수는 API 서버의 기본 경로를 나타냅니다
const prefix2 = `${API_SERVER_HOST}/api/board`   // 이 상수는 API 서버의 기본 경로를 나타냅니다
const token = `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBY2Nlc3NUb2tlbiIsIm5hbWUiOiJ0ZXN0IiwiZW1haWwiOiJ0ZXN0IiwiYXV0aCI6IlJPTEVfQURNSU4iLCJpYXQiOjE3MzIzNDE2MDcsImV4cCI6MTczMjM0NTIwN30.xk9ZSTHb0zHO9EdFjTfspA5uie-qe8wfJIoPp1A6kls`

export  const getOne = async (tno,category) => {
    //const res = await axios.get(`${prefix}/${tno}`);

    const res = await axios.get(`${prefix}/${category}/${tno}`, {
        headers: {
            'Authorization': `Bearer ${token}` // Authorization 헤더 추가
        },
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
export const getAnlyOne = async (pageParam) => {
    const { tno ,category,anlyId} = pageParam;
    const res = await axios.get(`${prefix}/${category}/${tno}/${anlyId}`, {
        headers: {
            'Authorization': `Bearer ${token}` // Authorization 헤더 추가
        },
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


// API 호출 후 응답 처리2
export const getList = async (pageParam) => {
    const { category} = pageParam;
    const res = await axios.get(`${prefix}/${category}/all`, {
        headers: {
            'Authorization': `Bearer ${token}` // Authorization 헤더 추가
        },
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

// API 호출 후 응답 처리2
export const getAnlyList = async (params) => {
    const { tno, category, title, content } = params;

    console.log("title",title);
    console.log("content",content);
    // 쿼리 문자열을 동적으로 생성
    let query = "all";  // 기본값 설정
/*

    // title과 content 파라미터가 있는 경우
    if (title || content) {
        const queryParams = new URLSearchParams();
        if (title) queryParams.append("title", title);
        if (content) queryParams.append("content", content);
        query = `search?${queryParams.toString()}`;
    }
*/
console.log(`${prefix}/${category}/${tno}/${query}`);
    // API 요청
    const res = await axios.get(`${prefix}/${category}/${tno}/${query}`, {
  /*      headers: {
            'Authorization': `Bearer ${token}` // Authorization 헤더 추가
        }*/
        validateStatus: function (status) {
            return true;
        }
    });

    // 302 상태일 때 데이터 처리
    if (res.status === 302) {
        console.log("Redirect detected:", res.data); // 디버깅용 로그
    }

    // 데이터만 반환
    return res.data;
};

export const postAdd = async (board, files, fileInfo) => {
    const formData = new FormData();
    const { category } = board;

    // 게시글 정보 추가 - board를 JSON 형태로 변환하여 추가
    formData.append('board', new Blob([JSON.stringify(board)], { type: "application/json" }));

    // 이미지 파일 추가
    files.forEach((fileObj) => {
        formData.append('files', fileObj.file);  // 파일 자체 추가
    });

    // 파일 메타 데이터 추가 (fileInfo)

    formData.append('fileInfo', new Blob([JSON.stringify(fileInfo)], { type: "application/json" }));
    try {
        const response = await axios.post(`${prefix}/${category}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data' , // Axios가 자동으로 Content-Type 설정
                'Authorization': `Bearer ${token}`
            }
        });

        // 응답 전체를 반환하여 상태 코드와 데이터를 모두 받을 수 있도록 변경
        return { status: response.status, data: response.data };
    } catch (error) {
        if (error.response) {
            console.error('Server responded with:', error.response.data);
        } else if (error.request) {
            console.error('No response received:', error.request);
        } else {
            console.error('Error during request:', error.message);
        }
        throw error;
    }
};

export const postAnlyAdd = async (board, files) => {
    const formData = new FormData();

    // 게시글 정보 추가 - board를 JSON 형태로 변환하여 추가
    formData.append('board', new Blob([JSON.stringify(board)], { type: "application/json" }));

    // 이미지 파일과 메타 데이터 추가
    files.forEach((fileObj) => {
        formData.append('files', fileObj.file);  // 파일 자체 추가
        formData.append('fileTitles', fileObj.fileTitle);  // 파일 제목 추가 (A 이름 또는 B 이름)
    });
    const { category, tno } = board;

    try {
        const response = await axios.post(`${prefix}/${category}/${tno}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',  // 파일 업로드를 위한 헤더 설정
                'Authorization': `Bearer ${token}`
            }
        });

        // 응답 전체를 반환하여 상태 코드와 데이터를 모두 받을 수 있도록 변경
        return { status: response.status, data: response.data };
    } catch (error) {
        if (error.response) {
            console.error('Server responded with:', error.response.data);
        } else if (error.request) {
            console.error('No response received:', error.request);
        } else {
            console.error('Error during request:', error.message);
        }
        throw error;
    }
};
export const postCommentAdd = async ({ anlyId, newCommentData }) => {

    try {
        const response = await axios.post(`${prefix2}/${anlyId}/comment`, newCommentData, {
            headers: {
                'Content-Type': 'application/json',  // JSON 전송을 위한 Content-Type 설정
                'Authorization': `Bearer ${token}`
            },
        });

        return { status: response.status, data: response.data };
    } catch (error) {
        if (error.response) {
            console.error('Server responded with:', error.response.data);
        } else if (error.request) {
            console.error('No response received:', error.request);
        } else {
            console.error('Error during request:', error.message);
        }
        throw error;
    }
};

export const deleteCommentAdd = async ({ anlyId, commentId, password }) => {
    try {
        const response = await axios.delete(`${prefix}/${anlyId}/comment/${commentId}`, {
            data: { password },
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });

        return { status: response.status, data: response.data };
    } catch (error) {
        if (error.response) {
            console.error('Server responded with:', error.response.data);
        } else if (error.request) {
            console.error('No response received:', error.request);
        } else {
            console.error('Error during request:', error.message);
        }
        throw error;
    }
};

export const increaseVisitCount = async (anlyId) => {


    try {
        const response = await axios.patch(`${prefix}/${anlyId}/visit`);
        return { status: response.status, data: response.data };
    } catch (error) {
        console.error('조회수 증가 요청 실패:', error);
        return false;
    }
};

export const incrementRecommend = async ({ anlyId }) => {
    console.log("anlyId" , anlyId);
    try {
        const response = await axios.patch(`${prefix}/${anlyId}/recommend`);
        return { status: response.status, data: response.data };
    } catch (error) {
        console.error('Error incrementing recommendation:', error);
        throw error;
    }
};


// todoApi.js
export const getKakaoLogin = async () => {
    const res = await axios.get(`http://localhost:8082/api/user/login`);
    return res.data;
};



// 투표 API 호출
export const postVote = async (tno, voteType) => {
    try {
        const res = await axios.patch(`${prefix}/${tno}/vote`, voteType, {
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
        });

        return { status: res.status, data: res.data };
    } catch (error) {
        if (error.response) {
            console.error('Server responded with:', error.response.data);
        } else if (error.request) {
            console.error('No response received:', error.request);
        } else {
            console.error('Error during request:', error.message);
        }
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