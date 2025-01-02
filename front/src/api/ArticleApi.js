import ApiClient from "./ApiClient";

export const myArticle = async () => {
  try {
    const response = await ApiClient.get("article/user");

    return response.data.articles;
  } catch (error) {
    console.error("my Article error", error);
  }
};

export const writeArticle = async (payload) => {
  try {
    const setArticle = {
      pocketSeq: payload.pocketSeq,
      nickname: payload.nickname,
      content: payload.message,
      fortuneSeq: payload.decorationId,
      visibility: payload.visibility === null ? false : payload.visibility
    };
    
    await ApiClient.post("article", setArticle);
  } catch (error) {
    console.debug("write Article error", error);
  }
};