import React from "react";
import { Navigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import landingImageP from "../image/landingImage.png";
import landingImageW from "../image/landingImage.png";
import MainImageP from "../image/landing.png";
import MainImageW from "../image/landing.png";
import kakaoIcon from "../image/kakao-icon.png";
import kakaoIconW from "../image/kakao-icon.png";

import Footer from "../components/Footer";

const LoginPage = () => {
  const user = AuthStore((state) => state.user);

  // 카카오 소셜 로그인 / 로그아웃
  const REST_API_KEY = process.env.REACT_APP_REST_API_KEY;
  const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
  const LOGOUT_REDIRECT_URI = process.env.REACT_APP_LOGOUT_REDIRECT_URI;
  const LogoutLink = `https://kauth.kakao.com/oauth/logout?client_id=${REST_API_KEY}&logout_redirect_uri=${LOGOUT_REDIRECT_URI}`;

  const handleKakaoLogin = () => {
    const currentPath = window.location.pathname;

    const loginLink = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&state=${currentPath}`;

    window.location.href = loginLink;
  };

  if (user.createdAt) {
    return <Navigate to={`/${user.address}`} replace />;
  }

  return (
    <div className="relative flex flex-col items-center justify-center p-6 w-full max-w-[600px] z-20">
      <picture>
        <div className="absolute inset-0 bg-gradient-to-b from-black/15 to-transparent -z-10"></div>
        <source srcSet={landingImageW} type="image/webp" />
        <img
          src={landingImageP}
          alt="Background"
          className="absolute inset-0 w-full h-full object-fill -z-20"
        />
      </picture>

      <h1 className="text-4xl mb-12">LUCKY BOCKY!</h1>

      <picture>
        <source srcSet={MainImageW} type="image/webp" />
        <img
          src={MainImageP}
          alt="복주머니 이미지"
          className="w-72 h-72 mb-6"
        />
      </picture>

      <button
        className="max-w-[350px] w-full flex justify-center gap-2 bg-[#fee500] text-[#3c1e1e] py-3 rounded-lg"
        onClick={handleKakaoLogin}
      >
        <picture>
          <source srcSet={kakaoIconW} type="image/webp" />
          <img src={kakaoIcon} alt="카카오 아이콘" className="w-8 h-8" />
        </picture>
        <span className="mx-4 pt-1.5">카카오계정으로 계속하기</span>
      </button>

      <Footer />
    </div>
  );
};

export default LoginPage;
