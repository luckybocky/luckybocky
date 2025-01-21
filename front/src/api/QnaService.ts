import ApiClient from "./ApiClient";

export interface QuestionPayload {
  title: string;
  content: string;
  secretStatus: boolean;
}

export interface Question {
  qnaSeq: number;
  title: string;
  content: string;
  answer: string;
  secretStatus: boolean;
  userNickname: string;
  createdAt?: string;
}

export interface PaginationParams {
  page: number;
  size: number;
  sort: string;
}

export interface PaginatedResponse<Question> {
  question: Question[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

class QnaService {
  /**
   * 질문들 조회
   * @param {PaginationParams} params - 페이지네이션 파라미터
   * @returns {Promise<PaginatedResponse<Question>>}
   */
  static async getMyQuestion(params: PaginationParams): Promise<PaginatedResponse<Question> | undefined> {  // 내 QnA만 조회 -> 사용자 관점
    try {
      const { page, size, sort } = params;
      const response = await ApiClient.get("qna/question/me", {
        params: { page, size, sort },
      });

      return response.data.data.qnaListResDto;  // 응답 구조에 맞게 반환
    } catch (error) {
      console.error("Error in getMyQuestions:", error);
    }
  }

  // static async beforeAnswer(): {  // 답변달리지 않은 QnA -> 관리자 관점

  // }

  /**
   * 질문들 조회
   * @param {PaginationParams} params - 페이지네이션 파라미터
   * @returns {Promise<PaginatedResponse<Question>>}
   */
    static async getQuestions(params: PaginationParams): Promise<PaginatedResponse<Question> | undefined> {
      try {
        const { page, size, sort } = params;
        const response = await ApiClient.get("qna/question", {
          params: { page, size, sort },
        });

        return response.data.data.qnaListResDto;  // 응답 구조에 맞게 반환
      } catch (error) {
        console.error("Error in getQuestions:", error);
      }
    }

  /**
   * 질문 등록
   * @param {QuestionPayload} payload - 질문
   * @returns {Promise<void>}
   */
  static async saveQuestion(payload: QuestionPayload): Promise<void> {
    try {
      await ApiClient.post("qna/question", payload);
    } catch (error) {
      console.error("Error in question save: ", error);
    }
  }

  /**
   * 
   * @param qnaSeq 
   * @returns {Promise<number>}
   */
  static async checkAccess(qnaSeq: number): Promise<number | undefined> {  // 특정 질문 조회
    try {
      const response = await ApiClient.get(`qna/question/${qnaSeq}`)
      
      return response.data.data;
    } catch (error) {
      console.error("Failed to access QnA: ", error)
    }
  }

  // static async updateQuestion(): {  // 특정 질문 수정

  // }

  // static async deleteQuestion(): {  // 특정 질문 삭제

  // }

    /**
   * 답변 등록
   * @param {Question} payload - 답변이 추가된 질문
   * @returns {Promise<void>}
   */
  static async saveAnswer(payload: Question): Promise<void> {  // 답변 등록
    try {
      await ApiClient.put("qna/answer", payload);
    } catch (error) {
      console.error("Error in answer save: ", error);
    }
  }

  // static async updateAnswer(): {  // 답변 수정

  // }

  // static async deleteAnswer(): {  // 답변 삭제

  // }
}

export default QnaService;
