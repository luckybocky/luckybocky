import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { IoArrowBack } from "react-icons/io5";
import AuthStore from "../store/AuthStore";
import Footer from "../components/Footer";
import { updateUser } from "../api/AuthApi";

const AccountPage = () => {
  const user = AuthStore((state) => state.user);
  const setUser = AuthStore((state) => state.setUser);

  const [nickname, setNickname] = useState(null);
  const [newNickname, setNewNickname] = useState(nickname);
  const [isPublic, setIsPublic] = useState(true); // 공개 여부
  const [changeMode, setChangeMode] = useState(false);

  useEffect(() => {
    if (nickname == null) setNickname(user.userNickname);
    if (user.createdAt) updateUser();
  }, [user]);

  const navigate = useNavigate();

  const saveNickname = () => {
    setUser({
      ...user,
      userNickname: nickname,
    });
  };

  const saveAlarmStatus = () => {
    setUser({
      ...user,
      alarmStatus: !user.alarmStatus,
    });
  };
  const saveFortuneVisibility = () => {
    setUser({
      ...user,
      fortuneVisibility: !user.fortuneVisibility,
    });
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
      <h1 className="text-5xl font-bold mb-8 mt-5">계정 설정</h1>
      <h1 className="text-3xl">{user.userNickname} 님</h1>

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
          disabled={!changeMode}
        />
        <button
          onClick={() => {
            if (changeMode) saveNickname();
            setChangeMode((prev) => !prev);
          }}
          className="bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 w-[50px]"
        >
          {changeMode ? "저장" : "변경"}
        </button>
      </div>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 mt-16 mb-10" />

      {/* 알림 설정 여부 */}
      <div className="flex items-center mb-8">
        <label className="mr-4">알림 설정 여부</label>
        <div className="flex items-center absolute right-10">
          <input
            type="checkbox"
            checked={user.alarmStatus}
            onChange={saveAlarmStatus}
            className="h-5 w-5"
          />
          <span className="ml-2 w-[30px]">
            {user.alarmStatus ? "허용" : "거절"}
          </span>
        </div>
      </div>

      {/* 메시지 공개 여부 */}
      <div className="flex items-center">
        <label className="mr-4">메시지 공개 여부</label>
        <div className="flex items-center absolute right-10">
          <input
            type="checkbox"
            checked={user.fortuneVisibility}
            onChange={saveFortuneVisibility}
            className="h-5 w-5"
          />
          <span className="ml-2 w-[30px]">
            {user.fortuneVisibility ? "공개" : "비밀"}
          </span>
        </div>
      </div>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 my-16" />
      <div>로그아웃</div>
      <Footer />
    </div>
  );
};

export default AccountPage;
