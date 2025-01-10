import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { IoArrowBack } from "react-icons/io5";
import AuthStore from "../store/AuthStore";
import Footer from "../components/Footer";
import { updateUser, logout } from "../api/AuthApi";

const AccountPage = () => {
  const user = AuthStore((state) => state.user);
  const setUser = AuthStore((state) => state.setUser);

  const [nickname, setNickname] = useState(null);
  const [changeMode, setChangeMode] = useState(false);
  const [saved, setSaved] = useState(false);
  const [logoutModalOpen, setLogoutModalOpen] = useState(false);

  useEffect(() => {
    if (!user.createdAt) navigate("/");
    if (nickname == null) setNickname(user.userNickname);
    updateUser();
  }, [user]);

  const navigate = useNavigate();

  const saveNickname = () => {
    setUser({
      ...user,
      userNickname: nickname,
    });

    setSaved(true);
    setTimeout(() => setSaved(false), 2000);
  };

  const saveAlarmStatus = () => {
    setUser({
      ...user,
      alarmStatus: !user.alarmStatus,
    });

    setSaved(true);
    setTimeout(() => setSaved(false), 2000);
  };
  const saveFortuneVisibility = () => {
    setUser({
      ...user,
      fortuneVisibility: !user.fortuneVisibility,
    });

    setSaved(true);
    setTimeout(() => setSaved(false), 2000);
  };

  const logoutButton = () => {
    setLogoutModalOpen(true);
  };

  const confirmLogout = async () => {
    setLogoutModalOpen(false);
    navigate("/");
    const setUser = AuthStore.getState().setUser;
    setUser({
      userNickname: null,
      alarmStatus: null,
      fortuneVisibility: null,
      createdAt: null,
      address: null,
    });

    await logout();
  };

  return (
    <div className="relative flex flex-col w-full max-w-[375px] min-h-screen bg-[#333] p-2 text-white overflow-hidden">
      {/* 뒤로 가기 버튼 */}
      <button
        className="absolute top-4 right-4 text-2xl z-20"
        onClick={() => navigate(-1)}
      >
        <IoArrowBack />
      </button>

      {/* 계정 설정 화면 */}
      <h1 className="text-3xl mb-8 mt-5">계정 설정</h1>
      <h1 className="text-xl">
        <span className="text-[pink]">{user.userNickname}</span> 님
      </h1>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 mt-3 mb-10" />

      {/* 닉네임 변경 */}
      <label className="mb-1">닉네임</label>
      <div className="flex items-center">
        <input
          type="text"
          value={nickname}
          onChange={(e) => {
            const input = e.target.value;
            if (input.length <= 8) {
              setNickname(input); // 8자 이하일 때만 상태 업데이트
            }
          }}
          className="border p-2 rounded-md text-black mr-4 w-full"
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
          <span className="ml-2 w-[35px]">
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
          <span className="ml-2 w-[35px]">
            {user.fortuneVisibility ? "공개" : "비밀"}
          </span>
        </div>
      </div>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 my-16" />
      <div>
        <button onClick={logoutButton}>로그아웃</button>
      </div>
      <div className="flex justify-center">
        <Footer />
      </div>

      {/* 변경 성공 알림 */}
      {saved && (
        <div className="fixed bottom-16 bg-green-500 text-white py-2 px-4 rounded-lg shadow-md left-1/2 transform -translate-x-1/2">
          변경 완료!
        </div>
      )}

      {logoutModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div
            className="bg-white rounded-lg p-6 w-80 shadow-lg text-center"
            onClick={(e) => e.stopPropagation()}
          >
            <h2 className="text-lg text-black mb-4">로그아웃 하시겠어요?</h2>
            <div className="flex justify-center gap-4">
              <button
                className="bg-gray-300 text-black py-2 px-4 rounded-md"
                onClick={() => setLogoutModalOpen(false)}
              >
                취소
              </button>
              <button
                className="bg-red-500 text-white py-2 px-4 rounded-md"
                onClick={confirmLogout}
              >
                로그아웃
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default AccountPage;
