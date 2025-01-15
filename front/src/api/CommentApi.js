import ApiClient from "./ApiClient";

export const saveComment = async (article, message) => {
  const myComment = {
    articleSeq: article,
    comment: message,
    url: `${window.location.origin}${window.location.pathname}`,
  };

  try {
    await ApiClient.post("comment", myComment);
  } catch (error) {
    console.error("saving comment error", error);
  }
};
