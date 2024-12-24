import React from "react";
import "../App.css";
import MainImage from "../image/동백꽃바라.png";
import kakaoIcon from "../image/kakao-icon.png";
import { useNavigate } from "react-router-dom";

const LoginPage = () => {
  const navigate = useNavigate(); // useNavigate 훅 사용

  const handleKakaoLogin = () => {
    navigate("/main"); // 버튼 클릭 시 /home 경로로 이동
  };

  return (
    <div className="content">
      <h1 className="title">Lucky Bocky!</h1>
      <p className="subtitle">복 내놔라</p>
      <img src={MainImage} alt="복주머니 이미지" className="main-image" />
      <div className="login-buttons">
        <button className="login-button kakao" onClick={handleKakaoLogin}>
          <img src={kakaoIcon} alt="카카오 아이콘" className="login-icon" />
          <span>카카오계정으로 계속하기</span>
        </button>
        {/* <button className="login-button naver">네이버로 계속하기</button>
      <button className="login-button google">구글로 계속하기</button> */}
      </div>
    </div>
  );
};

export default LoginPage;
