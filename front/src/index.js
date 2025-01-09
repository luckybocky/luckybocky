import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";

import { setupOnMessageListener } from "./api/FireBaseApi"; //12-31 창희 추가, 파이어베이스 api들고오기

// iOS 환경 감지 함수
const isIos = () => {
  const ua = navigator.userAgent;
  const isIosDevice = /iPhone|iPad|iPod/i.test(ua);
  // const isKakaoWebView = /KAKAOTALK/i.test(ua);
  // return isIosDevice && isKakaoWebView;
  return isIosDevice;
};
// 서비스 워커 등록
try {
  if ("serviceWorker" in navigator && !isIos()) {
    navigator.serviceWorker
      .register("/firebase-messaging-sw.js") // 서비스 워커 경로
      .then((registration) => {
        console.log(
          "Service Worker registered with scope:",
          registration.scope
        );
      })
      .catch((error) => {
        console.error("Service Worker registration failed:", error);
      });
  } else if (isIos()) {
    console.log("iOS 환경에서는 서비스워커를 지원하지 않습니다.");
  }
} catch (error) {
  console.error("Service Worker 등록 중 예기치 못한 오류 발생:", error);
}

//=====12-31 창희 추가 start=====
//main페이지에 들어오면 로그인이 성공했다고 판단하기에, 푸시를 위한 파이어베이스키 업데이트
// console.log("12-31 창희 추가");

setupOnMessageListener();

//=====12-31 창희 추가 end=====

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  // <React.StrictMode>
  <App />
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
