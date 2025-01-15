import React from "react";
import { RouterProvider } from "react-router-dom";
import router from "./router";

const App = () => {
  return (
    <div className="flex flex-col items-center justify-center bg-[#ede0d4] text-white min-h-screen text-lg">
      {/* 라우터 적용 */}
      <RouterProvider router={router} />
    </div>
  );
};

export default App;
