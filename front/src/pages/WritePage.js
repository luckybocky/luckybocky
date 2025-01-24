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
  const [visibilityModalOpen, setVisibilityModalOpen] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [cansSubmit, setCanSubmit] = useState(false);

  const location = useLocation();
  const decorationId = location.state?.decorationId;
  const pocketAddress = location.state?.pocketAddress;
  const pocketSeq = location.state?.pocketSeq;

  const confirmWrite = async () => {
    if (isSubmitting) return;

    setIsSubmitting(true);

    const payload = {
      pocketSeq: pocketSeq,
      nickname: nickname,
      content: message,
      fortuneSeq: decorationId,
      visibility: visibility,
    };

    await ArticleService.save(payload);

    //비회원
    if (userNickname === "") {
      navigate("/" + pocketAddress, {
        state: {
          afterWrite: true,
        },
      });
    } else {
      navigate("/" + pocketAddress);
    }

    setSaveModalOpen(false);
  };

  const changeVisibility = () => {
    //로그인이 된 유저
    if (userNickname !== "") {
      setVisibility(!visibility);
      return;
    }

    //비로그인 유저
    else {
      if (!visibility) {
        setVisibility(true);
        return;
      } else {
        setVisibilityModalOpen(true);
      }
    }
  }

  useEffect(() => {
    setNickname(userNickname);
  }, []);

  useEffect(() => {
    if (nickname?.length >= 2 && nickname?.length <= 6 && message?.length >= 1)
      setCanSubmit(true);
    else setCanSubmit(false);
  }, [nickname, message]);

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
            onChange={changeVisibility}
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
            onClick={() => { setSaveModalOpen(true) }}
            className={`${cansSubmit
              ? "bg-blue-500 hover:bg-blue-600"
              : "bg-gray-400 cursor-not-allowed"
              } text-white rounded-lg py-2 px-6`}
            disabled={!cansSubmit}
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

      {visibilityModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
          <div className="flex flex-col items-center bg-white shadow-lg rounded-lg w-80 p-6">
            <h2 className="text-xl text-[#3c1e1e] text-center mb-4">
              비로그인 시 이후에 <br />글을 확인할 수 없습니다.
            </h2>
            <p className="text-gray-700 mb-6">비밀글로 설정하시겠어요?</p>
            <div className="flex gap-4">
              <button
                className="bg-gray-300 text-black rounded-md py-2 px-4"
                onClick={() => setVisibilityModalOpen(false)}
              >
                취소
              </button>
              <button
                className="bg-red-500 text-white rounded-md py-2 px-4"
                onClick={() => {
                  setVisibility(false);
                  setVisibilityModalOpen(false);
                }}
              >
                확인
              </button>
            </div>
          </div>
        </div>
      )}

    </div>
  );
};

export default WritePage;
