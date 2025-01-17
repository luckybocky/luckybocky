import React, { useState, Suspense } from "react";
import { useNavigate } from "react-router-dom";
import PocketIcon from "../image/pocketIcon.svg";
import FeedbackService from "../api/FeedbackService.ts";
import AuthStore from "../store/AuthStore";
import Util from "./Util";

const IoMenuSharp = Util.loadIcon("SlMenu").sl;
const IoSettingsOutline = Util.loadIcon("IoSettingsOutline").io5;
const IoMailOutline = Util.loadIcon("IoMailOutline").io5;
const IoChatbubblesOutline = Util.loadIcon("IoChatbubblesOutline").io5;

const Menu = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [feedbackModalOpen, setFeedbackModalOpen] = useState(false);
  const [feedback, setFeedback] = useState("");
  const [rating, setRating] = useState(0);
  const [feedbackAlarm, setFeedbackAlarm] = useState(false);
  const [confirmCloseModal, setConfirmCloseModal] = useState(false);

  const navigate = useNavigate();

  const myAddress = AuthStore((state) => state.user.address);

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

  const confirmFeedback = () => {
    const payload = {
      feedbackContent: feedback,
      feedbackRate: rating,
    };

    FeedbackService.save(payload);
    setFeedbackModalOpen(false);
    setRating(0);
    setFeedback("");

    setFeedbackAlarm(true);
    setTimeout(() => setFeedbackAlarm(false), 2000);
  };

  return (
    <div>
      {/* 오버레이 */}
      {menuOpen && (
        <div
          className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-60 z-10"
          onClick={toggleMenu}
        ></div>
      )}

      {/* 메뉴 버튼 */}
      <button
        className="absolute top-4 right-4 text-3xl z-20"
        onClick={toggleMenu}
      >
        <Suspense>
          <IoMenuSharp />
        </Suspense>
      </button>

      {/* 메뉴 바 */}
      <div
        className={`absolute top-0 right-0 h-full bg-[#333] transition-transform duration-300 ease-in-out z-20 ${
          menuOpen ? "translate-x-0" : "translate-x-full"
        }`}
        style={{ width: "270px" }}
      >
        {myAddress && (
          <ul className="py-3 px-6 space-y-5">
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
          <ul className="py-3 px-6 space-y-5">
            <button
              className="flex hover:underline items-center gap-2"
              onClick={() => navigate("/")}
            >
              <span className="text-2xl my-8">로그인 / 회원가입</span>
            </button>
          </ul>
        )}

        <footer className="border-t border-gray-600 p-4 text-center text-base">
          Lucky Bocky!
        </footer>
      </div>

      {/* 피드백 모달 */}
      {feedbackModalOpen && (
        <div className="fixed inset-0 z-30 flex items-center justify-center bg-black bg-opacity-60">
          <div className="bg-white text-[#0d1a26] p-4 rounded-lg w-80">
            <h2 className="text-xl mb-4">피드백하기</h2>
            <textarea
              className="w-full h-60 p-2 border border-gray-300 rounded-md mb-2 resize-none"
              placeholder="피드백 내용을 입력하세요."
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
                  className="bg-gray-300 text-black py-2 px-4 rounded-lg"
                  onClick={closeModal}
                >
                  취소
                </button>
                <button
                  className="bg-[#0d1a26] text-white py-2 px-4 rounded-lg"
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
          <div className="bg-white rounded-lg p-6 w-80 shadow-lg text-center">
            <h2 className="text-xl text-black mb-4">
              작성한 내용이 사라집니다.
            </h2>
            <p className="text-gray-700 mb-6">정말 닫으시겠어요?</p>
            <div className="flex justify-center gap-4">
              <button
                className="bg-gray-300 text-black py-2 px-4 rounded-md"
                onClick={() => setConfirmCloseModal(false)}
              >
                취소
              </button>
              <button
                className="bg-red-500 text-white py-2 px-4 rounded-md"
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
        <div className="fixed bottom-16 bg-green-500 text-white py-2 px-4 rounded-lg shadow-md z-30 transform -translate-x-1/2">
          피드백 전달 완료!
        </div>
      )}
    </div>
  );
};

export default Menu;
