import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";

import fortuneImages from "../components/FortuneImages";

const SelectDecoPage = () => {
  const navigate = useNavigate();

  const [selectedDecoration, setSelectedDecoration] = useState(null);

  const location = useLocation();
  const { address, pocketSeq } = location.state;

  const decorations = new Array(9).fill(null).map((_, idx) => idx);

  const handleNext = () => {
    if (selectedDecoration === null) {
      alert("장식을 선택해주세요!");
      return;
    }
    navigate("/write", {
      state: {
        decorationId: selectedDecoration,
        pocketAddress: address,
        pocketSeq,
      },
    });
  };

  return (
    <div className="flex flex-col items-center justify-center w-full max-w-[600px] bg-[#f5f5f5] p-4">
      <h1 className="text-3xl text-[#3c1e1e] mb-4">장식을 골라주세요</h1>

      <div className="grid grid-cols-3 gap-4 mb-6">
        {decorations.map(
          (id) =>
            fortuneImages[id] && (
              <button
                key={id}
                className={`border-4 rounded-md ${
                  selectedDecoration === id
                    ? "border-blue-500"
                    : "border-gray-300"
                }`}
                onClick={() => setSelectedDecoration(id)}
              >
                <picture>
                  <source srcSet={fortuneImages[id].src} type="image/webp" />
                  <img
                    src={fortuneImages[id].fallback}
                    alt="장식물"
                    className="w-20 h-20"
                  />
                </picture>
              </button>
            )
        )}
      </div>

      <div className="flex gap-2">
        <button
          onClick={() => navigate(-1)}
          className="bg-gray-500 rounded-lg py-2 px-6 "
        >
          이전
        </button>
        <button
          onClick={handleNext}
          className="bg-blue-500 rounded-lg py-2 px-6"
        >
          다음
        </button>
      </div>
    </div>
  );
};

export default SelectDecoPage;
