import React from 'react';

const ResultModal = ({ title, content, callbackFn }) => {
    return (
        <div
            className="fixed inset-0 z-[1055] flex items-center justify-center bg-black bg-opacity-40"
            onClick={() => {
                if (callbackFn) {
                    callbackFn();
                }
            }}
        >
            <div
                className="relative bg-white rounded-lg shadow-lg w-full max-w-md p-6 text-center"
                onClick={(e) => e.stopPropagation()}
            >
                {/* 아이콘과 제목 */}
                <div className="flex items-center justify-center mb-4">
                    <div className="text-green-500 text-4xl mr-2">✔️</div>
                    <h2 className="text-xl font-semibold">{title}</h2>
                </div>

                {/* 내용 */}
                <p className="text-gray-700 mb-6">{content}</p>

                {/* 버튼 */}
                <button
                    className="w-full py-2 bg-blue-500 hover:bg-blue-600 text-white rounded-lg text-lg"
                    onClick={() => {
                        if (callbackFn) {
                            callbackFn();
                        }
                    }}
                >
                    확인
                </button>
            </div>
        </div>
    );
};

export default ResultModal;
