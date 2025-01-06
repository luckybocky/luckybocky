import ApiClient from "./ApiClient";

export const saveFeedback = async (content, rating) => {
  const feedback = { feedbackContent: content, feedbackRate: rating };

  try {
    await ApiClient.post("feedback", feedback);
  } catch (error) {
    console.error("feedback error", error);
  }
};
