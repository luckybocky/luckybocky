import React from "react";
import { RouterProvider } from "react-router-dom";
import router from "./router";

const App = () => {
  return (
    //default : text-white,lg / 높이 기기 화면에 맞춤
    <div className="flex justify-center bg-[#ede0d4] text-white min-h-screen text-base">
      {/* 라우터 적용 */}
      <RouterProvider router={router} future={{ v7_startTransition: true }}/>
    </div>
  );
};

export default App;
