import FortuneImageBasicW from "../image/fortunes/푸른뱀.webp";
import FortuneImageHealthW from "../image/fortunes/건강뱀.webp";
import FortuneImageLoveW from "../image/fortunes/애정뱀.webp";
import FortuneImageWealthW from "../image/fortunes/재물뱀.webp";
import FortuneImageJobW from "../image/fortunes/취업뱀.webp";
import FortuneImageEduW from "../image/fortunes/학업뱀.webp";

import FortuneImageBasicP from "../image/fortunes/푸른뱀.png";
import FortuneImageHealthP from "../image/fortunes/건강뱀.png";
import FortuneImageLoveP from "../image/fortunes/애정뱀.png";
import FortuneImageWealthP from "../image/fortunes/재물뱀.png";
import FortuneImageJobP from "../image/fortunes/취업뱀.png";
import FortuneImageEduP from "../image/fortunes/학업뱀.png";

const fortuneImages = [
  { src: FortuneImageBasicW, fallback: FortuneImageBasicP },
  { src: FortuneImageHealthW, fallback: FortuneImageHealthP },
  { src: FortuneImageLoveW, fallback: FortuneImageLoveP },
  { src: FortuneImageWealthW, fallback: FortuneImageWealthP },
  { src: FortuneImageJobW, fallback: FortuneImageJobP },
  { src: FortuneImageEduW, fallback: FortuneImageEduP },
];

export default fortuneImages;
