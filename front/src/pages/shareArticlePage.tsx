import React, { useState, useEffect, Suspense } from "react";
import { useParams, useNavigate } from "react-router-dom";

import AuthStore from "../store/AuthStore";
import ShareArticleService from "../api/ShareArticleService.ts";
import { ShareArticle } from "../api/ShareArticleService.ts"

import { ReactComponent as PocketIcon } from "../image/pocketIcon.svg"

import fortuneImages from "../components/FortuneImages.js";
import Util from "../components/Util.js";

const IoLogInOutline = Util.loadIcon("IoLogInOutline").io5;
const IoShareOutline = Util.loadIcon("IoShareOutline").io5;
const BsPencil = Util.loadIcon("BsPencil").bs;

const ShareArticlePage = () => {
    const navigate = useNavigate();

    const myAddress = AuthStore((state) => state.user.address);

    const [detail, setDetail] = useState<ShareArticle | undefined>();
    const [isOwner, setIsOwner] = useState(false);
    const [copied, setCopied] = useState(false); // URL 복사 알림 상태
    const [isLogin, setIsLogin] = useState<boolean | undefined>();
    const [isLoaded, setIsLoaded] = useState<boolean | undefined>(false); // 로드 상태
    const [notice, setNotice] = useState(false);

    const { id } = useParams();

    const fetchShareArticle = async () => {
        try {
            if (id) {
                const result = await ShareArticleService.getByShareArticleAddress(id);

                window.sessionStorage.setItem("pocketAddress", "/" + result?.shareArticleDto.pocketAddress);
                if (result?.shareArticleDto.pocketAddress === myAddress)
                    setIsOwner(true);

                setDetail(result?.shareArticleDto);
                setIsLogin(result?.login);
            }
        }
        catch (error) {
            navigate("/error");
        } finally {
            setIsLoaded(true);
        }
    }

    const handleCopyURL = async () => {
        const copyWrite = `${detail?.userNickname} 님이 행운의 새해 인사를 보냈어요! 💌\n지금 바로 읽어보세요. \n\n`
        const currentURL = window.location.href; // 현재 URL 가져오기

        try {
            if (navigator.clipboard && navigator.clipboard.writeText) {
                // 클립보드 API가 지원되는 경우
                await navigator.clipboard.writeText(copyWrite + currentURL);
                setCopied(true);
                setTimeout(() => setCopied(false), 2000);
            } else {
                // 클립보드 API가 지원되지 않는 경우 대체 방법
                const textArea = document.createElement("textarea");
                textArea.value = copyWrite + currentURL;
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

        if (navigator.share) {
            navigator.share({
                text: copyWrite + currentURL,
            })
                .catch((error) => console.error('공유 실패:', error));
        } else {
            alert('공유 기능이 이 브라우저에서 지원되지 않습니다.');
        }
    };

    useEffect(() => {
        window.sessionStorage.setItem("share", window.location.pathname);

        fetchShareArticle();
    }, []);

    useEffect(() => {
        if (isLogin && !isOwner) {
            setNotice(true);
            setTimeout(() => setNotice(false), 2000);
        }
    }, [isLoaded])

    return (
        isLoaded && (
            <div className="flex flex-col justify-center items-center w-full max-w-[600px] bg-[#f5f5f5] text-[#3c1e1e] py-6 px-4" >
                <h1 className="text-2xl text-center mb-24" ><span className="text-blue-400">{detail?.userNickname}</span> 님의 메시지</h1>

                < div className="relative w-full border rounded-md bg-white p-2 pt-4 mb-4" >
                    <picture>
                        <source srcSet={fortuneImages[detail?.fortuneSeq ?? 0].src} type="image/webp" />
                        <img
                            src={fortuneImages[detail?.fortuneSeq ?? 0].fallback}
                            alt="Fortune"
                            className="absolute top-[-80px] left-1/2 transform -translate-x-1/2 w-[100px] h-[100px]"
                        />
                    </picture>
                    <div className="h-72 whitespace-pre-wrap break-words overflow-y-auto">
                        {detail?.shareArticleContent}
                    </div>
                </div>

                {!isLogin &&
                    <button className="bg-[#F4BB44] w-full max-w-[350px] rounded-lg py-4 px-5"
                        onClick={() => navigate("/")}>
                        <div className="flex items-center justify-center">
                            <Suspense>
                                <IoLogInOutline size={28} className="mr-1.5" />
                            </Suspense>
                            <span className="mt-1">
                                로그인하고 복주머니에 담기
                            </span>
                        </div>
                    </button>}

                {/* 로그인 상태 */}
                {isLogin &&
                    <div className="flex flex-col text-[#0d1a26] gap-2 w-full max-w-[350px] ">
                        <button
                            onClick={
                                isOwner
                                    ? handleCopyURL
                                    : () =>
                                        navigate(`/${detail?.pocketAddress}`)
                            }
                            className={`${isOwner ? "bg-[#156082] pt-3 pb-4" : "bg-green-600 py-4"
                                } w-full px-5 rounded-lg`}
                        >
                            <div className="flex items-center justify-center">
                                <Suspense>
                                    {isOwner ? (
                                        <IoShareOutline size={28} className="mr-2 text-white" />
                                    ) : (
                                        <BsPencil size={22} className="mr-3 text-white" />
                                    )}
                                </Suspense>
                                <span className={`${isOwner ? "pt-2" : "pt-1"} text-white`}>
                                    {isOwner ? "새해 인사 공유하기" : "답장 남기러 가기"}
                                </span>
                            </div>
                        </button>

                        <button
                            onClick={() =>
                                navigate(`/${myAddress}`)}
                            className={"bg-gray-400 py-4 w-full px-5 rounded-lg"}
                        >
                            <div className="flex items-center justify-center">
                                <PocketIcon width={26} height={26} fill="#0d1a26" className="mb-1 mr-1.5" />
                                <span className="pt-1">
                                    {isOwner ? "내 복주머니로 돌아가기" : "내 복주머니 보러 가기"}
                                </span>
                            </div>
                        </button>
                    </div>
                }

                {/* 복사 성공 알림 */}
                {copied && (
                    <div className="fixed bottom-16 bg-green-500 text-white text-center py-2 px-4 rounded-lg shadow-md">
                        URL 복사 완료!<br />
                        친구들에게 새해 인사를 공유해보세요.
                    </div>
                )}

                {/* 복주머니 저장 알림 */}
                {notice && (
                    <div className="fixed bottom-16 bg-green-500 text-white text-center py-2 px-4 rounded-lg shadow-md">
                        메세지가 복주머니에 저장되었어요.
                    </div>
                )}
            </div >)
    );
};

export default ShareArticlePage;
