import React, { useState, Suspense } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import FeedbackService from "../api/FeedbackService.ts";

import PocketIcon from "../image/pocketIcon.svg";

import Util from "./Util";

const IoMenuSharp = Util.loadIcon("SlMenu").sl;
const IoSettingsOutline = Util.loadIcon("IoSettingsOutline").io5;
const IoMailOutline = Util.loadIcon("IoMailOutline").io5;
const IoChatbubblesOutline = Util.loadIcon("IoChatbubblesOutline").io5;
const IoPersonCircleOutline = Util.loadIcon("IoPersonCircleOutline").io5;
const IoHelpCircleOutline = Util.loadIcon("IoHelpCircleOutline").io5;

const Menu = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const myAddress = AuthStore((state) => state.user.address);

  const [menuOpen, setMenuOpen] = useState(false);
  const [feedbackModalOpen, setFeedbackModalOpen] = useState(false);
  const [feedback, setFeedback] = useState("");
  const [rating, setRating] = useState(0);
  const [feedbackAlarm, setFeedbackAlarm] = useState(false);
  const [confirmCloseModal, setConfirmCloseModal] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const isQnaPage = location.pathname.startsWith("/qna");

  const toggleMenu = () => setMenuOpen((prev) => !prev);

  const closeModal = () => {
    if (feedback === "") confirmClose();
    else setConfirmCloseModal(true);
  };

  const confirmClose = () => {
    setConfirmCloseModal(false);
    setFeedbackModalOpen(false);
    setRating(0);
    setFeedback("");
  };

  const sendFeedback = () => {
    if (feedback === "") {
      alert("피드백 내용을 입력해주세요.");
    } else if (rating === 0) {
      alert("별점을 선택해주세요.");
    } else {
      confirmFeedback();
    }
  };

  const confirmFeedback = async () => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    const payload = {
      feedbackContent: feedback,
      feedbackRate: rating,
    };

    await FeedbackService.save(payload);
    setFeedbackModalOpen(false);
    setRating(0);
    setFeedback("");
    setIsSubmitting(false);

    setFeedbackAlarm(true);
    setTimeout(() => setFeedbackAlarm(false), 2000);
  };

  return (
    <div>
      {/* 오버레이 */}
      {menuOpen && (
        <div
          className="fixed inset-0 bg-black bg-opacity-60 z-10"
          onClick={toggleMenu}
        ></div>
      )}

      {/* 메뉴 버튼 */}
      <button
        className="absolute top-0 right-0 text-3xl p-4 z-10"
        style={{
          color: isQnaPage ? "#0d1a26" : undefined, // CSS 변수로 동적 색상 지정
        }}
        onClick={toggleMenu}
      >
        <Suspense>
          <IoMenuSharp />
        </Suspense>
      </button>

      {/* 메뉴 바 */}
      <div
        className={`absolute top-0 right-0 h-full bg-[#333] transition-transform duration-300 ease-in-out py-4 px-6 z-20 ${
          menuOpen ? "translate-x-0" : "translate-x-full"
        }`}
        style={{ width: "260px" }}
      >
        {myAddress && (
          <ul className="space-y-3">
            <button
              onClick={() => navigate("/account")}
              className="flex hover:underline items-center gap-2"
            >
              <Suspense>
                <IoSettingsOutline size={24} />
              </Suspense>
              <span className="mt-1">계정 설정</span>
            </button>

            <button
              onClick={() => {
                navigate(`/${myAddress}`);
                toggleMenu();
              }}
              className="flex hover:underline items-center gap-2"
            >
              <img
                src={PocketIcon}
                alt="pocketIcon"
                width="24"
                className="mb-1"
              ></img>
              <span>내 복주머니 보러가기</span>
            </button>

            <button
              className="flex hover:underline items-center gap-2"
              onClick={() => {
                navigate("/my-message");
                toggleMenu();
              }}
            >
              <Suspense>
                <IoMailOutline className="mb-1" size={24} />
              </Suspense>
              <span>내가 보낸 메시지</span>
            </button>

            <button
              className="flex hover:underline items-center gap-2"
              onClick={() => setFeedbackModalOpen(true)}
            >
              <Suspense>
                <IoChatbubblesOutline className="mb-1" size={24} />
              </Suspense>
              <span>피드백하기</span>
            </button>
          </ul>
        )}

        {!myAddress && (
          <ul className="space-y-3">
            <button
              className="flex hover:underline items-center gap-2 my-16 text-lg"
              onClick={() => navigate("/")}
            >
              <IoPersonCircleOutline size={28} />
              <span className="mt-1">로그인 / 회원가입</span>
            </button>
          </ul>
        )}

        <hr className="border-t-2 border-gray-600 my-4 mx-auto" />

        <div>
          <ul className="space-y-2">
            <button
              className="flex hover:underline items-center gap-2"
              onClick={() => {
                if (window.sessionStorage.getItem("flag") !== null) {
                  window.sessionStorage.removeItem("flag");
                }
                navigate("/qna", { state: false });
                toggleMenu();
              }}
            >
              <IoHelpCircleOutline size={24} />
              <span className="mt-1">문의하기</span>
            </button>
          </ul>
        </div>

        <hr className="border-t-2 border-gray-600 my-4 mx-auto" />

        <footer className="text-center">Lucky Bocky!</footer>
      </div>

      {/* 피드백 모달 */}
      {feedbackModalOpen && (
        <div className="fixed inset-0 z-30 flex items-center justify-center bg-black bg-opacity-60">
          <div className="bg-white text-[#3c1e1e] rounded-lg w-80 p-4">
            <h2 className="text-xl mb-4">피드백하기</h2>

            <textarea
              className="border border-gray-300 rounded-md w-full h-60 p-2 mb-2 resize-none"
              placeholder="서비스에 대한 피드백을 남겨주세요 :)"
              value={feedback}
              onChange={(e) => setFeedback(e.target.value)}
            ></textarea>

            <div className="flex justify-between">
              <div className="flex gap-1 pl-1">
                {[1, 2, 3, 4, 5].map((star) => (
                  <button
                    key={star}
                    className={`${
                      star <= rating ? "text-yellow-500" : "text-gray-300"
                    }`}
                    onClick={() => setRating(star)}
                  >
                    ★
                  </button>
                ))}
              </div>
              <div className="flex gap-2">
                <button
                  className="bg-gray-300 text-black rounded-lg py-2 px-4"
                  onClick={closeModal}
                >
                  취소
                </button>
                <button
                  className="bg-blue-500 text-white rounded-lg py-2 px-4 "
                  onClick={sendFeedback}
                >
                  보내기
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {confirmCloseModal && (
        <div className="fixed inset-0 z-40 flex items-center justify-center bg-black bg-opacity-50">
          <div className="flex flex-col items-center bg-white rounded-lg shadow-lg w-80 p-6">
            <h2 className="text-xl text-[#3c1e1e] mb-4">
              작성한 내용이 사라집니다.
            </h2>
            <p className="text-gray-700 mb-6">정말 닫으시겠어요?</p>
            <div className="flex gap-4">
              <button
                className="bg-gray-300 text-black rounded-md py-2 px-4"
                onClick={() => setConfirmCloseModal(false)}
              >
                취소
              </button>
              <button
                className="bg-red-500 text-white rounded-md py-2 px-4"
                onClick={confirmClose}
              >
                닫기
              </button>
            </div>
          </div>
        </div>
      )}

      {/*피드백 성공 알림 */}
      {feedbackAlarm && (
        <div className="fixed bottom-16 bg-green-500 rounded-lg shadow-md py-2 px-4 z-30 transform -translate-x-1/2">
          피드백 전달 완료!
        </div>
      )}
    </div>
  );
};

export default Menu;
