import React, { useState, useEffect, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import Menu from "../components/Menu";
import QnaService from "../api/QnaService.ts";

const QnaDetailPage = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [questionModalOpen, setQuestionModalOpen] = useState(false);
  const [question, setQuestion] = useState(location.state?.question || null);
  const [title, setTitle] = useState(question?.title || null);
  const [content, setContent] = useState(question?.content || null);
  const [secretStatus, setSecretStatus] = useState(
    question?.secretStatus || null
  );
  const [answer, setAnswer] = useState("");
  const [deleteModalOpen, setDeleteModalOpen] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [cansSubmit, setCanSubmit] = useState(false);
  const initialValues = useRef({ title: "", content: "", secretStatus: false });

  const code = location.state?.code || 200;

  const closeModals = () => {
    setQuestionModalOpen(false);
    setTitle("");
    setContent("");
    setSecretStatus(false);
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

  const confirmDelete = async () => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    await QnaService.deleteQuestion(question.qnaSeq);
    setIsSubmitting(false);
    navigate(-1);
  };

  const confirmUpdate = async () => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    const payload = {
      qnaSeq: question.qnaSeq,
      title,
      content,
      answer: question.answer,
      secretStatus,
    };

    await QnaService.updateQuestion(payload);

    setIsSubmitting(false);
    closeModals();
    setQuestion(payload);
  };

  useEffect(() => {
    if (questionModalOpen) {
      // 모달이 열릴 때 초기값을 저장 (처음 한 번만)
      if (!initialValues.current.title && !initialValues.current.content) {
        initialValues.current = {
          title: question?.title || "",
          content: question?.content || "",
          secretStatus: question?.secretStatus || false,
        };
      }
      // 상태를 초기값으로 설정
      setTitle(initialValues.current.title);
      setContent(initialValues.current.content);
      setSecretStatus(initialValues.current.secretStatus);
    }
  }, [questionModalOpen, location.question]);

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
      {question && (code === 300 || code === 400) ? (
        <div className="w-full grid grid-cols-2 px-4 space-x-4 mt-3 mb-3 text-sm">
          <button
            className={`${
              question.answer === null
                ? "bg-[#AD8B73]"
                : "bg-gray-400 cursor-not-allowed"
            } h-9 p-3 rounded-lg flex items-center justify-center`}
            onClick={() => setQuestionModalOpen(true)}
            disabled={question.answer !== null}
          >
            수정하기
          </button>
          <button
            className="bg-[#CEAB93] h-9 p-3 rounded-lg flex items-center justify-center"
            onClick={() => setDeleteModalOpen(true)}
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
      <div className="w-full text-[#0d1a26]">
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
      {/* 삭제 모달 */}
      {deleteModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-40">
          <div className="flex flex-col items-center bg-white rounded-lg shadow-lg p-6 w-80">
            <h2 className="text-xl text-[#3c1e1e] mb-4">
              질문은 복구할 수 없어요.
            </h2>
            <p className="text-gray-700 mb-6">정말 삭제하시겠어요?</p>
            <div className="flex gap-4">
              <button
                className="bg-gray-300 text-black rounded-md py-2 px-4"
                onClick={() => setDeleteModalOpen(false)}
              >
                취소
              </button>
              <button
                className="bg-red-500 text-white rounded-md py-2 px-4"
                onClick={confirmDelete}
              >
                삭제
              </button>
            </div>
          </div>
        </div>
      )}
      {/* 돌아가기 버튼 */}
      <button
        onClick={() => navigate(-1)}
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
            className="bg-white w-full max-w-[600px] text-[#0d1a26] p-4 rounded-lg w-80"
            onClick={(e) => e.stopPropagation()}
          >
            <textarea
              className="w-full p-2 border border-gray-300 rounded-md resize-none whitespace-nowrap"
              placeholder="제목을 입력하세요."
              rows={1}
              value={title}
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
              value={content}
              onChange={(e) => setContent(e.target.value)}
            ></textarea>
            <div className="space-x-2 mb-4">
              <input
                type="checkbox"
                checked={secretStatus}
                onChange={() => {
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
                  onClick={confirmUpdate}
                  className={`${
                    cansSubmit
                      ? "bg-[#AD8B73]"
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
    </div>
  );
};

export default QnaDetailPage;
