import React, { useEffect, useState } from "react";
import MainImage from "../image/pocket.png";
import { useNavigate } from "react-router-dom";
import Menu from "../components/Menu";
import Article from "../components/Article";
import AuthStore from "../store/AuthStore";
import Footer from "../components/Footer";
import { useParams } from "react-router-dom";
import FortuneImageBasic from "../image/fortunes/푸른뱀.png";
import FortuneImageHealth from "../image/fortunes/건강뱀.png";
import FortuneImageLove from "../image/fortunes/애정뱀.png";
import FortuneImageWealth from "../image/fortunes/재물뱀.png";
import FortuneImageJob from "../image/fortunes/취업뱀.png";
import FortuneImageEdu from "../image/fortunes/학업뱀.png";
import { loadPocket } from "../api/PocketApi";

import { requestFcmToken } from "../api/FireBaseApi"; //12-31 창희 추가, 파이어베이스 api들고오기

const MainPage = () => {
  const images = [
    FortuneImageBasic,
    FortuneImageHealth,
    FortuneImageLove,
    FortuneImageWealth,
    FortuneImageJob,
    FortuneImageEdu,
  ];
  const navigate = useNavigate();

  const { address } = useParams();

  const [selectArticle, setSelectArticle] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [copied, setCopied] = useState(false); // URL 복사 알림 상태
  const [pocket, setPocket] = useState(null);
  const [decorations, setDecorations] = useState([]);

  const userNickname = AuthStore((state) => state.user.userNickname);
  const myAddress = AuthStore((state) => state.user.address);
  const fortuneVisibility = AuthStore((state) => state.user.fortuneVisibility);

  const positions = [
    { id: 1, position: "top-[40%] left-[5%]" }, // 상단 왼쪽
    { id: 2, position: "top-[45%] left-[36%]" }, // 상단 오른쪽 -> 가운데
    { id: 3, position: "top-[40%] left-[67%]" }, // 중단 왼쪽 -> 상단 오른쪽
    { id: 4, position: "top-[60%] left-[5%]" }, // 중단 오른쪽 -> 하단 왼쪽
    { id: 5, position: "top-[65%] left-[36%]" }, // 하단 왼쪽 -> 가운데
    { id: 6, position: "top-[60%] left-[67%]" }, // 하단 오른쪽
  ];

  useEffect(() => {
    const fetchPocket = async () => {
      try {
        const data = await loadPocket(address);
        setPocket(data);

        const articlesArray = data.articles || [];

        const updatedPocket = articlesArray.map((decoration, idx) => {
          const decorationIdx = idx % 6; // 순환 인덱스
          return {
            id: decoration.articleSeq,
            position: positions[decorationIdx].position,
            image: parseInt(decoration.fortuneImgUrl),
          };
        });

        setDecorations(updatedPocket); // 위치가 할당된 데이터 저장
      } catch (error) {
        console.error("Error loading pocket:", error);
      }
    };

    fetchPocket();
  }, [address]);

  const decorationsPerPage = 6;
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

  const handleCopyURL = async () => {
    try {
      const currentURL = window.location.href; // 현재 URL 가져오기

      if (navigator.clipboard && navigator.clipboard.writeText) {
        // 클립보드 API가 지원되는 경우
        await navigator.clipboard.writeText(currentURL);
        setCopied(true);
        setTimeout(() => setCopied(false), 2000);
      } else {
        // 클립보드 API가 지원되지 않는 경우 대체 방법
        const textArea = document.createElement("textarea");
        textArea.value = currentURL;
        document.body.appendChild(textArea);
        textArea.select();
        document.execCommand("copy");
        document.body.removeChild(textArea);
        setCopied(true);
        setTimeout(() => setCopied(false), 2000);
      }
    } catch (error) {
      console.error("URL 복사 실패:", error);
      alert("URL 복사에 실패했습니다. 브라우저 설정을 확인해주세요.");
    }
  };

  //=====12-31 창희 추가 start=====
  //main페이지에 들어오면 로그인이 성공했다고 판단하기에, 푸시를 위한 파이어베이스키 업데이트

  useEffect(() => {
    // console.log("12-31 창희 추가");
    requestFcmToken();
  }, []);
  //=====12-31 창희 추가 end=====

  return (
    <div className="relative flex flex-col items-center justify-center w-full max-w-[375px] min-h-screen bg-[#0d1a26] text-white overflow-hidden">
      <Menu />
      {/* 메인 화면 */}
      <div className="absolute top-4 left-4">
        <h1 className="text-3xl mb-1">{userNickname} 님의 복주머니</h1>
        <p className="text-xl">{decorations.length}개의 복이 왔어요.</p>
      </div>
      <h1 className="text-5xl font-bold mb-3">Lucky Bocky!</h1>
      <p className="text-3xl mb-6">복 내놔라</p>

      <div className="relative">
        <img src={MainImage} alt="복주머니 이미지" className="w-60 h-60 mb-6" />
        {/* 장식물 배치 */}
        {currentDecorations.map((decoration) => (
          <button
            key={decoration.id}
            className={`absolute ${decoration.position}`}
            onClick={() => setSelectArticle(decoration.id)}
          >
            <img
              src={images[decoration.image]}
              alt="장식물"
              className="w-16 h-16 cursor-pointer"
            />
          </button>
        ))}
      </div>

      {/* 페이지네이션 */}
      <div className="flex justify-center mb-2 w-full px-10 text-lg">
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
        onClick={
          address === myAddress
            ? handleCopyURL
            : () =>
                navigate("/select-deco", {
                  state: {
                    address,
                    fortuneVisibility,
                    pocketSeq: pocket.pocketSeq,
                  },
                })
        }
        className="bg-white text-[#0d1a26] py-4 px-20 rounded-lg"
      >
        {address === myAddress ? "내 복주머니 공유하기" : "복 전달하기"}
      </button>

      {/* 복사 성공 알림 */}
      {copied && (
        <div className="fixed bottom-16 bg-green-500 text-white py-2 px-4 rounded-lg shadow-md">
          URL이 복사되었습니다!
        </div>
      )}

      {/* 모달 */}
      {selectArticle && (
        <Article
          onClose={() => setSelectArticle(null)}
          content={`장식물 ${selectArticle}번의 메시지입니다.`}
          articleSeq={selectArticle}
        />
      )}

      <Footer />
    </div>
  );
};

export default MainPage;
