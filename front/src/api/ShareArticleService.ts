import ApiClient from "./ApiClient";
import { ArticleDetail } from "./ArticleService.ts";

export interface ShareArticlePayload {
    content: string;
    fortuneSeq: number;
}

export interface ShareArticle {
    shareArticleSeq: number;
    userKey: string;
    userNickname: string;
    pocketAddress: string;
    fortuneSeq: number;
    articles: ArticleDetail[];
    shareArticleContent: string;
    shareArticleAddress: string;
    shareCount: number;
}

export interface ShareArticleDetail {
    shareArticleDto: ShareArticle;
    login: boolean;
}

class ShareArticleService {
    /**
     * 공유 게시글 작성
     * @param {ShareArticlePayload} payload 글 작성 정보
     * @returns {Promise<ShareArticle>}
     */
    static async save(payload: ShareArticlePayload): Promise<ShareArticle | undefined> {
        try {
            const response = await ApiClient.post("share", payload);

            return response.data.data;
        } catch (error) {
            console.error("Error in save:", error);
        }
    }

    /**
     * 공유 글 상세를 불러오고 상태에 따라 복주머니에 글 저장
     * @param {string} shareArticleAddress 공유 글 주소
     * @returns {Promise<ShareArticleDetail>} 공유 글 상세와 로그인 정보
     */
    static async getByShareArticleAddress(
        shareArticleAddress: string
    ): Promise<ShareArticleDetail | undefined> {
        try {
            const result = await ApiClient.get(`share/${shareArticleAddress}`);

            return result.data.data;
        } catch (error) {
            console.error("Error in getByShareArticleAddress:", error);
        }
    }
}

export default ShareArticleService;
