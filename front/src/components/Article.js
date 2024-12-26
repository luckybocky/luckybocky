import React from "react";

const Article = ({ onClose, content }) => {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
      <div className="bg-gray-100 rounded-lg p-6 w-[300px] text-center shadow-lg">
        <h2 className="text-xl font-bold mb-4">{content}</h2>
        <div className="flex justify-between">
          <button
            className="bg-red-500 text-white py-2 px-4 rounded-md"
            onClick={onClose}
          >
            삭제
          </button>
          <button
            className="bg-blue-500 text-white py-2 px-4 rounded-md"
            onClick={onClose}
          >
            닫기
          </button>
        </div>
      </div>
    </div>
  );
};

export default Article;
