import React from 'react';
import useCustomMove from "../../hooks/useCustomMove";

const LoginBarMenu = () => {
    const { moveToSignup,moveToLogin} = useCustomMove();

    return (
        <div style={styles.loginBar}>
            <button style={styles.button} onClick={moveToSignup}>
                <span style={styles.icon}>👤</span> 회원가입
            </button>
            <button style={styles.button} onClick={moveToLogin}>
                <span style={styles.icon}>🔑</span> 로그인
            </button>
        </div>
    );
};

const styles = {
    loginBar: {
        backgroundColor: '#00796b', // 배경색을 이미지와 유사하게 설정
        padding: '10px',
        display: 'flex',
        justifyContent: 'flex-end', // 오른쪽 정렬
        gap: '10px', // 버튼 간격
    },
    button: {
        backgroundColor: '#004d40', // 버튼 배경색
        color: '#fff',
        border: 'none',
        borderRadius: '15px', // 버튼을 둥글게, 크기에 맞게 조금 줄임
        padding: '6px 12px', // 패딩을 줄여 버튼 크기를 작게
        display: 'flex',
        alignItems: 'center',
        gap: '4px', // 아이콘과 텍스트 간격
        fontSize: '12px', // 글자 크기를 줄임
        cursor: 'pointer',
    },
    icon: {
        fontSize: '14px', // 아이콘 크기 조정
    },
};

export default LoginBarMenu;
