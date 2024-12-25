import React from "react";
import MainImage from "../image/동백꽃바라.png";
import { useNavigate } from "react-router-dom";
import Menu from "../components/Menu";

const MainPage = () => {
  const navigate = useNavigate();

  return (
    <div className="relative flex flex-col items-center justify-center w-full max-w-[375px] min-h-screen bg-[#0d1a26] text-white overflow-hidden">
      <Menu />
      {/* 메인 화면 */}
      <h1 className="text-3xl font-bold mb-2">Lucky Bocky!</h1>
      <p className="text-lg mb-6">복 내놔라</p>
      <img src={MainImage} alt="복주머니 이미지" className="w-48 h-48 mb-6" />
      <button
        onClick={() => navigate("/")}
        className="text-lg bg-white text-[#0d1a26] py-4 px-20 rounded-lg"
      >
        내 복주머니 공유하기
      </button>
    </div>
  );
};

export default MainPage;
