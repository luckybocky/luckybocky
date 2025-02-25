import React, { useState, useEffect } from "react";
import { useNavigate, Navigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import ArticleService from "../api/ArticleService.ts";
import ShareArticleService from "../api/ShareArticleService.ts";

import Alarm from "../components/Alarm.js";
import fortuneImages from "../components/FortuneImages";
import Menu from "../components/Menu";

const MyArticlePage = () => {
  const navigate = useNavigate();
  const user = AuthStore((state) => state.user);
  const [articles, setArticles] = useState([]);
  const [shareArticles, setShareArticles] = useState([]);
  const [articleSelector, setArticleSelector] = useState(false);
  const [loading, setLoading] = useState(true);
  const [copied, setCopied] = useState(false);

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
      setShareArticles(response.reverse());
    } catch (error) {
      console.error("Failed to fetch shared articles:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleCopyURL = async (currentURL) => {
    const domain = new URL(window.location.href).origin;
    const copyWrite = `${user?.userNickname} 님이 행운의 새해 인사를 보냈어요! 💌\n지금 바로 읽어보세요. \n\n${domain}/share/`;

    try {
      if (navigator.clipboard && navigator.clipboard.writeText) {
        // 클립보드 API가 지원되는 경우
        await navigator.clipboard.writeText(copyWrite + currentURL);
        setCopied(true);
      } else {
        // 클립보드 API가 지원되지 않는 경우 대체 방법
        const textArea = document.createElement("textarea");
        textArea.value = copyWrite + currentURL;
        document.body.appendChild(textArea);
        textArea.select();
        document.execCommand("copy");
        document.body.removeChild(textArea);
        setCopied(true);
      }
    } catch (error) {
      console.error("URL 복사 실패:", error);
      alert("URL 복사에 실패했습니다. 브라우저 설정을 확인해주세요.");
    }

    if (navigator.share) {
      navigator
        .share({
          text: copyWrite + currentURL,
        })
        .catch((error) => console.error("공유 실패:", error));
    } else {
      alert("공유 기능이 이 브라우저에서 지원되지 않습니다.");
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
              onClick={
                !articleSelector
                  ? () => {
                      navigate(`/${article.pocketAddress}`);
                    }
                  : () => handleCopyURL(article.shareArticleAddress)
              }
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
                  className="absolute top-[-45px] left-3/4 transform -translate-x-1/2 w-[80px] h-[80px]"
                />
              </picture>
              <div
                className={`text-base mb-1 ${
                  !articleSelector ? "" : "text-blue-400"
                }`}
              >
                {!articleSelector
                  ? `To. ${article.pocketOwner}`
                  : `${article.shareCount} 명이 저장했어요!`}
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

      <Alarm
        message={"URL 복사 완료! \n친구들에게 새해 인사를 공유해보세요."}
        visible={copied}
        onClose={() => setCopied(false)}
        backgroundColor={"bg-green-500"}
      />

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
