import React, { useState, useEffect, lazy, Suspense } from "react";
import { saveComment } from "../api/CommentApi";
import { saveReport } from "../api/ReportApi";
import { sendCommentPush } from "../api/FireBaseApi";
import { loadArticle, deleteArticle } from "../api/ArticleApi";
import fortuneImages from "../components/FortuneImages";

const AiOutlineMail = lazy(() =>
  import("react-icons/ai").then((module) => ({ default: module.AiOutlineMail }))
);
const AiOutlineAlert = lazy(() =>
  import("react-icons/ai").then((module) => ({
    default: module.AiOutlineAlert,
  }))
);
const AiOutlineDelete = lazy(() =>
  import("react-icons/ai").then((module) => ({
    default: module.AiOutlineDelete,
  }))
);
const AiOutlineClose = lazy(() =>
  import("react-icons/ai").then((module) => ({
    default: module.AiOutlineClose,
  }))
);
const AiOutlineLock = lazy(() =>
  import("react-icons/ai").then((module) => ({ default: module.AiOutlineLock }))
);

const Article = ({ onClose, articleSeq, onDelete, myAddress, address }) => {
  const [reportModalOpen, setReportModalOpen] = useState(false);
  const [report, setReport] = useState("");
  const [reportType, setReportType] = useState(0);
  const [message, setMessage] = useState(null);
  const [detail, setDetail] = useState({
    articleVisibility: false,
    articleSeq: 0,
    userKey: "",
    userNickname: "",
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

  const confirmDelete = async () => {
    await deleteArticle(articleSeq);
    onClose();
    onDelete();
  };

  const confirmComment = async () => {
    await sendComment();
    fetchArticle();
    setCommentModalOpen(false);
  };

  const fetchArticle = async () => {
    const result = await loadArticle(articleSeq);
    setDetail(result);
    setIsLoaded(true);
  };

  useEffect(() => {
    fetchArticle();
  }, [articleSeq]);

  const sendComment = async () => {
    if (!message) {
      alert("답장을 입력해주세요 :)");
      return;
    }
    await saveComment(articleSeq, message);

    //=====01-03 창희 추가 start=====
    //리복할때
    // sendCommentPush(articleSeq);
    //=====01-03 창희 추가 end=====
  };

  const sendReport = () => {
    console.log("Sending seq:", articleSeq); // sendReport 호출 시 seq 값 확인

    if (reportType == 0) {
      alert("신고 유형을 선택해주세요.");
    } else if (report === "") {
      alert("신고 내용을 입력해주세요.");
    } else {
      // alert(`${articleSeq}, ${reportType}, ${report}`)
      saveReport(articleSeq, reportType, report);
      setReported(true);
      setTimeout(() => setReported(false), 2000);
      setReport("");
      setReportType(0);
      setReportModalOpen(false);
    }
  };

  return (
    isLoaded && (
      <div
        className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50"
        onClick={onClose}
      >
        <div
          className="relative bg-white rounded-lg p-4 max-w-[375px] w-full text-center shadow-lg"
          onClick={(e) => e.stopPropagation()}
        >
          {/* 이미지 추가 */}
          <img
            src={fortuneImages[detail?.fortuneImg]}
            alt="Fortune"
            className="absolute top-[-45px] left-1/2 transform -translate-x-1/2 w-[100px] h-[100px] object-contain"
          />
          <div className="flex justify-end mb-1">
            <button
              className="bg-blue-500 text-white py-1 px-3 rounded-md"
              onClick={onClose}
            >
              <Suspense>
                <AiOutlineClose size={24} />
              </Suspense>
            </button>
          </div>

          <div className="relative">
            <div
              className={`border rounded-md mb-2 p-1 text-start ${
                !detail.articleVisibility ? "filter blur-lg" : ""
              }`}
            >
              <div className="text-[black] text-xl mb-1">
                {"From. " + detail?.userNickname}
              </div>
              <div className="text-[black] h-[200px] overflow-y-auto">
                {detail?.articleContent}
              </div>
            </div>
            <textarea
              className={`text-black w-full h-24 p-1 border rounded-md resize-none ${
                !detail.articleVisibility ? "filter blur-lg" : ""
              }`}
              value={detail?.articleComment ? detail?.articleComment : message}
              onChange={(e) => setMessage(e.target.value)}
              placeholder={`${myAddress !== address ? "" : "나도 복 보내기"}`}
              disabled={detail?.articleComment || myAddress !== address}
            />

            {/* 자물쇠 아이콘 추가 */}
            {!detail.articleVisibility && (
              <div className="absolute flex-col rounded-md inset-0 bg-gray-800 bg-opacity-60 flex items-center justify-center z-50">
                <Suspense>
                  <AiOutlineLock size={160} className="text-white" />
                </Suspense>
                <div className="text-2xl">비밀글입니다.</div>
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
                className="bg-yellow-500 text-white py-1 px-3 rounded-md"
                onClick={() => setReportModalOpen(true)}
              >
                <Suspense>
                  <AiOutlineAlert size={24} />
                </Suspense>
              </button>
              {myAddress === address && (
                <button
                  className="bg-red-500 text-white py-1 px-3 rounded-md"
                  onClick={() => setDeleteModalOpen(true)}
                >
                  <Suspense>
                    <AiOutlineDelete size={24} />
                  </Suspense>
                </button>
              )}

              {deleteModalOpen && (
                <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
                  <div
                    className="bg-white rounded-lg p-6 w-80 shadow-lg text-center"
                    onClick={(e) => e.stopPropagation()}
                  >
                    <h2 className="text-xl text-black mb-4">
                      메세지는 다시 복구할 수 없어요.
                    </h2>
                    <p className="text-gray-700 mb-6">정말 삭제하시겠어요?</p>
                    <div className="flex justify-center gap-4">
                      <button
                        className="bg-gray-300 text-black py-2 px-4 rounded-md"
                        onClick={() => setDeleteModalOpen(false)}
                      >
                        취소
                      </button>
                      <button
                        className="bg-red-500 text-white py-2 px-4 rounded-md"
                        onClick={confirmDelete}
                      >
                        삭제
                      </button>
                    </div>
                  </div>
                </div>
              )}
            </div>
            {!detail?.articleComment && myAddress === address && (
              <button
                className={`${
                  message ? "bg-green-500" : "bg-gray-400"
                } text-white py-1 px-4 rounded-md`}
                onClick={() => {
                  setCommentModalOpen(true);
                }}
                disabled={!message}
              >
                <Suspense>
                  <AiOutlineMail size={24} />
                </Suspense>
              </button>
            )}

            {commentModalOpen && (
              <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
                <div
                  className="bg-white rounded-lg p-6 w-80 shadow-lg text-center"
                  onClick={(e) => e.stopPropagation()}
                >
                  <h2 className="text-xl text-black mb-4">
                    답장을 남기시겠어요?
                  </h2>
                  <div className="flex justify-center gap-4">
                    <button
                      className="bg-gray-300 text-black py-2 px-4 rounded-md"
                      onClick={() => setCommentModalOpen(false)}
                    >
                      취소
                    </button>
                    <button
                      className="bg-green-500 text-white py-2 px-4 rounded-md"
                      onClick={confirmComment}
                    >
                      저장
                    </button>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>

        {reportModalOpen && (
          <div>
            <div
              className="fixed inset-0 z-30 flex items-center justify-center bg-black bg-opacity-60"
              onClick={() => setReportModalOpen(false)}
            >
              <div
                className="bg-white text-[#0d1a26] p-5 rounded-lg w-80"
                onClick={(e) => e.stopPropagation()}
              >
                <h2 className="text-2xl mb-4">신고하기</h2>

                <label style={{ color: "gray" }}>
                  <select
                    className="mb-4 text"
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
                  className="w-full h-48 p-2 border border-gray-300 rounded-md mb-2 resize-none"
                  placeholder="신고 이유를 알려주세요."
                  value={report}
                  onChange={(e) => setReport(e.target.value)}
                ></textarea>
                <div className="flex justify-end gap-4">
                  <button
                    className="bg-gray-300 text-black py-2 px-4 rounded-lg"
                    onClick={() => setReportModalOpen(false)}
                  >
                    취소
                  </button>
                  <button
                    className="bg-[#0d1a26] text-white py-2 px-4 rounded-lg"
                    onClick={sendReport}
                  >
                    보내기
                  </button>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* 신고 성공 알림 */}
        {reported && (
          <div className="fixed bottom-16 bg-green-500 text-white py-2 px-4 rounded-lg shadow-md">
            신고 완료!
          </div>
        )}
      </div>
    )
  );
};

export default Article;
