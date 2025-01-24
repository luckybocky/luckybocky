import React, { useState } from "react";

import pengshuImageP from "../image/pengshu.png";
import pengshuImageW from "../image/pengshu.webp";

const Footer = () => {
  const [clickCount, setClickCount] = useState(0); 
  const [easterEgg, setEasterEgg] = useState(false); 

  const handleClick = () => {
    setClickCount((prev) => prev + 1);

    if (clickCount + 1 === 5) {
      setEasterEgg(true); 
      setClickCount(0); 

      setTimeout(() => {
        setEasterEgg(false);
      }, 3000);
    }
  };

  return (
    <footer className="absolute bottom-5 text-xs" onClick={handleClick}>
      <p>Copyright ©luckybocky. All rights reserved.</p>
      {easterEgg && (
          <picture>
          <source srcSet={pengshuImageW} type="image/webp" />
          <img
            src={pengshuImageP}
            alt="easterEgg"
            className="fixed bottom-10 w-60 h-90 z-30 opacity-80"
          />
        </picture>
      )}
    </footer>
  );
};

export default Footer;
