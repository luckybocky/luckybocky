import React from "react";
import Comment from "../components/Comment"
import { AiOutlineDelete, AiOutlineClose } from "react-icons/ai";

const Article = ({ onClose, content, seq }) => {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
      <div className="bg-gray-100 rounded-lg p-6 w-[300px] text-center shadow-lg">
        <div className="flex justify-between mb-4">
          <button
            className="bg-red-500 text-white py-2 px-4 rounded-md"
            onClick={onClose}
          >
            <AiOutlineDelete />
          </button>
          <button
            className="bg-blue-500 text-white py-2 px-4 rounded-md"
            onClick={onClose}
          >
            <AiOutlineClose />
          </button>
        </div>

        <h2 className="text-xl font-bold mb-4">{content}</h2>
        
        <div className="mb-4"></div>

        <Comment          
          articleSeq = {seq}
        ></Comment>
        
      </div>
    </div>
  );
};

export default Article;
