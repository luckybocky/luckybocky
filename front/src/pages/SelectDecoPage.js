import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import fortuneImages from "../components/FortuneImages";

const SelectDecoPage = () => {
  const [selectedDecoration, setSelectedDecoration] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();
  const { address, pocketSeq } = location.state;
  const decorations = new Array(6).fill(null).map((_, idx) => idx);

  const handleNext = () => {
    if (selectedDecoration === null) {
      alert("장식을 선택해주세요!");
      return;
    }
    navigate("/write", {
      state: {
        decorationId: selectedDecoration,
        pocketAddress: address,
        // visibility: fortuneVisibility,
        pocketSeq,
      },
    });
  };

  return (
    <div className="flex flex-col items-center justify-center w-full max-w-[375px] min-h-screen bg-[#f5f5f5] text-black mx-auto p-2">
      <h1 className="text-2xl mb-4">장식을 골라주세요</h1>
      <div className="grid grid-cols-3 gap-4 mb-6">
        {decorations.map((id) => (
          <button
            key={id}
            className={`p-2 border-2 rounded-md ${
              selectedDecoration === id ? "border-blue-500" : "border-gray-300"
            }`}
            onClick={() => setSelectedDecoration(id)}
          >
            <img
              src={fortuneImages[id]}
              alt="장식물"
              className="w-16 h-16 mx-auto"
            />
          </button>
        ))}
      </div>
      <div className="flex gap-2">
        <button
          onClick={() => navigate(-1)}
          className="bg-gray-500 text-white py-2 px-6 rounded-lg"
        >
          이전
        </button>
        <button
          onClick={handleNext}
          className="bg-blue-500 text-white py-2 px-6 rounded-lg"
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default SelectDecoPage;
