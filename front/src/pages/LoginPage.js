import React, { useEffect } from "react";
import MainImage from "../image/landing.png";
import kakaoIcon from "../image/kakao-icon.png";
import { useNavigate } from "react-router-dom";
import AuthStore from "../store/AuthStore";
import Footer from "../components/Footer";
import { sendLog } from "../api/FireBaseApi";

import { requestFcmToken } from "../api/FireBaseApi"; //12-31 창희 추가, 파이어베이스 api들고오기
const LoginPage = () => {
  const navigate = useNavigate();

  const user = AuthStore((state) => state.user);

  useEffect(() => {
    if (user.createdAt != null) {
      requestFcmToken();
      navigate(`/${user.address}`);
    }
  }, [user, navigate]);

  // 카카오 소셜 로그인 / 로그아웃
  const REST_API_KEY = process.env.REACT_APP_REST_API_KEY;
  const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
  const LOGOUT_REDIRECT_URI = process.env.REACT_APP_LOGOUT_REDIRECT_URI;
  const LogoutLink = `https://kauth.kakao.com/oauth/logout?client_id=${REST_API_KEY}&logout_redirect_uri=${LOGOUT_REDIRECT_URI}`;

  const handleKakaoLogin = () => {
    const currentPath = window.location.pathname;
    console.log(currentPath);

    const loginLink = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&state=${currentPath}`;

    window.location.href = loginLink;
  };

  return (
    <div className="relative flex flex-col items-center justify-center text-center bg-[#ba947f] text-white min-h-screen p-2 w-full max-w-[600px]">
      <h1 className="text-5xl mb-2">Lucky Bocky!</h1>
      <p className="text-2xl mb-6">복 내놔라</p>
      <img src={MainImage} alt="복주머니 이미지" className="w-72 h-72 mb-6" />
      <button
        className="max-w-[375px] w-full flex items-center justify-center gap-2 bg-[#fee500] text-[#3c1e1e] py-4 px-6 rounded-lg"
        onClick={handleKakaoLogin}
      >
        <img src={kakaoIcon} alt="카카오 아이콘" className="w-6 h-6" />
        <span className="mx-8 pt-1">카카오계정으로 계속하기</span>
      </button>
      <Footer />
    </div>
  );
};

export default LoginPage;
