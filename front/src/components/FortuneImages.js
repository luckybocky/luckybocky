import FortuneImageBasicW from "../image/fortunes/새해뱀.webp";
import FortuneImageHealthW from "../image/fortunes/건강뱀.webp";
import FortuneImageLoveW from "../image/fortunes/애정뱀.webp";
import FortuneImageWealthW from "../image/fortunes/재물뱀.webp";
import FortuneImageJobW from "../image/fortunes/취업뱀.webp";
import FortuneImageEduW from "../image/fortunes/학업뱀.webp";
import FortuneImageLuckyW from "../image/fortunes/행운뱀.webp";

import FortuneImageBasicP from "../image/fortunes/새해뱀.png";
import FortuneImageHealthP from "../image/fortunes/건강뱀.png";
import FortuneImageLoveP from "../image/fortunes/애정뱀.png";
import FortuneImageWealthP from "../image/fortunes/재물뱀.png";
import FortuneImageJobP from "../image/fortunes/취업뱀.png";
import FortuneImageEduP from "../image/fortunes/학업뱀.png";
import FortuneImageLuckyP from "../image/fortunes/행운뱀.png";

const fortuneImages = [
  {
    src: FortuneImageBasicW, fallback: FortuneImageBasicP,
    name: "새해복",
    comment: "벌써 2025년 설날이 다가왔습니다. \n\n뱀만큼 긴 연휴, 푸르고 밝은 설날이 되길 소망합니다. \n\n당신의 올해를 응원하며, 새해 복 많이 받으시길 바랍니다."
  },
  { src: FortuneImageHealthW, fallback: FortuneImageHealthP, name: "건강복", comment: "2025년 설, 새해가 밝았습니다. \n\n저는 당신이 올 한 해 건강했으면 좋겠습니다. 그대의 아픔은 저의 고통이기에.. \n\n건강한 모습에, 보다 유연한 몸과 마음을 갖춘 그대가 되길 기도하겠습니다." },
  { src: FortuneImageLoveW, fallback: FortuneImageLoveP, name: "애정복", comment: "지금까지 정체였던 당신의 애정운,  청신호가 되었습니다. \n\n가장 뜨거운 푸른 불꽃처럼, \n\n누군가에게는 더욱 뜨거워지는 계기가, 누군가에게는 서로를 비추는 인연을 만날 수 있는 한 해가 되길 소망합니다." },
  { src: FortuneImageWealthW, fallback: FortuneImageWealthP, name: "재물복", comment: "초록색과 파란색이 공존하는 푸른색. \n\n서양에서는 이들을 상황에 따라 부를 상징하는 색이라고 여긴다고 합니다. \n\n풍요를 상징하는 뱀과 부를 상징하는 푸른색, 이들이 조화를 이루는 2025년, 그대에게도 풍요가 깃들길 기원합니다." },
  { src: FortuneImageJobW, fallback: FortuneImageJobP, name: "취업복", comment: "너무나도 추운 취업 시장입니다. \n\n좌절하지 않았으면 좋겠습니다. \n포기하지 않았으면 좋겠습니다. \n\n응원조차 당신에게 짐을 지우는 것임을 알기에... \n\n그저 묵묵히, 당신의 옆에 함께 있겠습니다." },
  { src: FortuneImageEduW, fallback: FortuneImageEduP, name: "학업복", comment: "2025년 올해는 푸른 뱀의 해, 푸른색과 뱀, 모두 지혜의 상징이라고 합니다. \n\n비록 미신일지언정, 힘든 길을 걷고 있는 당신에게 부디 자그마한 마음을 전하고 싶었습니다. \n\n실패하려고 공부하는 사람은 없습니다. 그러니 스스로를 의심하지 않았으면 좋겠습니다." },
  { src: FortuneImageLuckyW, fallback: FortuneImageLuckyP, name: "행운복", comment: "설을 핑계로 그대에게 연락을 드려봅니다. \n\n그대가 필요로 하는 올해의 소망은 무엇일까요? \n\n건강, 애정, 재물, 취업, 학업 그게 무엇이든 그대의 소망이 이루어지면 좋겠습니다. \n\n새해 복 많이 받으세요." },
];

export default fortuneImages;
