import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import ArticleService from "../api/ArticleService.ts";

import fortuneImages from "../components/FortuneImages";

const WritePage = () => {
  const navigate = useNavigate();

  const userNickname = AuthStore((state) => state.user.userNickname);

  const [nickname, setNickname] = useState("");
  const [message, setMessage] = useState("");
  const [visibility, setVisibility] = useState(true);
  const [saveModalOpen, setSaveModalOpen] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const location = useLocation();
  const decorationId = location.state?.decorationId;
  const pocketAddress = location.state?.pocketAddress;
  const pocketSeq = location.state?.pocketSeq;

  const handleSubmit = async () => {
    if (!nickname || !message) {
      alert("닉네임과 메시지를 입력해주세요!");
      return;
    }

    setSaveModalOpen(true);
  };

  const confirmWrite = async () => {
    console.log(isSubmitting);
    if (isSubmitting) return;

    setIsSubmitting(true);

    const payload = {
      pocketSeq: pocketSeq,
      nickname: nickname,
      content: message,
      fortuneSeq: decorationId,
      visibility: visibility,
      url: `${window.location.origin}${window.location.pathname}`,
    };

    await ArticleService.save(payload);

    navigate("/" + pocketAddress);
    setSaveModalOpen(false);
  };

  useEffect(() => {
    setNickname(userNickname);
  }, []);

  useEffect(() => {}, [isSubmitting]);

  return (
    <div className="flex flex-col justify-center w-full max-w-[600px] bg-[#f5f5f5] text-[#3c1e1e] p-4">
      <h1 className="text-3xl text-center mb-24">메시지를 남겨주세요</h1>

      <div className="relative mb-2">
        <picture>
          <source srcSet={fortuneImages[decorationId].src} type="image/webp" />
          <img
            src={fortuneImages[decorationId].fallback}
            alt="Fortune"
            className="absolute top-[-75px] left-1/2 transform -translate-x-1/2 w-[100px] h-[100px]"
          />
        </picture>
        <textarea
          value={message}
          onChange={(e) => {
            const input = e.target.value;
            if (input.length <= 500) {
              setMessage(e.target.value); // 500자 이하일 때만 상태 업데이트
            }
          }}
          placeholder="메시지를 입력하세요"
          className="w-full h-60 border rounded-md p-2 pt-4 resize-none "
        />
      </div>

      <div className="flex flex-col">
        <input
          type="text"
          placeholder="닉네임을 입력하세요"
          value={nickname}
          onChange={(e) => {
            const input = e.target.value;
            if (input.length <= 6) {
              setNickname(input); // 6자 이하일 때만 상태 업데이트
            }
          }}
          className="border rounded-md p-2"
        />

        <div>
          {(nickname?.length < 2 || nickname?.length > 6) && (
            <span className="absolute text-red-500 text-sm mt-1 pl-1">
              닉네임은 2~6자 사이여야 합니다.
            </span>
          )}
        </div>
      </div>

      <div className="flex justify-between mt-8">
        <label className="flex items-center pl-1">
          <input
            type="checkbox"
            checked={!visibility}
            onChange={(e) => setVisibility(!e.target.checked)}
            className="mr-2 h-5 w-5"
          />
          비밀글
        </label>
        <div className="flex gap-2">
          <button
            onClick={() => navigate(-1)}
            className="bg-gray-500 text-white rounded-lg py-2 px-6"
          >
            이전
          </button>
          <button
            onClick={handleSubmit}
            className="bg-blue-500 text-white rounded-lg py-2 px-6"
          >
            저장
          </button>
        </div>
      </div>

      {saveModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-10">
          <div
            className="flex flex-col bg-white rounded-lg p-6 w-80 shadow-lg"
            onClick={(e) => e.stopPropagation()}
          >
            <h2 className="text-xl mb-4">이대로 복을 전달하시겠어요?</h2>
            <div className="flex justify-center gap-4">
              <button
                className="bg-gray-300 text-black rounded-md py-2 px-4"
                onClick={() => setSaveModalOpen(false)}
              >
                취소
              </button>
              <button
                className="bg-blue-500 text-white rounded-md py-2 px-4"
                onClick={confirmWrite}
              >
                저장
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default WritePage;
