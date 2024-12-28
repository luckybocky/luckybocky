import ApiClient from "./ApiClient";

export const myArticle = async () => {
  try {
    const response = await ApiClient.get("article/user");

    return response.data.articles;
  } catch (error) {
    console.error("my Article error", error);
  }
};
