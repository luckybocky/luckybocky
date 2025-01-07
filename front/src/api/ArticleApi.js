import ApiClient from "./ApiClient";

export const myArticle = async () => {
  try {
    const response = await ApiClient.get("article/user");

    return response.data.data.articles;
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
      visibility: payload.visibility,
    };

    await ApiClient.post("article", setArticle);
  } catch (error) {
    console.debug("write Article error", error);
  }
};

export const loadArticle = async (articleSeq) => {
  try {
    const result = await ApiClient.get(`article?articleSeq=${articleSeq}`);

    return result.data.data;
  } catch (error) {
    console.debug("load Article error", error);
  }
};

export const deleteArticle = async (articleSeq) => {
  try {
    await ApiClient.delete(`article?articleSeq=${articleSeq}`);
  } catch (error) {
    console.debug("delete Article error", error);
  }
};
