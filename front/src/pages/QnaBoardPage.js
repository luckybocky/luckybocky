import React, {useEffect, useState, Suspense } from "react";
import { useNavigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import QnaService from "../api/QnaService.ts";

import Menu from "../components/Menu";
import Util from "../components/Util.js";

const IoLockClosed = Util.loadIcon("IoLockClosed").io5;

const QnaBoardPage = () => {
  const navigate = useNavigate();
  const [questionModalOpen, setQuestionModalOpen] = useState(false);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [secretStatus, setSecretStatus] = useState(false);
  const [secretAlarm, setSecretAlarm] = useState(false);
  const [accessAlarm, setAccessAlarm] = useState(false);
  const [questions, setQuestions] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [cansSubmit, setCanSubmit] = useState(false);
  const [flag, setFlag] = useState(JSON.parse(sessionStorage.getItem("flag"))||false);

  const itemsPerPage = 5;
  const startPage = Math.floor((currentPage - 1) / 3) * 3 + 1;
  const endPage = Math.min(startPage + 2, totalPages);
  const visiblePages = Array.from(
    { length: endPage - startPage + 1 },
    (_, i) => startPage + i
  );

  const changePage = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
    }
  };
  const user = AuthStore((state) => state.user);

  const closeModals = () => {
    setQuestionModalOpen(false);
    setTitle("");
    setContent("");
    setSecretStatus(false);
    setCurrentPage(1);
  };

  const checkFlag = () => {
    if (!user?.userNickname) {
      setSecretAlarm(true);
      setTimeout(() => setSecretAlarm(false), 2000);
      return;
    }
    setCurrentPage(1);
    const newFlag = !flag;
    setFlag(newFlag);
    sessionStorage.setItem("flag", JSON.stringify(newFlag));
  };

  const confirmQuestion = async () => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    const payload = {
      title,
      content,
      secretStatus,
    };

    await QnaService.saveQuestion(payload); // 질문 저장

    setIsSubmitting(false);
    closeModals(); // 모달 닫기
    fetchQuestions();
  };

  const checkAccess = async (payload) => {
    const response = await QnaService.checkAccess(payload.qnaSeq);
    if (
      response === 400 ||
      response === 300 ||
      payload.question.secretStatus === false
    ) {
      navigate(`/qna/${payload.qnaSeq}`, {
        state: { question: payload.question, code: response },
      });
    } else {
      setAccessAlarm(true);
      setTimeout(() => setAccessAlarm(false), 2000);
    }
  };

  const fetchQuestions = async () => {
    const params = {
      page: currentPage - 1,
      size: itemsPerPage,
      sort: "createdAt,desc",
    };

    let response = null; // 상세 페이지
    try {
      if (flag === false) {
        response = await QnaService.getQuestions(params);
      } else if (flag === true) {
        response = await QnaService.getMyQuestions(params);
      }
  
      setQuestions(response.content);
      setTotalPages(response.page.totalPages);
    } catch (error) {
      navigate("/");
    }
  };

  useEffect(() => {
    fetchQuestions();
  }, [currentPage, flag]);

  useEffect(() => {
    if (title?.length >= 1 && content?.length >= 1) setCanSubmit(true);
    else setCanSubmit(false);
  }, [title, content]);

  return (
    <div className="relative flex flex-col items-center w-full p-4 max-w-[600px] min-h-screen bg-[#FEFAF6] text-white overflow-hidden">
      <Menu />
      {/* 제목 영역 */}
      <h1 className="text-xl mt-2 mb-2 text-[#0d1a26]">QnA</h1>
      {/* 상단 메뉴 영역 */}
      <div className="w-full grid grid-cols-2 px-4 space-x-4 mt-3 mb-3 text-sm">
        <button
          className="bg-[#AD8B73] h-9 p-3 rounded-lg flex items-center justify-center"
          onClick={() => setQuestionModalOpen(true)}
        >
          작성하기
        </button>
        <div className="flex items-center">
          <label className="relative inline-flex items-center cursor-pointer">
            <input
              type="checkbox"
              checked={flag}
              onChange={() => checkFlag(!flag)}
              className="sr-only peer"
            />
            <div
              className="w-12 h-6 bg-gray-300 rounded-full peer peer-checked:bg-[#CEAB93] 
             transition-colors duration-300 outline outline-2 outline-transparent focus:outline-[#CEAB93]"
            ></div>
            <span
              className="absolute left-1 top-1 w-4 h-4 bg-white rounded-full shadow peer-checked:translate-x-6 
                 transition-transform duration-300 focus:outline-none"
            ></span>
          </label>
          <span className="ml-4 text-[#0d1a26]">
            {flag ? "내 QnA" : "전체 QnA"}
          </span>
        </div>
      </div>
      {/* 메시지 리스트 */}
      <div className="w-full">
        {questions.map((question, index) => (
          <button
            key={question.qnaSeq}
            className="w-full px-4 text-[#0d1a26]"
            onClick={() => checkAccess({ qnaSeq: question.qnaSeq, question })}
          >
            <div
              className={`relative p-3 ${
                index === questions.length - 1
                  ? "border-t border-b border-gray-600"
                  : "border-t border-gray-600"
              }`}
            >
              <div className="text-lg text-left leading-none pl-1">
                {question.secretStatus ? (
                  <span className="flex items-center gap-1">
                    비밀글
                    <Suspense>
                      <IoLockClosed size={16} />
                    </Suspense>
                  </span>
                ) : question.title.length > 12 ? (
                  `${question.title.slice(0, 12)}...`
                ) : (
                  question.title
                )}
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
                  {question.answer === null ? "답변 대기" : "답변 완료"}
                </div>
              </div>
            </div>
          </button>
        ))}
      </div>

      {/* 페이지네이션 */}
      <div className="justify-center mt-6 text-black">
        {startPage > 1 && (
          <button
            className="px-3 py-1 mx-1 bg-gray-300 rounded"
            onClick={() => changePage(startPage - 1)}
          >
            이전
          </button>
        )}

        {visiblePages.map((page) => (
          <button
            key={page}
            className={`px-3 py-1 mx-1 rounded ${
              page === currentPage ? "bg-[#AD8B73] text-white" : "bg-gray-300"
            }`}
            onClick={() => changePage(page)}
          >
            {page}
          </button>
        ))}

        {endPage < totalPages && (
          <button
            className="px-3 py-1 mx-1 bg-gray-300 rounded"
            onClick={() => changePage(endPage + 1)}
          >
            다음
          </button>
        )}
      </div>

      {/* 돌아가기 버튼 */}
      <button
        onClick={() => navigate(window.sessionStorage.getItem("pocketAddress"))}
        className="fixed bottom-0 w-full max-w-[600px] bg-[#E3CAA5] text-[#0d1a26] rounded-t-lg pt-5 pb-4"
      >
        돌아가기
      </button>

      {/* 질문게시 모달 */}
      {questionModalOpen && (
        <div
          className="fixed inset-0 z-30 flex items-center justify-center bg-black bg-opacity-60"
          onClick={closeModals}
        >
          <div
            className="bg-white text-[#0d1a26] p-4 rounded-lg w-80"
            onClick={(e) => e.stopPropagation()}
          >
            <textarea
              className="w-full p-2 border border-gray-300 rounded-md resize-none whitespace-nowrap"
              placeholder="제목을 입력하세요."
              rows={1}
              onChange={(e) => setTitle(e.target.value)}
              onKeyDown={(e) => {
                if ((e.altKey && e.key === "Enter") || e.key === "Enter") {
                  e.preventDefault();
                }
              }}
            ></textarea>
            <textarea
              className="w-full h-60 p-2 border border-gray-300 rounded-md mb-2 resize-none"
              placeholder="내용을 입력하세요."
              onChange={(e) => setContent(e.target.value)}
            ></textarea>
            <div className="space-x-2 mb-4">
              <input
                type="checkbox"
                checked={secretStatus}
                onChange={() => {
                  if (!user?.userNickname) {
                    setSecretAlarm(true);
                    setTimeout(() => setSecretAlarm(false), 2000);
                    return;
                  }
                  setSecretStatus(!secretStatus);
                }}
              />
              <span>비공개</span>
            </div>
            <div className="flex justify-between">
              <div className="flex gap-2">
                <button
                  className="bg-[#E3CAA5] text-black py-2 px-4 rounded-lg"
                  onClick={closeModals}
                >
                  취소
                </button>
                <button
                  onClick={confirmQuestion}
                  className={`${
                    cansSubmit
                      ? "bg-[#AD8B73] hover:bg-blue-600"
                      : "bg-gray-400 cursor-not-allowed"
                  } text-white rounded-lg py-2 px-6`}
                  disabled={!cansSubmit}
                >
                  저장
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
      {/*비회원 블로킹 알림 */}
      {secretAlarm && (
        <div className="fixed bottom-16 bg-red-500 text-white py-2 px-4 rounded-lg shadow-md z-30">
          로그인 후 이용 가능합니다!
        </div>
      )}
      {/*QnA 접근 블로킹 알림 */}
      {accessAlarm && (
        <div className="fixed bottom-16 bg-red-500 text-white py-2 px-4 rounded-lg shadow-md z-30">
          비공개 글입니다!
        </div>
      )}
    </div>
  );
};

export default QnaBoardPage;
