import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import Menu from "../components/Menu";
import QnaService from "../api/QnaService.ts";

const QnaDetailPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [answer, setAnswer] = useState("");
  const [tempAlarm, setTempAlarm] = useState(false);
  const [question, setQuestion] = useState(location.state?.question || null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const code = location.state?.code || 200;

  const activateAlarm = () => {
    setTempAlarm(true);
    setTimeout(() => setTempAlarm(false), 1000);
  };

  const sendAnswer = async () => {
    if (answer === "") {
      alert("답변을 입력해주세요.");
    } else {
      confirmAnswer();
    }
  };

  const confirmAnswer = async () => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    const payload = {
      qnaSeq: question.qnaSeq,
      title: question.title,
      content: question.content,
      answer: answer,
      secretStatus: question.secretStatus,
      userNickname: question.userNickname,
    };
    await QnaService.saveAnswer(payload);

    setIsSubmitting(false);
    setQuestion(payload);
  };

  const deleteQuestion = async () => {
    await QnaService.deleteQuestion(question.qnaSeq);
    navigate(-1);
  }

  return (
    <div className="relative flex flex-col items-center w-full p-2 max-w-[600px] min-h-screen bg-[#FEFAF6] text-white overflow-hidden">
      <Menu />
      {/* 제목 영역 */}
      <h1 className="text-xl mt-2 mb-2 text-[#0d1a26]">QnA</h1>
      {/* 상단 메뉴 영역 */}
      {question && (code === 300 || code === 400) ? (
        <div className="w-full grid grid-cols-2 px-4 space-x-4 mt-3 mb-3 text-sm">
          <button
            className="bg-[#AD8B73] h-9 p-3 rounded-lg flex items-center justify-center"
            onClick={() => activateAlarm()}
          >
            수정하기
          </button>
          <button
            className="bg-[#CEAB93] h-9 p-3 rounded-lg flex items-center justify-center"
            onClick={() => deleteQuestion()}
          >
            삭제하기
          </button>
        </div>
      ) : (
        <div className="w-full px-4">
          <div className="h-9 p-3 mt-3 mb-3"></div>
        </div>
      )}
      {/* QnA */}
      <div className="w-full text-[#0d1a26] font-JalnanGothic">
        {question ? (
          <div className="w-full px-4">
            <div className="p-3 border-t border-b border-gray-600">
              <div className="text-lg text-left leading-none pl-1">
                {question.title}
              </div>
              <div className="grid grid-cols-3 mt-3 text-xs">
                <div className="text-left p-1">{question.createdAt}</div>
                <div
                  className={`${
                    question.userNickname === "Anonym_Dummy"
                      ? "bg-[#747474] text-white"
                      : ""
                  } text-center p-1`}
                >
                  {question.userNickname === "Anonym_Dummy"
                    ? "Guest"
                    : question.userNickname}
                </div>
                <div className="text-right p-1">
                  {question.answer !== null ? "답변 완료" : "답변 대기"}
                </div>
              </div>
            </div>
            <div className="p-3 bg-[#D5D5D5]">
              <div className="p-1 whitespace-pre-wrap text-xs">
                {question.content}
              </div>
            </div>
            <div className="p-1 border-t border-gray-600"></div>
            {code === 400 && question.answer === null ? (
              <div>
                <textarea
                  className="w-full h-40 p-2 border border-gray-300 rounded-md mb-2 resize-none text-xs"
                  placeholder="답변을 입력하세요"
                  onChange={(e) => setAnswer(e.target.value)}
                ></textarea>
                <button
                  className="bg-[#D5D5D5] h-9 p-3 rounded-lg flex items-center justify-center"
                  onClick={() => sendAnswer()}
                >
                  저장
                </button>
              </div>
            ) : (
              <div className="p-3 text-xs">
                {question.answer === null ? "답변 없음" : question.answer}
              </div>
            )}
          </div>
        ) : (
          <div className="w-full fixed top-[50%] max-w-[375px] left-[50%] transform -translate-x-[50%]">
            <p className="flex justify-center">데이터를 불러올 수 없습니다.</p>
          </div>
        )}
      </div>
      {/* 돌아가기 버튼 */}
      <button
        onClick={() => navigate(-1)}
        className="fixed bottom-0 w-full max-w-[600px] bg-[#E3CAA5] text-[#0d1a26] rounded-t-lg pt-5 pb-4"
      >
        돌아가기
      </button>
      {/*임시 알림 */}
      {tempAlarm && (
        <div className="fixed bottom-16 bg-blue-500 text-white py-2 px-4 rounded-lg shadow-md z-30">
          준비중입니다!
        </div>
      )}
    </div>
  );
};

export default QnaDetailPage;
