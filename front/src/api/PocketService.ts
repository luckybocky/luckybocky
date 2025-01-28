import ApiClient from "./ApiClient";

export interface ArticleSummary {
  articleSeq: number;
  fortuneName: string;
  fortuneImg: number;
  userNickname: string;
}

export interface PocketDetail {
  pocketSeq: number;
  userNickname: string;
  articles: ArticleSummary[];
  fortuneVisibility : boolean;
}

class PocketService {
  /**
   * 내 복 주머니 주소 가져오기
   * @returns {Promise<string>}
   */
  static async getMyAddress(): Promise<string | undefined> {
    try {
      const response = await ApiClient.get(`pocket/address`);

      return response.data.data;
    } catch (error) {
      if (error.status === 404) {
        const address = await PocketService.save();
        return address;
      }

      console.error("Error in getMyAddress:", error);
    }
  }

  /**
   * 복주머니 생성 후 주소 가져오기
   * @returns {Promise<string>}
   */
  static async save(): Promise<string | undefined> {
    try {
      const response = await ApiClient.post(`pocket`);

      return response.data.data;
    } catch (error) {
      console.error("Error in save", error);
    }
  }

  /**
   * 복주머니 정보 가져오기
   * @param {string} pocketAddress
   * @returns {Promise<PocketDetail>}
   */
  static async getByAddress(
    pocketAddress: string
  ): Promise<PocketDetail | undefined> {
    try {
      const response = await ApiClient.get("pocket/" + pocketAddress);

      return response.data.data;
    } catch (error) {
      console.error("Error in getByAddress", error);
    }
  }
}

export default PocketService;
