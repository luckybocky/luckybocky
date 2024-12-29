import ApiClient from "./ApiClient";

export const saveComment = async (article, message) => {
    const myComment = { articleSeq: article, comment: message };
  
    try {
      const response = await ApiClient.post('comment', myComment);
    } catch (error) {
      console.error("saving comment error", error);
    }
};  