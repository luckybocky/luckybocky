import React, { useEffect, useState, Suspense } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useParams } from "react-router-dom";

import AuthStore from "../store/AuthStore";

import PocketService from "../api/PocketService.ts";

import bgImageP from "../image/bgImage.png";
import bgImageW from "../image/bgImage.webp";
import mainImageP from "../image/pocket.png";
import mainImageW from "../image/pocket.webp";
import pocketIconImageP from "../image/pocketIcon.png"
import pocketIconImageW from "../image/pocketIcon.webp"

import fortuneImages from "../components/FortuneImages";
import Menu from "../components/Menu";
import Article from "../components/Article";
import Footer from "../components/Footer";
import Util from "../components/Util.js";

const IoShareOutline = Util.loadIcon("IoShareOutline").io5;
const BsPencil = Util.loadIcon("BsPencil").bs;
const IoLogInOutline = Util.loadIcon("IoLogInOutline").io5;

const MainPage = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const myAddress = AuthStore((state) => state.user.address);

  const [selectArticle, setSelectArticle] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [copied, setCopied] = useState(false); // URL 복사 알림 상태
  const [pocket, setPocket] = useState(null);
  const [decorations, setDecorations] = useState([]);
  const [isOwner, setIsOwner] = useState(false);
  const [isLoaded, setIsLoaded] = useState(false); // 로드 상태
  const [notice, setNotice] = useState(false);

  const afterWrite = location.state?.afterWrite;
  const { address } = useParams();
  const positions = [
    { id: 1, position: "top-[25%] left-[8%]" }, // 상단 왼쪽
    { id: 2, position: "top-[35%] left-[38%]" }, // 상단 오른쪽 -> 가운데
    { id: 3, position: "top-[25%] left-[68%]" }, // 중단 왼쪽 -> 상단 오른쪽
    { id: 4, position: "top-[55%] left-[8%]" }, // 중단 오른쪽 -> 하단 왼쪽
    { id: 5, position: "top-[65%] left-[38%]" }, // 하단 왼쪽 -> 가운데
    { id: 6, position: "top-[55%] left-[68%]" }, // 하단 오른쪽
  ];
  const decorationsPerPage = 6;
  const totalPages = Math.max(
    Math.ceil(decorations.length / decorationsPerPage),
    1
  );
  const currentDecorations = decorations.slice(
    (currentPage - 1) * decorationsPerPage,
    currentPage * decorationsPerPage
  );

  const fetchPocket = async () => {
    window.sessionStorage.setItem("pocketAddress", window.location.pathname);
    try {
      const data = await PocketService.getByAddress(address);
      setPocket(data);

      const articlesArray = data.articles.reverse() || [];

      const updatedPocket = articlesArray.map((decoration, idx) => {
        const decorationIdx = idx % 6; // 순환 인덱스
        return {
          id: decoration.articleSeq,
          position: positions[decorationIdx].position,
          image: decoration.fortuneImg,
          userNickname: decoration.userNickname,
        };
      });

      setDecorations(updatedPocket); // 위치가 할당된 데이터 저장
    } catch (error) {
      navigate("/error");
    } finally {
      setIsLoaded(true);
    }
  };

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

  useEffect(() => {
    fetchPocket();
    setIsOwner(address === myAddress);
  }, [address]);

  useEffect(() => {
    if (afterWrite) {
      setNotice(true);
      setTimeout(() => setNotice(false), 3000);
    }
  }, [])

  return (
    isLoaded && (
      <div className="relative flex flex-col items-center justify-center w-full max-w-[600px] p-4 overflow-hidden z-20">
        <picture>
          <div className="absolute inset-0 bg-gradient-to-b from-black/15 to-transparent -z-10"></div>
          <source srcSet={bgImageW} type="image/webp" />
          <img
            src={bgImageP}
            alt="Background"
            className="absolute inset-0 w-full h-full object-fill -z-20"
          />
        </picture>

        <Menu />
        {/* 메인 화면 */}
        <div className="absolute top-0 left-0 p-4">
          <h1 className="text-2xl xs:text-xl mb-1">
            <span className="text-[pink] ">{pocket?.userNickname}</span> 님의
            복주머니
          </h1>
          <picture className="flex">
            <source srcSet={pocketIconImageW} type="image/webp" />
            <img
              src={pocketIconImageP}
              alt="복주머니 아이콘 이미지"
              className="w-5 h-5 xs:w-4 xs:h-4 mr-1"
            />
            <span className="text-base xs:text-sm">{decorations.length}개의 복이 왔어요!</span>
          </picture>
        </div>

        <div className="relative">
          <picture>
            <source srcSet={mainImageW} type="image/webp" />
            <img
              src={mainImageP}
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
              <p className="absolute w-full top-[-10px] text-xs text-shadow-outline">
                {decoration.userNickname}
              </p>
              <picture>
                <source
                  srcSet={fortuneImages[decoration.image].src}
                  type="image/webp"
                />
                <img
                  src={fortuneImages[decoration.image].fallback}
                  alt="장식물"
                  className="w-20 h-20 cursor-pointer"
                />
              </picture>
            </button>
          ))}
        </div>

        {/* 페이지네이션 */}
        <div className="flex justify-center w-full mb-2 px-10">
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

        {/* 비로그인 상태 */}
        {myAddress === "" &&
          <div className="flex justify-center items-center gap-2 w-full">
            <button className="bg-[#F4BB44] py-4 px-5 rounded-lg"
              onClick={() => navigate("/")}>
              <div className="flex items-center justify-center">
                <Suspense>
                  <IoLogInOutline size={28} className="mr-1.5" />
                </Suspense>
                <span className="w-[95px] text-shadow-outline">
                  로그인하기
                </span>
              </div>
            </button>

            <button
              onClick={() =>
                navigate("/select-deco", {
                  state: {
                    address,
                    pocketSeq: pocket.pocketSeq,
                  },
                })
              }
              className={"bg-[#156082] py-4 px-5 rounded-lg"}
            >
              <div className="flex items-center justify-center">
                <Suspense>
                  <BsPencil size={22} className="mr-3" />
                </Suspense>
                <span className={"pt-1 w-[95px]"}>
                  복 전달하기
                </span>
              </div>
            </button>
          </div>
        }


        {/* 로그인 상태 */}
        {myAddress !== "" &&
          <div className="flex justify-center items-center gap-2 w-full">
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
              className={`${isOwner ? "bg-white text-[#0d1a26] pt-3 pb-4" : "bg-[#156082] py-4"
                } w-full max-w-[350px] px-5 rounded-lg`}
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
          </div>
        }

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

        {/* 비회원 글쓰기 후 알림 */}
        {notice && (
          <div className="fixed bottom-50 bg-green-500 bg-opacity-70 py-2 px-4 rounded-lg shadow-md left-1/2 transform -translate-x-1/2">
            <p className="whitespace-nowrap">로그인 후 나의 복주머니도 공유해보세요!</p>
          </div>
        )}

        <Footer />
      </div>
    )
  );
};

export default MainPage;
