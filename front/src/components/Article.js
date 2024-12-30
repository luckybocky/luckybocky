import React, { useState } from "react";
import { saveComment } from "../api/CommentApi"
import { saveReport } from "../api/ReportApi"
import { AiOutlineMail, AiOutlineAlert, AiOutlineDelete, AiOutlineClose } from "react-icons/ai";

const Article = ({ onClose, content, articleSeq }) => {
  const [reportModalOpen, setReportModalOpen] = useState(false);
  const [report, setReport] = useState("");
  const [reportType, setReportType] = useState(0);
  const [message, setMessage] = useState(null);

  const sendComment = () => {
        if (!message){
          alert("답장을 입력해주세요 :)");
          return;
        }

        alert(`${articleSeq}번 복에 답장하기 : ${message}`)
        saveComment(articleSeq, message);        
    }

  const sendReport = () => {
      console.log("Sending seq:", articleSeq); // sendReport 호출 시 seq 값 확인
      if (reportType == 0){
        alert("신고 유형을 선택해주세요.");
      } else if (report === ""){
        alert("신고 내용을 입력해주세요.");
      } else {
        // alert(`${articleSeq}, ${reportType}, ${report}`)
        saveReport(articleSeq, reportType, report);
        alert("감사합니다. 신고 완료되었습니다.");
        setReport("");
        setReportType(0);
        setReportModalOpen(false);
      }  
  };

  
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
      <div className="bg-gray-100 rounded-lg p-6 w-[300px] text-center shadow-lg">
        <div className="flex justify-between mb-4">
          <button
            className="bg-red-500 text-white py-2 px-4 rounded-md"
            onClick={onClose}
          >
            <AiOutlineDelete />
          </button>

          <button
            className="bg-yellow-500 text-white py-2 px-4 rounded-md"
            onClick={() => setReportModalOpen(true)}
          >
            <AiOutlineAlert />
          </button>
          
          <button
            className="bg-blue-500 text-white py-2 px-4 rounded-md"
            onClick={onClose}
          > 
            <AiOutlineClose />
          </button>
        </div>

        <h2 className="text-xl font-bold mb-4">{content}</h2>

        <div>
            <textarea 
            className="text-xl w-full h-24 p-2 border rounded-md mb-4 resize-none"
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            placeholder="나도 복 보내기"/>
        
            <button
              className="bg-green-500 text-white py-2 px-4 rounded-md"
              onClick={sendComment}
            >
              <AiOutlineMail/>
            </button>
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
            <h2 className="text-2xl font-bold mb-4">신고하기</h2>

            <label style={{ color : "gray"}}>
              <select
                className="mb-4 text"
                value={reportType}
                onChange={e => setReportType(Number(e.target.value))}
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
                onClick={sendReport}>
                보내기
              </button>
            </div>
          </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Article;
