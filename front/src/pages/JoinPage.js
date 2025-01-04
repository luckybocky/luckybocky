import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthStore from "../store/AuthStore";
import Footer from "../components/Footer";
import { updateUser } from "../api/AuthApi";

const JoinPage = () => {
  const user = AuthStore((state) => state.user);
  const setUser = AuthStore((state) => state.setUser);

  const [nickname, setNickname] = useState(null);
  const [isPublic, setIsPublic] = useState(false); // 공개 여부
  const [isAlarm, setIsAlarm] = useState(false); // 알람 여부

  const navigate = useNavigate();

  const joinUser = () => {
    setUser({
      ...user,
      userNickname: nickname,
      alarmStatus: isAlarm,
      fortuneVisibility: isPublic,
    });

    updateUser();

    navigate(`/${user.address}`);
  };

  return (
    <div className="relative flex flex-col w-full max-w-[375px] min-h-screen bg-[#0d1a26] p-2 text-white overflow-hidden">
      {/* 계정 설정 화면 */}
      <h1 className="text-3xl mb-8 mt-5">회원 가입</h1>
      <h1 className="text-xl">안녕하세요!!</h1>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 mt-3 mb-10" />

      {/* 닉네임 변경 */}
      <label className="mb-1">닉네임</label>
      <div className="flex items-center">
        <input
          type="text"
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
          className="border p-2 rounded-lg text-black mr-4 w-full"
        />
      </div>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 mt-16 mb-10" />

      {/* 알림 설정 여부 */}
      <div className="flex items-center mb-8">
        <label className="mr-4">알림 설정 여부</label>
        <div className="flex items-center absolute right-10">
          <input
            type="checkbox"
            checked={isAlarm}
            onChange={(e) => setIsAlarm(e.target.checked)}
            className="h-5 w-5"
          />
          <span className="ml-2 w-[35px]">{isAlarm ? "허용" : "거절"}</span>
        </div>
      </div>

      {/* 메시지 공개 여부 */}
      <div className="flex items-center">
        <label className="mr-4">메시지 공개 여부</label>
        <div className="flex items-center absolute right-10">
          <input
            type="checkbox"
            checked={isPublic}
            onChange={(e) => setIsPublic(e.target.checked)}
            className="h-5 w-5"
          />
          <span className="ml-2 w-[35px]">{isPublic ? "공개" : "비밀"}</span>
        </div>
      </div>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 mt-16 mb-12" />
      <button
        className="bg-white text-[#0d1a26] py-4 rounded-lg text-xl"
        onClick={joinUser}
      >
        <span className="flex justify-center pt-1">시작하기</span>
      </button>
      <div className="flex justify-center">
        <Footer />
      </div>
    </div>
  );
};

export default JoinPage;
