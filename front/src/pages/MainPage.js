import React, { useState } from "react";
import "../App.css";
import MainImage from "../image/동백꽃바라.png";
import { useNavigate } from "react-router-dom";

const MainPage = () => {
  const [menuOpen, setMenuOpen] = useState(false); // 메뉴 열림 상태 관리

  const navigate = useNavigate();

  // 메뉴 열기/닫기 핸들러
  const toggleMenu = () => {
    setMenuOpen((prev) => !prev);
  };

  const login = () => {
    navigate("/");
  };

  return (
    <div className={`content ${menuOpen ? "menu-open" : ""}`}>
      {menuOpen && <div className="overlay" onClick={toggleMenu}></div>}
      <div className="menu-button" onClick={toggleMenu}>
        ☰
      </div>

      {/* 메뉴 */}
      <div className={`menu-bar ${menuOpen ? "menu-bar-open" : ""}`}>
        <ul className="menu-list">
          <li>메뉴1</li>
          <li>메뉴2</li>
          <li>메뉴3</li>
          <li>메뉴4</li>
          <li>메뉴5</li>
        </ul>
        <footer className="menu-footer">
          <p>Lucky Bocky!</p>
        </footer>
      </div>

      {/* 메인화면 */}
      <h1 className="title">Lucky Bocky!</h1>
      <p className="subtitle">복 내놔라</p>
      <img src={MainImage} alt="복주머니 이미지" className="main-image" />
      <button onClick={login}>버튼임둥</button>
    </div>
  );
};

export default MainPage;
