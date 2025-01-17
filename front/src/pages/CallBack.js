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

  return <h1 className="flex items-center text-4xl">로그인 중 입니다...</h1>;
};

export default CallBack;
