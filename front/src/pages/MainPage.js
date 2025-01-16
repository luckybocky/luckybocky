import React, { useEffect, useState, lazy, Suspense } from "react";
import MainImage from "../image/pocket.png";
import MainImageW from "../image/pocket.webp";
import { useNavigate } from "react-router-dom";
import Menu from "../components/Menu";
import Article from "../components/Article";
import AuthStore from "../store/AuthStore";
import Footer from "../components/Footer";
import { useParams } from "react-router-dom";
import fortuneImages from "../components/FortuneImages";
import PocketService from "../api/PocketService.ts";

const IoShareOutline = lazy(() =>
  import("react-icons/io5").then((mod) => ({ default: mod.IoShareOutline }))
);
const BsPencil = lazy(() =>
  import("react-icons/bs").then((mod) => ({ default: mod.BsPencil }))
);

const MainPage = () => {
  const navigate = useNavigate();

  const { address } = useParams();

  const [selectArticle, setSelectArticle] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [copied, setCopied] = useState(false); // URL 복사 알림 상태
  const [pocket, setPocket] = useState(null);
  const [decorations, setDecorations] = useState([]);
  const [isOwner, setIsOwner] = useState(false);
  const [isLoaded, setIsLoaded] = useState(false); // 로드 상태

  const myAddress = AuthStore((state) => state.user.address);
  // const fortuneVisibility = AuthStore((state) => state.user.fortuneVisibility);

  const positions = [
    { id: 1, position: "top-[25%] left-[0%]" }, // 상단 왼쪽
    { id: 2, position: "top-[30%] left-[32%]" }, // 상단 오른쪽 -> 가운데
    { id: 3, position: "top-[25%] left-[64%]" }, // 중단 왼쪽 -> 상단 오른쪽
    { id: 4, position: "top-[55%] left-[0%]" }, // 중단 오른쪽 -> 하단 왼쪽
    { id: 5, position: "top-[60%] left-[32%]" }, // 하단 왼쪽 -> 가운데
    { id: 6, position: "top-[55%] left-[64%]" }, // 하단 오른쪽
  ];

  const fetchPocket = async () => {
    try {
      const data = await PocketService.getByAddress(address);
      setPocket(data);

      const articlesArray = data.articles || [];

      const updatedPocket = articlesArray.map((decoration, idx) => {
        const decorationIdx = idx % 6; // 순환 인덱스
        return {
          id: decoration.articleSeq,
          position: positions[decorationIdx].position,
          image: decoration.fortuneImg,
        };
      });

      setDecorations(updatedPocket); // 위치가 할당된 데이터 저장
    } catch (error) {
      navigate("/error");
    } finally {
      setIsLoaded(true);
    }
  };

  useEffect(() => {
    fetchPocket();
    setIsOwner(address === myAddress);
  }, [address]);

  const decorationsPerPage = 6;
  const totalPages = Math.max(
    Math.ceil(decorations.length / decorationsPerPage),
    1
  );
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

  return (
    isLoaded && (
      <div className="relative flex flex-col items-center justify-center p-2 w-full max-w-[600px] min-h-screen bg-[#ba947f] overflow-hidden">
        <Menu />
        {/* 메인 화면 */}
        <div className="absolute top-4 left-4">
          <h1 className="text-2xl mb-1">
            <span className="text-[pink]">{pocket?.userNickname}</span> 님의
            복주머니
          </h1>
          <p>{decorations.length}개의 복이 왔어요.</p>
        </div>
        {/* <h1 className="text-4xl mb-3">Lucky Bocky!</h1>
      <p className="text-xl mb-6">복 내놔라</p> */}

        <div className="relative">
          <picture>
            <source srcSet={MainImageW} type="image/webp" />
            <img
              src={MainImage}
              alt="복주머니 이미지"
              className="w-80 h-80 mb-6"
            />
          </picture>
          {/* 장식물 배치 */}
          {currentDecorations.map((decoration) => (
            <button
              key={decoration.id}
              className={`absolute ${decoration.position}`}
              onClick={() => setSelectArticle(decoration.id)}
            >
              <picture>
                <source
                  srcSet={fortuneImages[decoration.image].src}
                  type="image/webp"
                />
                <img
                  src={fortuneImages[decoration.image].fallback}
                  alt="장식물"
                  className="w-28 h-28 cursor-pointer"
                />
              </picture>
            </button>
          ))}
        </div>

        {/* 페이지네이션 */}
        <div className="flex justify-center mb-2 w-full px-10">
          <button
            onClick={handlePreviousPage}
            disabled={currentPage === 1}
            className="px-2 rounded disabled:opacity-50"
          >
            {"<<"}
          </button>
          <p>
            {currentPage} / {totalPages}
          </p>
          <button
            onClick={handleNextPage}
            disabled={currentPage === totalPages}
            className="px-2 rounded disabled:opacity-50"
          >
            {">>"}
          </button>
        </div>

        <button
          onClick={
            isOwner
              ? handleCopyURL
              : () =>
                  navigate("/select-deco", {
                    state: {
                      address,
                      // fortuneVisibility,
                      pocketSeq: pocket.pocketSeq,
                    },
                  })
          }
          className={`${
            isOwner ? "bg-white text-[#0d1a26] pt-3 pb-4" : "bg-blue-500 py-4"
          }   px-20 rounded-lg w-full max-w-[375px]`}
        >
          <div className="flex items-center justify-center">
            <Suspense>
              {isOwner ? (
                <IoShareOutline size={28} className="mr-2" />
              ) : (
                <BsPencil size={22} className="mr-3" />
              )}
            </Suspense>
            <span className={`${isOwner ? "pt-2" : "pt-1"}`}>
              {isOwner ? "내 복주머니 공유하기" : "복 전달하기"}
            </span>
          </div>
        </button>

        {/* 복사 성공 알림 */}
        {copied && (
          <div className="fixed bottom-16 bg-green-500 text-white py-2 px-4 rounded-lg shadow-md">
            URL 복사 완료!
          </div>
        )}

        {/* 모달 */}
        {selectArticle && (
          <Article
            onClose={() => setSelectArticle(null)}
            articleSeq={selectArticle}
            onDelete={fetchPocket}
            myAddress={myAddress}
            address={address}
          />
        )}

        <Footer />
      </div>
    )
  );
};

export default MainPage;
