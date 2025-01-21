import React, { useEffect, useState, lazy, Suspense } from "react";
import { useNavigate } from "react-router-dom";
import Menu from "../components/Menu";
import AuthStore from "../store/AuthStore";
import QnaService from "../api/QnaService.ts";

const IoLockClosed = lazy(() =>
  import("react-icons/io5").then((mod) => ({ default: mod.IoLockClosed }))
);

const QnaBoardPage = () => {
  const navigate = useNavigate();
  const [questionModalOpen, setQuestionModalOpen] = useState(false);
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [flag, setFlag] = useState(false);
  const [secretStatus, setSecretStatus] = useState(false);
  const [secretAlarm, setSecretAlarm] = useState(false);
  const [accessAlarm, setAccessAlarm] = useState(false);
  const [questions, setQuestions] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);

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
  }

  const sendQuestion = () => {
    if (title === "") {
      alert("제목을 입력해주세요.");
    } else if (content === "") {
      alert("문의 내용을 입력해주세요.");
    } else {
      confirmQuestion();
    }
  };

  const confirmQuestion = async () => {
    const payload = {
      title,
      content,
      secretStatus,
    };

    await QnaService.saveQuestion(payload); // 질문 저장
    closeModals(); // 모달 닫기
    fetchQuestions();
  };

  const checkAccess = async (payload) => {
    const response = await QnaService.checkAccess(payload.qnaSeq);
    if (response === 400 || response === 300 || payload.question.secretStatus === false) {
      navigate(`/qna/${payload.qnaSeq}`, { state: {question: payload.question, code: response} });
    } else {
      setAccessAlarm(true);
      setTimeout(() => setAccessAlarm(false), 2000);
    }
  };

  const fetchQuestions = async (currentPage, flag) => {
    if (currentPage === undefined) {
      // 첫 페이지를 가리킬 때 undefined
      currentPage = 1;
    }
    const params = {
      page: currentPage - 1,
      size: itemsPerPage,
      sort: "createdAt,desc",
    };

    let response = null;  // 상세 페이지
    if (flag === false) {
      response = await QnaService.getQuestions(params);
    } else if (flag === true) {
      response = await QnaService.getMyQuestion(params);
    }

    setQuestions(response.content);
    setTotalPages(response.page.totalPages);
  };

  useEffect(() => {
    fetchQuestions(currentPage, flag);
  }, [currentPage, flag]);

  useEffect(() => {
    const savedFlag = sessionStorage.getItem("flag");
    if (savedFlag !== null) {
      setFlag(JSON.parse(savedFlag));
    }
  }, []);

  return (
    <div className="relative flex flex-col items-center w-full p-2 max-w-[375px] min-h-screen bg-[#FEFAF6] text-white overflow-hidden">
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
        <button
          className="bg-[#CEAB93] h-9 p-3 rounded-lg flex items-center justify-center"
          onClick={() => checkFlag()}
        >
          {flag === false ? "내 QnA" : "전체 QnA" }
        </button>
      </div>
      {/* 메시지 리스트 */}
      <div className="w-full">
        {questions.map((question, index) => (
          <button
            key={question.qnaSeq}
            className="w-full px-4 text-[#0d1a26] font-JalnanGothic"
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
      <div className="fixed top-[508px] justify-center mt-6 text-black">
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
        className="fixed bottom-0 w-full max-w-[375px] bg-[#E3CAA5] text-[#0d1a26] py-4 rounded-t-lg z-20"
      >
        <span className="flex justify-center pt-1">돌아가기</span>
      </button>

      {/* 질문게시 모달 */}
      {questionModalOpen && (
        <div
          className="fixed inset-0 z-30 flex items-center justify-center bg-black bg-opacity-60 font-JalnanGothic"
          onClick={closeModals}
        >
          <div
            className="bg-white text-[#0d1a26] p-4 rounded-lg w-80"
            onClick={(e) => e.stopPropagation()}
          >
            <textarea
              className="w-full p-2 border border-gray-300 rounded-md resize-none whitespace-nowrap overflow-hidden"
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
                  className="bg-gray-300 text-black py-2 px-4 rounded-lg"
                  onClick={closeModals}
                >
                  취소
                </button>
                <button
                  className="bg-[#0d1a26] text-white py-2 px-4 rounded-lg"
                  onClick={sendQuestion}
                >
                  등록하기
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
          로그인 상태를 확인하세요!
        </div>
      )}
    </div>
  );
};

export default QnaBoardPage;
