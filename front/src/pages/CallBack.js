import React, { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";

import AuthService from "../api/AuthService.ts";

const CallBack = () => {
  const navigate = useNavigate();

  const isCalled = useRef(false);

  const urlParams = new URLSearchParams(window.location.search);
  const code = urlParams.get("code");

  const init = async () => {
    if (isCalled.current) return; // 이미 실행된 경우 중단
    isCalled.current = true;

    const result = await AuthService.login(code);

    if (result === 2) navigate(window.sessionStorage.getItem("pocketAddress")||"/");
    else if (result === 1) navigate("/join");
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <div className="flex flex-col justify-center items-center p-6">
       <h1 className="text-4xl mb-1">로그인 중<span className="dots"></span></h1>
      <style>{`
        @keyframes dotAnimation {
          0% { content: ""; }
          33% { content: "."; }
          66% { content: ".."; }
          100% { content: "..."; }
        }
        .dots::after {
          display: inline-block;
          animation: dotAnimation 3s steps(3, end) infinite;
          content: "...";
        }
      `}</style>
    </div>
  );
};

export default CallBack;
