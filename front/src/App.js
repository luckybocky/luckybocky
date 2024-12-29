import React from "react";
import { RouterProvider } from "react-router-dom";
import router from "./router";
import CheckLogin from "./components/CheckUser";

const App = () => {
  return (
    <div className="flex flex-col items-center justify-center bg-[#0d1a26] text-white min-h-screen text-xl">
      <CheckLogin />
      {/* 라우터 적용 */}
      <RouterProvider router={router} />
    </div>
  );
};

export default App;
