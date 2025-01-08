import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { writeArticle } from "../api/ArticleApi";
import { sendArticlePush } from "../api/FireBaseApi";
import AuthStore from "../store/AuthStore";
import fortuneImages from "../components/FortuneImages";

const WritePage = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const userNickname = AuthStore((state) => state.user.userNickname);
  const decorationId = location.state?.decorationId;
  const pocketAddress = location.state?.pocketAddress;
  // const visibility = location.state?.visibility;
  const pocketSeq = location.state?.pocketSeq;

  const [nickname, setNickname] = useState("");
  const [message, setMessage] = useState("");
  const [visibility, setVisibility] = useState(false);
  const [saveModalOpen, setSaveModalOpen] = useState(false);

  const handleSubmit = async () => {
    if (!nickname || !message) {
      alert("닉네임과 메시지를 입력해주세요!");
      return;
    }

    setSaveModalOpen(true);
  };

  const confirmWrite = async () => {
    const payload = {
      pocketSeq,
      pocketAddress,
      visibility,
      decorationId,
      nickname,
      message,
    };

    await writeArticle(payload);

    navigate("/" + pocketAddress);

    //=====12-31 창희 추가 start=====
    //복주머니에 복을 넣을때
    sendArticlePush(pocketSeq);
    //=====12-31 창희 추가 end=====
    setSaveModalOpen(false);
  };

  useEffect(() => {
    if (userNickname !== null) setNickname(userNickname);
  }, [userNickname]);

  return (
    <div className="flex flex-col justify-center w-full max-w-[375px] min-h-screen bg-[#f5f5f5] text-black mx-auto p-2">
      <h1 className="text-2xl mb-24 text-center">메시지를 남겨주세요</h1>
      <div className="relative w-full mb-2">
        {/* 이미지 추가 */}
        <img
          src={fortuneImages[decorationId]}
          alt="Fortune"
          className="absolute top-[-75px] left-1/2 transform -translate-x-1/2 w-[100px] h-[100px] object-contain"
        />
        <textarea
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="메시지를 입력하세요"
          className="w-full h-60 p-2 pt-4 border rounded-md resize-none"
        />
      </div>
      <div className="w-full mb-4">
        <input
          type="text"
          placeholder="닉네임을 입력하세요"
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
          className="w-full p-2 border rounded-md"
        />
      </div>
      <div className="flex justify-between">
        <label className="flex items-center pl-1">
          <input
            type="checkbox"
            checked={visibility}
            onChange={(e) => setVisibility(e.target.checked)}
            className="mr-2 h-5 w-5"
          />
          비밀글
        </label>
        <div className="flex gap-2">
          <button
            onClick={() => navigate(-1)}
            className="bg-gray-500 text-white py-2 px-6 rounded-lg"
          >
            이전
          </button>
          <button
            onClick={handleSubmit}
            className="bg-blue-500 text-white py-2 px-6 rounded-lg"
          >
            저장
          </button>
        </div>
      </div>

      {saveModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div
            className="bg-white rounded-lg p-6 w-80 shadow-lg text-center"
            onClick={(e) => e.stopPropagation()}
          >
            <h2 className="text-lg text-black mb-4">
              이대로 복을 전달하시겠어요?
            </h2>
            <div className="flex justify-center gap-4">
              <button
                className="bg-gray-300 text-black py-2 px-4 rounded-md"
                onClick={() => setSaveModalOpen(false)}
              >
                취소
              </button>
              <button
                className="bg-blue-500 text-white py-2 px-4 rounded-md"
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
