import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { writeArticle } from "../api/ArticleApi";
import { sendArticlePush } from "../api/FireBaseApi";

const WritePage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const decorationId = location.state?.decorationId;
  const pocketAddress = location.state?.pocketAddress;
  const visibility = location.state?.visibility;
  const pocketSeq = location.state?.pocketSeq;

  const [nickname, setNickname] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async () => {
    if (!nickname || !message) {
      alert("닉네임과 메시지를 입력해주세요!");
      return;
    }

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
  };

  return (
    <div className="flex flex-col items-center justify-center w-full max-w-[375px] min-h-screen bg-[#f5f5f5] text-black mx-auto p-2">
      <h1 className="text-2xl mb-4">메시지를 남겨주세요</h1>
      <textarea
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        placeholder="메시지를 입력하세요"
        className="w-full h-32 p-2 border rounded-md mb-4 resize-none"
      />
      <div className="w-full mb-4">
        <input
          type="text"
          placeholder="닉네임을 입력하세요"
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
          className="w-full p-2 border rounded-md"
        />
      </div>
      <div className="flex gap-2">
        <button
          onClick={() => navigate(-1)}
          className="bg-gray-500 text-white py-2 px-6 rounded-md"
        >
          이전
        </button>
        <button
          onClick={handleSubmit}
          className="bg-blue-500 text-white py-2 px-6 rounded-md"
        >
          저장
        </button>
      </div>
    </div>
  );
};

export default WritePage;
