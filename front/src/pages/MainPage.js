import React, { useState } from "react";
import MainImage from "../image/동백꽃바라.png";
import { useNavigate } from "react-router-dom";

const MainPage = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const navigate = useNavigate();

  const toggleMenu = () => setMenuOpen((prev) => !prev);

  return (
    <div className="relative flex flex-col items-center justify-center w-full max-w-[375px] min-h-screen bg-[#0d1a26] text-white">
      {/* 오버레이 */}
      {menuOpen && (
        <div
          className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-60 z-10"
          onClick={toggleMenu}
        ></div>
      )}

      {/* 메뉴 버튼 */}
      <button
        className="absolute top-4 right-4 text-2xl z-20"
        onClick={toggleMenu}
      >
        ☰
      </button>

      {/* 메뉴 바 */}
      <div
        className={`fixed top-0 right-0 h-full bg-[#333] text-white shadow-lg transition-transform duration-300 ease-in-out z-20 ${
          menuOpen ? "translate-x-0" : "translate-x-full"
        }`}
        style={{ width: "175px" }}
      >
        <ul className="p-4 space-y-4">
          <li className="hover:underline cursor-pointer">메뉴1</li>
          <li className="hover:underline cursor-pointer">메뉴2</li>
          <li className="hover:underline cursor-pointer">메뉴3</li>
          <li className="hover:underline cursor-pointer">메뉴4</li>
          <li className="hover:underline cursor-pointer">메뉴5</li>
        </ul>
        <footer className="border-t border-gray-600 p-4 text-center text-sm">
          Lucky Bocky!
        </footer>
      </div>

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
