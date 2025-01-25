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
  const [articleSelector, setArticleSelector] = useState(false);

  const init = async () => {
    const result = await ArticleService.getMyList();
    setArticles(result.reverse());
  };

  const temp = () => {  // 공유 메시지 목록 작업 예정
    setArticles([]);
  }

  useEffect(() => {
    if(articleSelector === false) {
      init();
    } else {
      setArticles([{
        fortuneImg: 6,
        pocketOwner: "스트롱거",
        content: 
        `설을 핑계로 그대에게 연락을 드려봅니다.
        \n그대가 필요로 하는 올해의 소망은 무엇일까요?
        \n건강, 애정, 재물, 취업, 학업 그게 무엇이든 그대의 소망이 이루어지면 좋겠습니다.
        \n새해 복 많이 받으세요.`,
        articleOwner: "암스트롱"
      }]);
    }
  }, [articleSelector]);

  if (!user.createdAt) {
    return <Navigate to="/" replace />;
  }

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
      <div className="w-full space-y-8 pb-16 mt-10">
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
            {/* {article.comment && (
              <div>
                <hr className="border-t-2 border-[gray] mt-1 mb-2" />
                <div className="text-xl mb-1">Re.</div>
                <div className="whitespace-pre-wrap break-words">
                  {article.comment}
                </div>
              </div>
            )} */}
          </div>
        ))}
      </div>

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
