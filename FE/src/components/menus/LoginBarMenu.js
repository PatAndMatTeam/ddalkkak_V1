import React from 'react';


const LoginBarMenu = () => {
    return (
        <div style={styles.loginBar}>
            <h2>로그인</h2>
            {/* 로그인 폼이나 버튼 추가 가능 */}
        </div>
    );
}

const styles = {
    loginBar: {
        backgroundColor: '#f8f8f8',
        padding: '10px',
        textAlign: 'center',
        borderBottom: '1px solid #ccc',
    }
};
export default LoginBarMenu;