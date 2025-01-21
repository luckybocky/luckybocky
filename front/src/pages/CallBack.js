import React, { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";

import AuthService from "../api/AuthService.ts";

const CallBack = () => {
  const navigate = useNavigate();

  const isCalled = useRef(false);

  const urlParams = new URLSearchParams(window.location.search);
  const code = urlParams.get("code");
  const redirectPath = urlParams.get("state");

  const init = async () => {
    if (isCalled.current) return; // 이미 실행된 경우 중단
    isCalled.current = true;

    const result = await AuthService.login(code);

    if (result === 2) navigate(redirectPath);
    else if (result === 1) navigate("/join");
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <div className="flex flex-col justify-center items-center">
      <h1 className="text-4xl mb-1">로그인 중 입니다...</h1>
      <p className="text-xl">알림 권한을 확인해주세요.</p>
    </div>
  );
};

export default CallBack;
