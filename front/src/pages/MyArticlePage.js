import React, { useState, useEffect } from "react";
import { useNavigate, Navigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import ArticleService from "../api/ArticleService.ts";

import fortuneImages from "../components/FortuneImages";
import Menu from "../components/Menu";

const MyArticlePage = () => {
  const navigate = useNavigate();

  const user = AuthStore((state) => state.user);

  const [articles, setArticles] = useState([]);

  const init = async () => {
    const result = await ArticleService.getMyList();
    setArticles(result.reverse());
  };

  useEffect(() => {
    init();
  }, []);

  if (!user.createdAt) {
    return <Navigate to="/" replace />;
  }

  return (
    <div className="relative flex flex-col items-center w-full max-w-[600px] bg-[#ba947f] overflow-hidden p-2">
      <Menu />

      <h1 className="text-2xl mb-4">메시지 목록</h1>

      {/* 메시지 리스트 */}
      <div className="w-full px-4 space-y-8 pb-16 mt-8">
        {articles?.map((article, index) => (
          <div
            key={index}
            className="relative bg-[#593c2c] text-left border-2 border-[gray] shadow-md rounded-lg p-4"
          >
            {/* 이미지 추가 */}
            <picture>
              <source
                srcSet={fortuneImages[article.fortuneImg].src}
                type="image/webp"
              />
              <img
                src={fortuneImages[article.fortuneImg].fallback}
                alt="Fortune"
                className="absolute top-[-45px] left-3/4 transform -translate-x-1/2 w-[100px] h-[100px]"
              />
            </picture>
            <div className="text-xl mb-1">To. {article.pocketOwner}</div>
            <div className="whitespace-pre-wrap break-words mb-2 ">
              {article.content}
            </div>
            <div className="text-base text-right text-[#ccc]">
              From. {article.articleOwner}
            </div>
            {article.comment && (
              <div>
                <hr className="border-t-2 border-[gray] mt-1 mb-2" />
                <div className="text-xl mb-1">Re.</div>
                <div className="whitespace-pre-wrap break-words">
                  {article.comment}
                </div>
              </div>
            )}
          </div>
        ))}
      </div>

      {/* 돌아가기 버튼 */}
      <button
        onClick={() => navigate(-1)}
        className="fixed bottom-0 w-full max-w-[600px] bg-white text-[#3c1e1e] rounded-t-lg pt-5 pb-4"
      >
        돌아가기
      </button>
    </div>
  );
};

export default MyArticlePage;
