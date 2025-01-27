import React, { useState, useEffect } from "react";
import { useNavigate, Navigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import ArticleService from "../api/ArticleService.ts";
import ShareArticleService from "../api/ShareArticleService.ts";

import fortuneImages from "../components/FortuneImages";
import Menu from "../components/Menu";

const MyArticlePage = () => {
  const navigate = useNavigate();
  const user = AuthStore((state) => state.user);
  const [articles, setArticles] = useState([]);
  const [shareArticles, setShareArticles] = useState([]);
  const [articleSelector, setArticleSelector] = useState(false);
  const [loading, setLoading] = useState(true);

  const init = async () => {
    setLoading(true);
    try {
      const result = await ArticleService.getMyList();
      setArticles(result.reverse());
    } catch (error) {
      console.error("Failed to fetch articles:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchShareArticles = async () => {
    setLoading(true);
    try {
      const response = await ShareArticleService.getShareMyList();
      setShareArticles(response);
    } catch (error) {
      console.error("Failed to fetch shared articles:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    init();
    fetchShareArticles();
  }, []);

  if (!user.createdAt) {
    return <Navigate to="/" replace />;
  }

  const currentArticles = articleSelector ? shareArticles : articles;

  return (
    <div className="relative flex flex-col items-center w-full max-w-[600px] bg-[#ba947f] p-4 overflow-hidden">
      <Menu />
      {/* 제목 영역 */}
      <h1 className="text-xl mt-2 mb-2">메시지 목록</h1>
      {/* 상단 메뉴 영역 */}
      <div className="w-full grid grid-cols-1 px-1 space-x-4 mt-3 mb-3 text-sm">
        <div className="flex items-center">
          <label className="relative inline-flex items-center cursor-pointer">
            <input
              type="checkbox"
              checked={articleSelector}
              onChange={() => setArticleSelector(!articleSelector)}
              className="sr-only peer"
            />
            <div
              className="w-12 h-6 bg-gray-300 rounded-full peer peer-checked:bg-[#593C2C] 
             transition-colors duration-300 outline outline-2 outline-transparent focus:outline-[#CEAB93]"
            ></div>
            <span
              className="absolute left-1 top-1 w-4 h-4 bg-white rounded-full shadow peer-checked:translate-x-6 
                 transition-transform duration-300 focus:outline-none"
            ></span>
          </label>
          <span className="ml-4">
            {!articleSelector ? "내가 쓴 메시지" : "내가 공유한 메시지"}
          </span>
        </div>
      </div>
      {/* 메시지 리스트 */}
      {loading ? (
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
          Loding...
        </div>
      ) : (
        <div className="w-full space-y-8 pb-16 mt-10">
          {currentArticles?.map((article, index) => (
            <div
              key={index}
              className="relative bg-[#593c2c] text-left border-2 border-[gray] shadow-md rounded-lg p-4"
            >
              {/* 이미지 추가 */}
              <picture>
                <source
                  srcSet={
                    fortuneImages[
                      !articleSelector
                        ? article.fortuneImg
                        : article.fortuneSeq ?? article.fortuneImg // fallback 처리
                    ]?.src || "/path/to/default-image.webp"
                  }
                  type="image/webp"
                />
                <img
                  src={
                    fortuneImages[
                      !articleSelector
                        ? article.fortuneImg
                        : article.fortuneSeq ?? article.fortuneImg // fallback 처리
                    ]?.fallback || "/path/to/default-image.png"
                  }
                  alt="Fortune"
                  className="absolute top-[-45px] left-3/4 transform -translate-x-1/2 w-[100px] h-[100px]"
                />
              </picture>
              <div className="text-lg mb-1">
                To. {!articleSelector ? article.pocketOwner : ""}
              </div>
              <div className="text-xs whitespace-pre-wrap break-words mb-2 ">
                {!articleSelector
                  ? article.content
                  : article.shareArticleContent}
              </div>
              <div className="text-sm text-right text-[#ccc]">
                From.{" "}
                {!articleSelector ? article.articleOwner : article.userNickname}
              </div>
            </div>
          ))}
        </div>
      )}

      {/* 돌아가기 버튼 */}
      <button
        onClick={() => navigate(window.sessionStorage.getItem("pocketAddress"))}
        className="fixed bottom-0 w-full max-w-[600px] bg-white text-[#3c1e1e] rounded-t-lg pt-5 pb-4"
      >
        돌아가기
      </button>
    </div>
  );
};

export default MyArticlePage;
