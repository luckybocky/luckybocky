import ApiClient from "./ApiClient.ts";

export interface FeedbackPayload {
  feedbackContent: string;
  feedbackRate: number;
}

class FeedbackService {
  /**
   * 피드백 작성
   * @param {FeedbackPayload} payload - 피드백 작성 정보
   * @returns {Promise<void>}
   */
  static async save(payload: FeedbackPayload): Promise<void> {
    try {
      await ApiClient.post("feedback", payload);
    } catch (error) {
      console.error("Error in save:", error);
    }
  }
}

export default FeedbackService;
