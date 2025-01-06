import React, { useState, useEffect } from 'react';
import { getOne, postVote } from "../../api/todoApi";
import useCustomMove from "../../hooks/useCustomMove";
import AnalysisBoardComponent from "../analysis/AnalysisBoardComponent";
import CommentsSection from "../analysis/CommentsSection";
import { Tab, Tabs } from '@mui/material';
import './BoardStyles.css';

function ReadComponent({ tno, category }) {
    const [serverData, setServerData] = useState(null);
    const { page, size, refresh } = useCustomMove();
    const [activeTab, setActiveTab] = useState(0);
    const [votes, setVotes] = useState({ vote1: 0, vote2: 0 }); // 투표수를 로컬에서 관리
    const [selectedVote, setSelectedVote] = useState(null); // 사용자가 선택한 항목 (왼쪽/오른쪽)
    const [error, setError] = useState(null); // 에러 메시지

    useEffect(() => {
        // 서버에서 데이터 가져오기
        getOne(tno, category).then(data => {
            console.log("Fetched data:", data);
            setServerData(data);

            // 초기 투표 데이터 설정
            setVotes({ vote1: data.leftVote, vote2: data.rightVote });
        }).catch(err => {
            console.error("데이터 로드 중 오류:", err);
        });
    }, [tno, category, page, size, refresh]);

    const totalVotes = votes.vote1 + votes.vote2;
    const vote1Percentage = totalVotes === 0 ? 50 : Math.round((votes.vote1 / totalVotes) * 100);
    const vote2Percentage = totalVotes === 0 ? 50 : Math.round((votes.vote2 / totalVotes) * 100);

    // 투표 핸들러
    const handleVote = async () => {
        if (selectedVote) {
            try {
                console.log("Selected vote:", selectedVote);

                // 새로운 투표 데이터 계산
                const newVotes = {
                    leftVote: votes.vote1 + (selectedVote === 'vote1' ? 1 : 0),
                    rightVote: votes.vote2 + (selectedVote === 'vote2' ? 1 : 0),
                };

                console.log("New vote data to be sent:", newVotes);

                // 서버로 데이터 전송
                const response = await postVote(tno, newVotes);

                if (response.status === 200) {
                    console.log("Vote submitted successfully!");
                    // 페이지 새로고침
                    window.location.reload();
                }
            } catch (err) {
                console.error("Vote submission error:", err);
                setError(err.response?.data?.message || "투표 중 오류가 발생했습니다.");
            }
        }
    };

    const handleTabChange = (event, newValue) => {
        setActiveTab(newValue);
    };

    if (!serverData) {
        return <div>Loading...</div>;
    }

    const base64Image0 = serverData.files && serverData.files[0]
        ? `data:image/jpeg;base64,${serverData.files[0].data}`
        : "/placeholder.jpg";

    const base64Image1 = serverData.files && serverData.files[1]
        ? `data:image/jpeg;base64,${serverData.files[1].data}`
        : "/placeholder.jpg";

    return (
        <div className="read-container">
            <div className="read-content">
                <h1 className="read-title">{serverData.title || "제목 없음"}</h1>

                {/* 투표 UI */}
                <div
                    className={`vote-item ${selectedVote === 'vote1' ? 'selected' : ''}`}
                    onClick={() => {
                        console.log("Left item clicked!");
                        setSelectedVote('vote1');
                    }}
                >
                    <div className="vote-icon">
                        <img src={base64Image0} alt="Team 1" />
                    </div>
                    <div className="vote-bar-container">
                        <div
                            className="vote-bar"
                            style={{
                                width: `${vote1Percentage}%`,
                                backgroundColor: '#4caf50',
                            }}
                        >
                            <span>{serverData.files[0]?.fileTitle}</span>
                        </div>
                    </div>
                </div>

                <div
                    className={`vote-item ${selectedVote === 'vote2' ? 'selected' : ''}`}
                    onClick={() => {
                        console.log("Right item clicked!");
                        setSelectedVote('vote2');
                    }}
                >
                    <div className="vote-icon">
                        <img src={base64Image1} alt="Team 2" />
                    </div>
                    <div className="vote-bar-container">
                        <div
                            className="vote-bar"
                            style={{
                                width: `${vote2Percentage}%`,
                                backgroundColor: '#f44336',
                            }}
                        >
                            <span>{serverData.files[1]?.fileTitle}</span>
                        </div>
                    </div>
                </div>

                {/* 투표 버튼 */}
                <div className="vote-button-container">
                    <button
                        className="vote-button"
                        onClick={handleVote}
                        disabled={!selectedVote}
                    >
                        투표하기
                    </button>
                </div>
                {error && <div className="error-message">{error}</div>}

                {/* 탭 */}
                <div className="tab-section">
                    <Tabs value={activeTab} onChange={handleTabChange}>
                        <Tab label="실시간 채팅" />
                        <Tab label="분석글" />
                    </Tabs>
                    {activeTab === 0 && <CommentsSection
                        tno={tno}
                        category={category}
                        teamNames={{
                            team1: serverData.files[0]?.fileTitle || "Team 1",
                            team2: serverData.files[1]?.fileTitle || "Team 2"
                        }}
                    />}
                    {activeTab === 1 && (
                        <div className="tab-content">
                            <AnalysisBoardComponent
                                category={category}
                                tno={tno}
                                title={serverData.title}
                            />
                        </div>
                    )}
                </div>

            </div>
        </div>
    );
}

export default ReadComponent;
