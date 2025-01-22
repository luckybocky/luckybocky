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
      <h1 className="text-4xl mb-1">로그인 중 입니다...</h1>
    </div>
  );
};

export default CallBack;
