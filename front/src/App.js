import React from "react";
import { RouterProvider } from "react-router-dom";
import router from "./router";

const App = () => {
  return (
    <div className="flex flex-col items-center justify-center bg-[#0d1a26] text-white min-h-screen text-base">
      {/* 라우터 적용 */}
      <RouterProvider router={router} />
    </div>
  );
};

export default App;