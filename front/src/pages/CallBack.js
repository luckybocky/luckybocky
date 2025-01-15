import React, { useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { callback } from "../api/AuthApi";

const CallBack = () => {
  const navigate = useNavigate();
  const urlParams = new URLSearchParams(window.location.search);
  const isCalled = useRef(false);
  const code = urlParams.get("code");
  const redirectPath = urlParams.get("state");

  useEffect(() => {
    const init = async () => {
      if (isCalled.current) return; // 이미 실행된 경우 중단
      isCalled.current = true;

      const result = await callback(code);
      console.log(result);
      console.log(redirectPath);
      if (result === 2) navigate(redirectPath);
      else if (result === 1) navigate("/join");
    };
    init();
  }, []);

  return (
    <div>
      <h1 className="min-h-screen flex items-center text-4xl">
        로그인 중 입니다...
      </h1>
    </div>
  );
};

export default CallBack;
