import ApiClient from "./ApiClient";

export interface ArticlePayload {
  pocketSeq: number;
  nickname: string;
  content: string;
  fortuneSeq: number;
  url: string;
}

export interface MyArticle {
  pocketOwner: string;
  articleOwner: string;
  content: string;
  fortuneName: string;
  fortuneImg: number;
  createdAt: string;
}

export interface ArticleDetail {
  articleVisibility: boolean;
  articleSeq: number;
  userKey: string;
  userNickname: string;
  pocketAddress: String;
  articleContent: string;
  fortuneName: string;
  fortuneImg: number;
  createdAt: string;
}

// export interface CommentPayload {
//   articleSeq: number;
//   comment: string;
//   url: string;
// }

export interface ReportPayload {
  articleSeq: number;
  reportType: number;
  reportContent: string;
}

class ArticleService {
  /**
   * 사용자 글 목록 가져오기
   * @returns {Promise<Article[]>}
   */
  static async getMyList(): Promise<MyArticle[] | undefined> {
    try {
      const response = await ApiClient.get("article/user");
      return response.data.data.articles;
    } catch (error) {
      console.error("Error in getMyList:", error);
    }
  }

  /**
   * 글 작성
   * @param {ArticlePayload} payload - 글 작성 정보
   * @returns {Promise<void>}
   */
  static async save(payload: ArticlePayload): Promise<void> {
    try {
      await ApiClient.post("article", payload);
    } catch (error) {
      console.error("Error in save:", error);
    }
  }

  /**
   * 글 상세 불러오기
   * @param {number} articleSeq
   * @returns {Promise<ArticleDetail>}
   */
  static async getBySeq(
    articleSeq: number
  ): Promise<ArticleDetail | undefined> {
    try {
      const result = await ApiClient.get(`article?articleSeq=${articleSeq}`);
      return result.data.data;
    } catch (error) {
      console.error("Error in getBySeq:", error);
    }
  }

  /**
   * 글 삭제
   * @param {number} articleSeq
   * @returns {Promise<void>}
   */
  static async deleteBySeq(articleSeq: number): Promise<void> {
    try {
      await ApiClient.delete(`article?articleSeq=${articleSeq}`);
    } catch (error) {
      console.error("Error in deleteBySeq:", error);
    }
  }

  // /**
  //  * 댓글 저장
  //  * @param {CommentPayload} payload - 댓글 작성 정보
  //  * @returns {Promise<void>}
  //  */
  // static async saveComment(payload: CommentPayload): Promise<void> {
  //   try {
  //     await ApiClient.post("comment", payload);
  //   } catch (error) {
  //     console.error("Error in saving comment:", error);
  //   }
  // }

  /**
   * 신고 저장
   * @param {ReportPayload} payload - 신고 작성 정보
   * @returns {Promise<void>}
   */
  static async saveReport(payload: ReportPayload): Promise<void> {
    try {
      await ApiClient.post("report", payload);
    } catch (error) {
      console.error("Error in saving Report:", error);
    }
  }
}

export default ArticleService;
