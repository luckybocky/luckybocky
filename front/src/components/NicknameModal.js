import React, { useState, useEffect } from "react";
import AuthStore from "../store/AuthStore";

const NicknameModal = () => {
  const [nickname, setNickname] = useState(""); // 닉네임 상태
  const [isNicknameModalOpen, setIsNicknameModalOpen] = useState(true); // 닉네임 모달 상태

  const userNickname = AuthStore((state) => state.user.userNickname);

  // 닉네임 모달 초기화
  useEffect(() => {
    console.log(userNickname);
    if (userNickname) {
      setIsNicknameModalOpen(false);
    }
  }, [userNickname]);

  const handleNicknameSubmit = () => {
    if (nickname.trim()) {
      setIsNicknameModalOpen(false);
    } else {
      alert("닉네임을 입력해주세요.");
    }
  };
  return (
    <>
      {/* 닉네임 모달 */}
      {isNicknameModalOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-70 z-50">
          <div className="bg-white p-6 rounded-lg w-80 text-center">
            <h2 className="text-2xl font-bold mb-4 text-[#0d1a26]">
              닉네임 입력
            </h2>
            <input
              type="text"
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
              placeholder="닉네임을 입력하세요"
              className="w-full px-4 py-2 border rounded-lg mb-4 text-[#0d1a26]"
            />
            <button
              onClick={handleNicknameSubmit}
              className="bg-[#0d1a26] text-white px-6 py-2 rounded-lg"
            >
              확인
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default NicknameModal;
