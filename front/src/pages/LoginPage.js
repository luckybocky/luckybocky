import React, { useEffect } from "react";
import MainImage from "../image/pocket.png";
import kakaoIcon from "../image/kakao-icon.png";
import { useNavigate } from "react-router-dom";
import AuthStore from "../store/AuthStore";
import Footer from "../components/Footer";

const LoginPage = () => {
  const navigate = useNavigate();

  const user = AuthStore((state) => state.user);

  useEffect(() => {
    if (user.createdAt != null) navigate(`/${user.address}`);
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
    <div className="flex flex-col items-center justify-center text-center bg-[#0d1a26] text-white min-h-screen p-4">
      <h1 className="text-5xl font-bold mb-2">Lucky Bocky!</h1>
      <p className="text-3xl mb-6">복 내놔라</p>
      <img src={MainImage} alt="복주머니 이미지" className="w-60 h-60 mb-6" />
      <div className="flex flex-col gap-4">
        <button
          className="flex items-center justify-center gap-2 bg-[#fee500] text-[#3c1e1e] py-4 px-6 rounded-lg"
          onClick={handleKakaoLogin}
        >
          <img src={kakaoIcon} alt="카카오 아이콘" className="w-6 h-6" />
          <span className="mx-14">카카오계정으로 계속하기</span>
        </button>
      </div>
      <Footer />
    </div>
  );
};

export default LoginPage;
