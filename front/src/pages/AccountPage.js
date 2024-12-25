import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { IoArrowBack } from "react-icons/io5";

const AccountPage = () => {
  const [nickname, setNickname] = useState("사용자123");
  const [newNickname, setNewNickname] = useState(nickname);
  const [isPublic, setIsPublic] = useState(true); // 공개 여부

  const navigate = useNavigate();

  const handleNicknameChange = () => {
    setNickname(newNickname);
  };

  const handlePublicChange = (event) => {
    setIsPublic(event.target.checked);
  };

  return (
    <div className="relative flex flex-col w-full max-w-[375px] min-h-screen bg-[#0d1a26] text-white overflow-hidden">
      {/* 뒤로 가기 버튼 */}
      <button
        className="absolute top-4 right-4 text-2xl z-20"
        onClick={() => navigate(-1)}
      >
        <IoArrowBack />
      </button>

      {/* 계정 설정 화면 */}
      <h1 className="text-3xl font-bold mb-2 mt-5">계정 설정</h1>

      {/* 닉네임 변경 */}
      <div className="flex items-center mb-6">
        <label className="text-lg mr-4">닉네임:</label>
        <input
          type="text"
          value={newNickname}
          onChange={(e) => setNewNickname(e.target.value)}
          className="border p-2 rounded-lg text-black mr-4"
        />
        <button
          onClick={handleNicknameChange}
          className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
        >
          변경
        </button>
      </div>

      {/* 현재 닉네임 */}
      <div className="mb-6">
        <p className="text-lg">
          <strong>현재 닉네임:</strong> {nickname}
        </p>
      </div>

      {/* 메시지 공개 여부 */}
      <div className="flex items-center mb-6">
        <label className="text-lg mr-4">메시지 공개 여부:</label>
        <input
          type="checkbox"
          checked={isPublic}
          onChange={handlePublicChange}
          className="h-5 w-5"
        />
        <span className="ml-2 text-lg">{isPublic ? "공개" : "비공개"}</span>
      </div>
    </div>
  );
};

export default AccountPage;
