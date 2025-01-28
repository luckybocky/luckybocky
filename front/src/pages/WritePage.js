import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import ArticleService from "../api/ArticleService.ts";
import ShareArticleService from "../api/ShareArticleService.ts";

import fortuneImages from "../components/FortuneImages";

const WritePage = () => {
  const navigate = useNavigate();

  const userNickname = AuthStore((state) => state.user.userNickname);

  // const [nickname, setNickname] = useState("");
  const [message, setMessage] = useState("");
  // const [visibility, setVisibility] = useState(true);
  const [saveModalOpen, setSaveModalOpen] = useState(false);
  const [confirmClearModal, setConfirmClearModal] = useState(false);
  // const [visibilityModalOpen, setVisibilityModalOpen] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [currentDecoration, setCurrentDecoration] = useState(0);

  const location = useLocation();
  // const decorationId = location.state?.decorationId;
  const pocketAddress = location.state?.pocketAddress;
  const pocketSeq = location.state?.pocketSeq;
  const share = location.state?.share;

  const handleImageSelect = (index) => {
    setCurrentDecoration(index);
  };

  const confirmWrite = async () => {
    if (isSubmitting) return;

    setIsSubmitting(true);

    const payload = {
      pocketSeq: pocketSeq,
      nickname: userNickname,
      content: message,
      fortuneSeq: currentDecoration,
      // visibility: visibility,
    };

    await ArticleService.save(payload);

    //비회원
    // if (userNickname === "") {
    //   navigate("/" + pocketAddress, {
    //     state: {
    //       afterWrite: true,
    //     },
    //   });
    // } else {
    navigate("/" + pocketAddress);
    // }

    setSaveModalOpen(false);
  };

  const confirmShare = async () => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    const payload = {
      content: message,
      fortuneSeq: currentDecoration,
    };

    const result = await ShareArticleService.save(payload);

    navigate(`/share/${result.shareArticleAddress}`)
  };

  // const changeVisibility = () => {
  //   //로그인이 된 유저
  //   if (userNickname !== "") {
  //     setVisibility(!visibility);
  //     return;
  //   }

  //   //비로그인 유저
  //   else {
  //     if (!visibility) {
  //       setVisibility(true);
  //       return;
  //     } else {
  //       setVisibilityModalOpen(true);
  //     }
  //   }
  // }

  // useEffect(() => {
  //   setNickname(userNickname);
  // }, []);

  // 전체 지우기 버튼을 위한 함수
  const confirmClear = () => {
    setMessage("");
    setConfirmClearModal(false);
  };

  useEffect(() => {
    setMessage(fortuneImages[currentDecoration].comment)
  }, [currentDecoration]);

  return (
    <div className="flex flex-col justify-center items-center w-full max-w-[600px] bg-[#f5f5f5] text-[#3c1e1e] py-6 px-4">
      <h1 className="text-xl text-center mb-10">상대방에게 전하고 싶은 복과 <br />
        마음을 담은 메세지를 남겨주세요</h1>

      {/* 가로 스크롤 이미지 목록 */}
      <div className="flex space-x-4 overflow-x-auto mb-4 w-full max-w-[450px]">
        {fortuneImages.map((image, index) => (
          <div
            key={index}
            onClick={() => handleImageSelect(index)}
            className={`cursor-pointer ${index === currentDecoration ? "border-2 border-gray-500 border-dashed rounded-lg" : "border-2 border-black border-opacity-0"}`}
          >
            <picture>
              <source
                srcSet={image.src}
                type="image/webp"
              />
              <img
                src={image.fallback}
                alt={image.name}
                className="w-16 h-16 min-w-[64px]"
              />
            </picture>
          </div>
        ))}
      </div>

      {/* 장식 이미지 */}
      <div className="flex space-x-14 mb-4">
        {/* < 이전 버튼 */}
        {/* <button
          onClick={handlePrev}
          className="text-gray-400 text-2xl"
        >
          &lt;
        </button> */}

        <picture className="flex flex-col items-center">
          <span className="text-sm">{fortuneImages[currentDecoration].name}</span>
          <source
            srcSet={fortuneImages[currentDecoration].src}
            type="image/webp"
          />
          <img
            src={fortuneImages[currentDecoration].fallback}
            alt="Fortune"
            className="w-[100px] h-[100px]"
          />
        </picture>

        {/* > 다음 버튼 */}
        {/* <button
          onClick={handleNext}
          className="text-gray-400 text-2xl"
        >
          &gt;
        </button> */}
      </div>

      {/* 메시지 입력 */}
      <div className="relative mb-2 w-full">
        {/* <picture>
          <source srcSet={fortuneImages[decorationId].src} type="image/webp" />
          <img
            src={fortuneImages[decorationId].fallback}
            alt="Fortune"
            className="absolute top-[-75px] left-1/2 transform -translate-x-1/2 w-[100px] h-[100px]"
          />
        </picture> */}
        <textarea
          value={message}
          onChange={(e) => {
            const input = e.target.value;
            if (input.length <= 500) {
              setMessage(e.target.value); // 500자 이하일 때만 상태 업데이트
            }
          }}
          placeholder="메시지를 입력하세요"
          className="w-full h-72 border rounded-md p-2 pt-4 resize-none"
        />

        {/* 글자 수 표시 */}
        <div className="text-end text-sm text-gray-600">
          <span>{message.length}</span> / 500
        </div>
      </div>

      {/* <div className="flex flex-col">
        <input
          type="text"
          // placeholder="닉네임을 입력하세요"
          value={userNickname}
          className="border rounded-md p-2"
          disabled={true}
        />

        <div>
          {(nickname?.length < 2 || nickname?.length > 6) && (
            <span className="absolute text-red-500 text-sm mt-1 pl-1">
              닉네임은 2~6자 사이여야 합니다.
            </span>
          )}
        </div>
      </div> */}

      <div className="w-full flex justify-between">
        {/* <label className="flex items-center pl-1">
          <input
            type="checkbox"
            checked={!visibility}
            onChange={changeVisibility}
            className="mr-2 h-5 w-5"
          />
          비밀글
        </label> */}
        <button
          onClick={() => setConfirmClearModal(true)} // 전체 지우기 버튼 클릭 시
          className={`${message ? "bg-red-500" : "bg-gray-400"} flex text-white rounded-lg py-2 px-4`}
          disabled={!message}
        >
          {/* `${
                  message ? "bg-green-500" : "bg-gray-400"
                } rounded-md py-1 px-4`} */}
          지우기
        </button>

        <div className="flex gap-2">
          <button
            onClick={() => navigate(-1)}
            className="bg-gray-500 text-white rounded-lg py-2 px-6"
          >
            이전
          </button>

          <button
            onClick={() => { setSaveModalOpen(true) }}
            className={`${message
              ? "bg-blue-500 hover:bg-blue-600"
              : "bg-gray-400 cursor-not-allowed"
              } text-white rounded-lg py-2 px-6`}
            disabled={!message}
          >
            {share ? "전달" : "저장"}
          </button>
        </div>
      </div>

      {saveModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-10">
          <div
            className="flex flex-col bg-white rounded-lg p-6 w-80 shadow-lg"
            onClick={(e) => e.stopPropagation()}
          >
            <h2 className="text-xl mb-4 text-center">
              {share ? "이대로 복을 전달하시겠어요?" : "이대로 복을 남기시겠어요?"}</h2>
            <div className="flex justify-center gap-4">
              <button
                className="bg-gray-300 text-black rounded-md py-2 px-4"
                onClick={() => setSaveModalOpen(false)}
              >
                취소
              </button>
              <button
                className="bg-blue-500 text-white rounded-md py-2 px-4"
                onClick={share ? confirmShare : confirmWrite}
              >
                {share ? "전달" : "저장"}
              </button>
            </div>
          </div>
        </div>
      )}

      {confirmClearModal && (
        <div className="fixed inset-0 z-40 flex items-center justify-center bg-black bg-opacity-50">
          <div className="flex flex-col items-center bg-white rounded-lg shadow-lg w-80 p-6">
            <h2 className="text-xl text-[#3c1e1e] mb-4">
              작성한 내용이 사라집니다.
            </h2>
            <p className="text-gray-700 mb-6">정말 지우시겠어요?</p>
            <div className="flex gap-4">
              <button
                className="bg-gray-300 text-black rounded-md py-2 px-4"
                onClick={() => setConfirmClearModal(false)}
              >
                취소
              </button>
              <button
                className="bg-red-500 text-white rounded-md py-2 px-4"
                onClick={confirmClear}
              >
                지우기
              </button>
            </div>
          </div>
        </div>
      )}

      {/* {visibilityModalOpen && (
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
      )} */}

    </div>
  );
};

export default WritePage;
