import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import FortuneImageBasic from "../image/fortunes/푸른뱀.png";
import FortuneImageHealth from "../image/fortunes/건강뱀.png";
import FortuneImageLove from "../image/fortunes/애정뱀.png";
import FortuneImageWealth from "../image/fortunes/재물뱀.png";
import FortuneImageJob from "../image/fortunes/취업뱀.png";
import FortuneImageEdu from "../image/fortunes/학업뱀.png";

const SelectDecoPage = () => {
  const images = [
    FortuneImageBasic,
    FortuneImageHealth,
    FortuneImageLove,
    FortuneImageWealth,
    FortuneImageJob,
    FortuneImageEdu,
  ];
  const [selectedDecoration, setSelectedDecoration] = useState(null);
  const navigate = useNavigate();
  const location = useLocation();
  const { address, fortuneVisibility, pocketSeq } = location.state;
  const decorations = new Array(6).fill(null).map((_, idx) => idx + 1);

  const handleNext = () => {
    if (!selectedDecoration) {
      alert("장식을 선택해주세요!");
      return;
    }
    navigate("/write", {
      state: {
        decorationId: selectedDecoration,
        pocketAddress: address,
        visibility: fortuneVisibility,
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
              src={images[id - 1]}
              alt="장식물"
              className="w-16 h-16 mx-auto"
            />
          </button>
        ))}
      </div>
      <div className="flex gap-2">
        <button
          onClick={() => navigate(-1)}
          className="bg-gray-500 text-white py-2 px-6 rounded-md"
        >
          이전
        </button>
        <button
          onClick={handleNext}
          className="bg-blue-500 text-white py-2 px-6 rounded-md"
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default SelectDecoPage;
