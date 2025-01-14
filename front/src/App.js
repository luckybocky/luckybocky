import React from "react";
import { RouterProvider } from "react-router-dom";
import router from "./router";
// import bgImage from "./image/background.png";

const App = () => {
  return (
    <div
      className="flex flex-col items-center justify-center bg-[#ede0d4] text-white min-h-screen text-base"
      // style={{
      //   backgroundImage: `url(${bgImage})`,
      //   backgroundSize: "375px 500px", // 배경 이미지를 100x100px로 설정
      //   backgroundRepeat: "repeat", // 배경 이미지 반복
      //   backgroundPosition: "center", // 배경 이미지 중앙 정렬
      //   minHeight: "100vh",
      // }}
    >
      {/* 라우터 적용 */}
      <RouterProvider router={router} />
    </div>
  );
};

export default App;
