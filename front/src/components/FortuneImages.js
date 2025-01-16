import FortuneImageBasic from "../image/fortunes/푸른뱀.png";
import FortuneImageHealth from "../image/fortunes/건강뱀.png";
import FortuneImageLove from "../image/fortunes/애정뱀.png";
import FortuneImageWealth from "../image/fortunes/재물뱀.png";
import FortuneImageJob from "../image/fortunes/취업뱀.png";
import FortuneImageEdu from "../image/fortunes/학업뱀.png";

import FortuneImageBasicW from "../image/fortunes/푸른뱀.webp";
import FortuneImageHealthW from "../image/fortunes/건강뱀.webp";
import FortuneImageLoveW from "../image/fortunes/애정뱀.webp";
import FortuneImageWealthW from "../image/fortunes/재물뱀.webp";
import FortuneImageJobW from "../image/fortunes/취업뱀.webp";
import FortuneImageEduW from "../image/fortunes/학업뱀.webp";

const fortuneImages = [
  { src: FortuneImageBasicW, fallback: FortuneImageBasic },
  { src: FortuneImageHealthW, fallback: FortuneImageHealth },
  { src: FortuneImageLoveW, fallback: FortuneImageLove },
  { src: FortuneImageWealthW, fallback: FortuneImageWealth },
  { src: FortuneImageJobW, fallback: FortuneImageJob },
  { src: FortuneImageEduW, fallback: FortuneImageEdu },
];

export default fortuneImages;
