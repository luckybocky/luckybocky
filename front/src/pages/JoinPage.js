import React, { useState, Suspense } from "react";
import { useNavigate, Navigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import Authservice from "../api/AuthService.ts";
import FirebaseService from "../api/FirebaseService.ts";

import Footer from "../components/Footer";
import Util from "../components/Util.js";

const IoShareOutline = Util.loadIcon("IoShareOutline").io5;
const AiOutlinePlusSquare = Util.loadIcon("AiOutlinePlusSquare").ai;
const IoBulbOutline = Util.loadIcon("IoBulbOutline").io5;

const JoinPage = () => {
  const navigate = useNavigate();

  const user = AuthStore((state) => state.user);
  const setUser = AuthStore((state) => state.setUser);

  const [nickname, setNickname] = useState("");
  const [isPublic, setIsPublic] = useState(false); // 공개 여부
  const [isAlarm, setIsAlarm] = useState(false); // 알람 여부
  const [notice, setNotice] = useState(false); // 브라우저 알림 체크
  const [isSubmitting, setIsSubmitting] = useState(false);

  const joinUser = async () => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    await setUser({
      ...user,
      userNickname: nickname,
      alarmStatus: isAlarm,
      fortuneVisibility: isPublic,
    });

    await Authservice.update();

    navigate(window.sessionStorage.getItem("pocketAddress")||`/${user.address}`);
    setIsSubmitting(false);
  };

  const updateAlarmStatus = async () => {
    const permission = Notification?.permission;

    try {
      if (permission === "undefined") {
        // 알림 API를 지원하지 않을 때 처리
        console.error("이 브라우저에서는 알림을 허용하지 않습니다.");
        setNotice(true);
        setTimeout(() => setNotice(false), 3000);
        return;
      }

      if (permission === "default") {
        FirebaseService.requestToken();
        return;
      }

      if (permission === "granted") {
        setIsAlarm(!isAlarm);
      } else {
        setNotice(true);
        setTimeout(() => setNotice(false), 3000);
      }
    } catch (error) {
      console.error(error);
    }
  };

  if (!isSubmitting&&(user.createdAt == null || user.userNickname !== null)) {
    return <Navigate to="/" replace />;
  }

  return (
    <div className="relative flex flex-col w-full max-w-[600px] bg-[#333] px-6 py-8">
      <h1 className="text-4xl mb-8">회원 가입</h1>
      <h1 className="text-2xl">새해 복 많이 받으세요 🙂</h1>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 mt-3 mb-10" />

      {/* 닉네임 변경 */}
      <label className="text-xl mb-2">닉네임</label>
      <input
        type="text"
        value={nickname}
        onChange={(e) => {
          const input = e.target.value;
          if (input.length <= 6) {
            setNickname(input); // 6자 이하일 때만 상태 업데이트
          }
        }}
        className="border p-2 rounded-md text-[#3c1e1e] mr-4"
      />
      <div>
        {(nickname?.length < 2 || nickname?.length > 6) && (
          <span className="absolute text-red-500 text-sm mt-1">
            닉네임은 2~6자 사이여야 합니다.
          </span>
        )}
      </div>

      {/* 구분선 추가 */}
      <hr className="border-t-2 border-gray-600 my-10" />

      {/* 메시지 공개 여부 */}
      <div className="flex mb-6">
        <label className="w-full md:w-8/12 mr-4">메시지 공개 여부</label>
        <div className="flex justify-between items-center gap-2">
          <div className="relative w-11 h-5">
            <input
              type="checkbox"
              checked={isPublic}
              onChange={(e) => setIsPublic(e.target.checked)}
              className="peer appearance-none w-11 h-5 bg-slate-100 rounded-full checked:bg-blue-500 cursor-pointer transition-colors duration-300"
            />
            <label className="absolute top-0 left-0 w-5 h-5 bg-white rounded-full border border-slate-300 shadow-sm transition-transform duration-300 peer-checked:translate-x-6 peer-checked:border-blue-600 cursor-pointer" />
          </div>
          <span className="ml-2 w-[80px]">
            {isPublic ? "전체 공개" : "나만 보기"}
          </span>
        </div>
      </div>

      {/* 알림 설정 여부 */}
      <div className="flex mb-6">
        <label className="w-full md:w-8/12 mr-4">알림 설정 여부</label>
        <div className="flex justify-between items-center gap-2">
          <div className="relative w-11 h-5">
            <input
              type="checkbox"
              checked={isAlarm}
              onChange={() => updateAlarmStatus()}
              className="peer appearance-none w-11 h-5 bg-slate-100 rounded-full checked:bg-blue-500 cursor-pointer transition-colors duration-300"
            />
            <label className="absolute top-0 left-0 w-5 h-5 bg-white rounded-full border border-slate-300 shadow-sm transition-transform duration-300 peer-checked:translate-x-6 peer-checked:border-blue-600 cursor-pointer" />
          </div>
          <span className="ml-2 w-[80px]">
            {isAlarm ? "알림 허용" : "알림 거절"}
          </span>
        </div>
      </div>

      {/* 알림이 브라우저에서 거부되어있을때 알려주기위함 */}
      {notice && (
        <div className="fixed bottom-16 bg-red-500 py-2 px-4 rounded-lg shadow-md left-1/2 transform -translate-x-1/2">
          <p className="whitespace-nowrap">브라우저 알림 설정을 확인해주세요</p>
        </div>
      )}

      

      {/* 아이폰 알림 설정 방법 안내 */}
      <div className="flex text-[#909090] items-center flex-wrap w-full pt-4">
        <div className="text-base text-[#91C2ED]">설정에 따라 알림이 차단될 수 있습니다.</div>
        <div className="text-sm text-[#A0A0A0] flex flex-col w-full pt-4 space-y-2">
          <div className="flex items-center">
            <Suspense><IoBulbOutline className="mr-2" /></Suspense>
            <span className="font-semibold">아이폰 알림 설정 방법:</span>
          </div>
          <ul className="list-decimal pl-5 space-y-1">
            <li className="flex items-center">
              Safari 하단 <Suspense><IoShareOutline className="mx-1" /></Suspense> 공유 버튼 클릭
            </li>
            <li className="flex items-center">
              홈 화면에 추가 <Suspense><AiOutlinePlusSquare className="mx-1" /></Suspense> 버튼 선택
            </li>
            <li className="flex items-center">
              앱 → 계정 설정 → 알림/팝업 허용
            </li>
          </ul>
        </div>
      </div>


      <hr className="border-t-2 border-gray-600 my-10" />

      <button
        className={`${
          nickname?.length >= 2 && nickname?.length <= 6
            ? "bg-white"
            : "bg-gray-400"
        } text-[#3c1e1e] text-2xl rounded-lg py-4`}
        onClick={joinUser}
        disabled={nickname?.length < 2 || nickname?.length > 6}
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
