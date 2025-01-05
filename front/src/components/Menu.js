import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  IoSettingsOutline,
  IoMailOutline,
  IoChatbubblesOutline,
} from "react-icons/io5";
import { AiOutlineAlert } from "react-icons/ai";
import PocketIcon from "../image/pocketIcon.svg";
import { saveFeedback } from "../api/FeedbackApi";
import { saveReport } from "../api/ReportApi";
import AuthStore from "../store/AuthStore";

const Menu = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [feedbackModalOpen, setFeedbackModalOpen] = useState(false);
  const [reportModalOpen, setReportModalOpen] = useState(false);
  const [feedback, setFeedback] = useState("");
  const [report, setReport] = useState("");
  const [rating, setRating] = useState(0);

  const navigate = useNavigate();

  const myAddress = AuthStore((state) => state.user.address);

  const toggleMenu = () => setMenuOpen((prev) => !prev);

  const closeModals = () => {
    setFeedbackModalOpen(false);
    setReportModalOpen(false);
    setRating(0);
  };

  const sendFeedback = () => {
    if (feedback === "") {
      alert("피드백 내용을 입력해주세요.");
    } else if (rating === 0) {
      alert("별점을 선택해주세요.");
    } else {
      saveFeedback(feedback, rating);
      setFeedbackModalOpen(false);
      setRating(0);
      setFeedback("");
    }
  };

  const sendReport = () => {
    if (report === "") {
      alert("신고 내용을 입력해주세요.");
      return;
    } else {
      saveReport(1, 0, report);
      alert("감사합니다. 신고 완료되었습니다.");
    }

    setReport("");
    setReportModalOpen(false);
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
        className="absolute top-4 right-4 text-2xl z-20"
        onClick={toggleMenu}
      >
        ☰
      </button>

      {/* 메뉴 바 */}
      <div
        className={`absolute top-0 left-28 h-full bg-[#333] text-white shadow-lg transition-transform duration-300 ease-in-out z-20 ${
          menuOpen ? "translate-x-0" : "translate-x-full"
        }`}
        style={{ width: "275px" }}
      >
        <ul className="py-3 px-6 space-y-5">
          <button
            onClick={() => navigate("/account")}
            className="flex hover:underline items-center gap-2"
          >
            <IoSettingsOutline />
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
              width="18"
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
            <IoMailOutline className="mb-1" />
            <span>내가 보낸 메시지</span>
          </button>
          <button
            className="flex hover:underline items-center gap-2"
            onClick={() => setFeedbackModalOpen(true)}
          >
            <IoChatbubblesOutline className="mb-1" />
            <span>피드백하기</span>
          </button>
          <button
            className="flex hover:underline items-center gap-2"
            onClick={() => setReportModalOpen(true)}
          >
            <AiOutlineAlert className="mb-1" />
            <span className="">신고하기</span>
          </button>
        </ul>
        <footer className="border-t border-gray-600 p-4 text-center text-sm">
          Lucky Bocky!
        </footer>
      </div>
      {/* 피드백 모달 */}
      {feedbackModalOpen && (
        <div
          className="fixed inset-0 z-30 flex items-center justify-center bg-black bg-opacity-60"
          onClick={closeModals}
        >
          <div
            className="bg-white text-[#0d1a26] p-5 rounded-lg w-80"
            onClick={(e) => e.stopPropagation()}
          >
            <h2 className="text-lg mb-4">피드백하기</h2>
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
                  onClick={closeModals}
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

      {/* 신고 모달 */}
      {reportModalOpen && (
        <div
          className="fixed inset-0 z-30 flex items-center justify-center bg-black bg-opacity-60"
          onClick={closeModals}
        >
          <div
            className="bg-white text-[#0d1a26] p-5 rounded-lg w-80"
            onClick={(e) => e.stopPropagation()}
          >
            <h2 className="text-lg mb-4">신고하기</h2>
            <input
              className="w-full p-2 border border-gray-300 rounded-md mb-4"
              placeholder="제목을 입력하세요."
            />
            <textarea
              className="w-full h-48 p-2 border border-gray-300 rounded-md mb-2 resize-none"
              placeholder="신고 내용을 입력하세요."
              value={report}
              onChange={(e) => setReport(e.target.value)}
            ></textarea>
            <div className="flex justify-end gap-2">
              <button
                className="bg-gray-300 text-black py-2 px-4 rounded-lg"
                onClick={closeModals}
              >
                취소
              </button>
              <button
                className="bg-[#0d1a26] text-white py-2 px-4 rounded-lg"
                onClick={sendReport}
              >
                보내기
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Menu;
