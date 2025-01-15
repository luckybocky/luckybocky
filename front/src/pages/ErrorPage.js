import React from "react";
import { useNavigate } from "react-router-dom";

const ErrorPage = () => {
  const navigate = useNavigate();
  return (
    <div className="min-h-screen flex flex-col items-center justify-center">
      <h1 className=" text-4xl">잘못된 경로유!</h1>
      <button
        className="bg-red-500 text-white py-2 px-4 rounded-lg mt-4"
        onClick={() => {
          navigate("/");
        }}
      >
        메인으로
      </button>
    </div>
  );
};

export default ErrorPage;
