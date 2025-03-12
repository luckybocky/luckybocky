import React, { useState, useEffect, Suspense } from "react";
import { useNavigate } from "react-router-dom";

import ArticleService from "../api/ArticleService.ts";

import Alarm from "./Alarm.js";
import fortuneImages from "./FortuneImages";
import Util from "./Util";

const AiOutlineMail = Util.loadIcon("AiOutlineMail").ai;
const AiOutlineAlert = Util.loadIcon("AiOutlineAlert").ai;
const AiOutlineDelete = Util.loadIcon("AiOutlineDelete").ai;
const AiOutlineClose = Util.loadIcon("AiOutlineClose").ai;
const AiOutlineLock = Util.loadIcon("AiOutlineLock").ai;

const Article = ({ onClose, articleSeq, onDelete, myAddress, address }) => {
  const navigate = useNavigate();

  const [reportModalOpen, setReportModalOpen] = useState(false);
  const [report, setReport] = useState("");
  const [reportType, setReportType] = useState(0);
  const [detail, setDetail] = useState({
    articleVisibility: false,
    articleSeq: 0,
    userKey: "",
    userNickname: "",
    pocketAddress: "",
    articleContent: "",
    articleComment: "",
    fortuneName: "",
    fortuneImg: 0,
    createdAt: "",
  });
  const [deleteModalOpen, setDeleteModalOpen] = useState(false);
  const [commentModalOpen, setCommentModalOpen] = useState(false);
  const [reported, setReported] = useState(false); // 신고 알림 상태
  const [isLoaded, setIsLoaded] = useState(false); // 로드 상태
  const [isSubmitting, setIsSubmitting] = useState(false);

  const confirmDelete = async () => {
    if (isSubmitting) return;
    setIsSubmitting(true);

    await ArticleService.deleteBySeq(articleSeq);
    onClose();
    onDelete();
    setIsSubmitting(false);
  };

  const confirmComment = async () => {
    //상대 복주머니로 가는 로직
    navigate(`/${detail.pocketAddress}`);
    setCommentModalOpen(false);
    onClose();
  };

  const fetchArticle = async () => {
    const result = await ArticleService.getBySeq(articleSeq);
    setDetail(result);
    setIsLoaded(true);
  };

  const sendReport = () => {
    if (reportType === 0) {
      alert("신고 유형을 선택해주세요.");
    } else if (report === "") {
      alert("신고 내용을 입력해주세요.");
    } else {
      if (isSubmitting) return;

      setIsSubmitting(true);

      const payload = {
        articleSeq,
        reportType: reportType,
        reportContent: report,
      };

      ArticleService.saveReport(payload);
      setReported(true);
      setReport("");
      setReportType(0);
      setReportModalOpen(false);
      setIsSubmitting(false);
    }
  };

  useEffect(() => {
    fetchArticle();
  }, [articleSeq]);

  return (
    isLoaded && (
      <div
        className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-10"
        onClick={onClose}
      >
        <div
          className="relative bg-white rounded-lg max-w-[350px] w-full shadow-lg p-4"
          onClick={(e) => e.stopPropagation()}
        >
          <picture>
            <source
              srcSet={fortuneImages[detail?.fortuneImg].src}
              type="image/webp"
            />
            <img
              src={fortuneImages[detail?.fortuneImg].fallback}
              alt="Fortune"
              className="absolute top-[-45px] left-1/2 transform -translate-x-1/2 w-[100px] h-[100px] z-10"
            />
          </picture>

          <div className="flex justify-end mb-1">
            <button className="text-gray-600 rounded-md" onClick={onClose}>
              <Suspense>
                <AiOutlineClose size={24} />
              </Suspense>
            </button>
          </div>

          <div className="relative">
            <div
              className={`border rounded-md mb-2 p-1 ${
                !detail.articleVisibility ? "filter blur-lg" : ""
              }`}
            >
              <div className="text-[#3c1e1e] text-xl mb-1">
                {"From. " + detail?.userNickname}
              </div>
              <div className="text-[#3c1e1e] h-[300px] whitespace-pre-wrap break-words overflow-y-auto">
                {detail?.articleContent}
              </div>
            </div>

            {/* 자물쇠 아이콘 추가 */}
            {!detail.articleVisibility && (
              <div className="absolute inset-0 flex flex-col items-center justify-center bg-gray-800 bg-opacity-60 rounded-md">
                <Suspense>
                  <AiOutlineLock size={160} />
                </Suspense>
                <div className="text-2xl">비밀 복주머니입니다.</div>
              </div>
            )}
          </div>

          <div
            className={`flex justify-between ${
              !detail.articleVisibility ? "invisible" : ""
            }`}
          >
            <div className="flex gap-2">
              <button
                className="bg-yellow-500 rounded-md py-1 px-3"
                onClick={() => setReportModalOpen(true)}
              >
                <Suspense>
                  <AiOutlineAlert size={24} />
                </Suspense>
              </button>

              {myAddress === address && (
                <button
                  className="bg-red-500 rounded-md py-1 px-3"
                  onClick={() => setDeleteModalOpen(true)}
                >
                  <Suspense>
                    <AiOutlineDelete size={24} />
                  </Suspense>
                </button>
              )}

              {deleteModalOpen && (
                <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-40">
                  <div className="flex flex-col items-center bg-white rounded-lg shadow-lg p-6 w-80">
                    <h2 className="text-xl text-[#3c1e1e] mb-4">
                      메세지는 복구할 수 없어요.
                    </h2>
                    <p className="text-gray-700 mb-6">정말 삭제하시겠어요?</p>
                    <div className="flex gap-4">
                      <button
                        className="bg-gray-300 text-black rounded-md py-2 px-4"
                        onClick={() => setDeleteModalOpen(false)}
                      >
                        취소
                      </button>
                      <button
                        className="bg-red-500 text-white rounded-md py-2 px-4"
                        onClick={confirmDelete}
                      >
                        삭제
                      </button>
                    </div>
                  </div>
                </div>
              )}
            </div>

            {myAddress === address && (
              <button
                className={"bg-green-600 rounded-md py-1 px-4"}
                onClick={() => {
                  setCommentModalOpen(true);
                }}
              >
                <Suspense>
                  <AiOutlineMail size={24} />
                </Suspense>
              </button>
            )}

            {commentModalOpen && (
              <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-40">
                <div className="flex flex-col items-center bg-white rounded-lg shadow-lg w-80 p-6">
                  <h2 className="text-xl text-[#3c1e1e] mb-4">
                    답장을 전달하러 가시겠어요?
                  </h2>
                  <div className="flex gap-4">
                    <button
                      className="bg-gray-300 text-black rounded-md py-2 px-4"
                      onClick={() => setCommentModalOpen(false)}
                    >
                      취소
                    </button>
                    <button
                      className="bg-green-600 rounded-md py-2 px-4"
                      onClick={confirmComment}
                    >
                      확인
                    </button>
                  </div>
                </div>
              </div>
            )}
          </div>

          {reportModalOpen && (
            <div>
              <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-60 z-40">
                <div className="bg-white text-[#3c1e1e] rounded-lg w-80 p-5">
                  <h2 className=" text-2xl mb-4">신고하기</h2>

                  <label className="text-[gray]">
                    <select
                      className="mb-4"
                      value={reportType}
                      onChange={(e) => setReportType(Number(e.target.value))}
                    >
                      <option value={0}>신고 유형 선택</option>
                      <option value={1}>불쾌감을 주는 내용</option>
                      <option value={2}>혐오적인 표현 사용</option>
                      <option value={3}>기타</option>
                    </select>
                  </label>

                  <textarea
                    className="w-full border border-gray-300 rounded-md h-48 mb-2 p-2 resize-none"
                    placeholder="신고 이유를 알려주세요."
                    value={report}
                    onChange={(e) => setReport(e.target.value)}
                  ></textarea>
                  <div className="flex justify-end gap-4">
                    <button
                      className="bg-gray-300 text-black rounded-lg py-2 px-4"
                      onClick={() => setReportModalOpen(false)}
                    >
                      취소
                    </button>
                    <button
                      className="bg-[#0d1a26] text-white rounded-lg py-2 px-4"
                      onClick={sendReport}
                    >
                      보내기
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>

        {/* 신고 성공 알림 */}
        <Alarm
          message="신고 완료!"
          visible={reported}
          onClose={() => setReported(false)}
          backgroundColor={"bg-green-500"}
        />
      </div>
    )
  );
};

export default Article;
