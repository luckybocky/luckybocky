import React, { useState } from "react";
import MainImage from "../image/동백꽃바라.png";
import { useNavigate } from "react-router-dom";
import Menu from "../components/Menu";
import DecorationImage from "../image/heart.png"; // 장식물 이미지
import Article from "../components/Article";
import NicknameModal from "../components/NicknameModal";
import AuthStore from "../store/AuthStore";

const MainPage = () => {
  const navigate = useNavigate();

  const [selectArticle, setSelectArticle] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);

  const userNickname = AuthStore((state) => state.user.userNickname);

  const decorations = [
    { id: 1, position: "top-[15%] left-[40%]" }, // 상단 중앙
    { id: 2, position: "top-[30%] left-[10%]" }, // 상단 왼쪽
    { id: 3, position: "top-[35%] left-[40%]" }, // 상단 오른쪽
    { id: 4, position: "top-[30%] left-[70%]" }, // 중단 왼쪽
    { id: 5, position: "top-[50%] left-[10%]" }, // 중단 오른쪽
    { id: 6, position: "top-[55%] left-[40%]" }, // 하단 왼쪽
    { id: 7, position: "top-[50%] left-[70%]" }, // 하단 오른쪽
    { id: 1, position: "top-[15%] left-[40%]" }, // 상단 중앙
    { id: 2, position: "top-[30%] left-[10%]" }, // 상단 왼쪽
    { id: 3, position: "top-[35%] left-[40%]" }, // 상단 오른쪽
    { id: 4, position: "top-[30%] left-[70%]" }, // 중단 왼쪽
    { id: 5, position: "top-[50%] left-[10%]" }, // 중단 오른쪽
    { id: 6, position: "top-[55%] left-[40%]" }, // 하단 왼쪽
  ];

  const decorationsPerPage = 8;
  const totalPages = Math.ceil(decorations.length / decorationsPerPage);
  const currentDecorations = decorations.slice(
    (currentPage - 1) * decorationsPerPage,
    currentPage * decorationsPerPage
  );

  const handleNextPage = () => {
    if (currentPage < totalPages) setCurrentPage(currentPage + 1);
  };

  const handlePreviousPage = () => {
    if (currentPage > 1) setCurrentPage(currentPage - 1);
  };

  return (
    <div className="relative flex flex-col items-center justify-center w-full max-w-[375px] min-h-screen bg-[#0d1a26] text-white overflow-hidden">
      <Menu />
      <NicknameModal />
      {/* 메인 화면 */}
      <div className="absolute top-4 left-4">
        <h1 className="text-3xl">{userNickname} 님의 복주머니</h1>
        <p className="text-lg">{decorations.length}개의 복이 왔어요.</p>
      </div>
      <h1 className="text-3xl font-bold mb-2">Lucky Bocky!</h1>
      <p className="text-lg mb-6">복 내놔라</p>

      <div className="relative">
        <img src={MainImage} alt="복주머니 이미지" className="w-48 h-48 mb-6" />
        {/* 장식물 배치 */}
        {currentDecorations.map((decoration) => (
          <button
            key={decoration.id}
            className={`absolute ${decoration.position}`}
            onClick={() => setSelectArticle(decoration.id)}
          >
            <img
              src={DecorationImage}
              alt="장식물"
              className="w-8 h-8 cursor-pointer"
            />
          </button>
        ))}
      </div>

      {/* 페이지네이션 */}
      <div className="flex justify-center mt-4 mb-2 w-full px-10 text-sm">
        <button
          onClick={handlePreviousPage}
          disabled={currentPage === 1}
          className="bg-gray-700 text-white px-4 py-2 rounded disabled:opacity-50"
        >
          이전
        </button>
        <p>
          {currentPage} / {totalPages}
        </p>
        <button
          onClick={handleNextPage}
          disabled={currentPage === totalPages}
          className="bg-gray-700 text-white px-4 py-2 rounded disabled:opacity-50"
        >
          다음
        </button>
      </div>

      <button
        onClick={() => navigate("/select-deco")}
        className="text-lg bg-white text-[#0d1a26] py-4 px-20 rounded-lg"
      >
        내 복주머니 공유하기
      </button>

      {/* 모달 */}
      {selectArticle && (
        <Article
          onClose={() => setSelectArticle(null)}
          content={`장식물 ${selectArticle}번의 메시지입니다.`}
        />
      )}
    </div>
  );
};

export default MainPage;
