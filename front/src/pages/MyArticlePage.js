import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Menu from "../components/Menu";
import { myArticle } from "../api/ArticleApi";

const MyArticlePage = () => {
  const navigate = useNavigate();

  const [articles, setArticles] = useState([]);

  const init = async () => {
    const result = await myArticle();
    setArticles(result);
  };

  useEffect(() => {
    init();
  }, []);

  // const messages = [
  //   { to: "파닥파닥", from: "ff", content: "ffffffffffffffffffffffff..." },
  //   { to: "고양이", from: "aa", content: "메시지 예시 2" },
  //   { to: "강아지", from: "bb", content: "메시지 예시 3" },
  //   { to: "파닥파닥", from: "ff", content: "ffffffffffffffffffffffff..." },
  //   { to: "고양이", from: "aa", content: "메시지 예시 2" },
  //   { to: "강아지", from: "bb", content: "메시지 예시 3" },
  // ];

  return (
    <div className="relative flex flex-col items-center w-full max-w-[375px] min-h-screen bg-[#0d1a26] text-white overflow-hidden">
      <Menu />
      {/* 제목 영역 */}
      <h1 className="text-2xl font-bold mb-4">메시지 목록</h1>

      {/* 메시지 리스트 */}
      <div className="w-full px-4 flex-1 space-y-4">
        {articles &&
          articles.map((article, index) => (
            <div
              key={index}
              className="bg-[#593c2c] rounded-lg p-4 shadow-md text-left"
            >
              <div className="font-bold mb-1">To. {article.to}</div>
              <div className="text-lg mb-2 whitespace-pre-wrap">
                {article.content}
              </div>
              <div className="text-right text-sm text-[#ccc]">
                From. {article.articleOwner}
              </div>
            </div>
          ))}
      </div>
      {/* 돌아가기 버튼 */}
      <button
        onClick={() => navigate(-1)}
        className="fixed bottom-0 w-full max-w-[375px] text-lg bg-white text-[#0d1a26] py-4 rounded-t-lg z-20"
      >
        돌아가기
      </button>
    </div>
  );
};

export default MyArticlePage;
