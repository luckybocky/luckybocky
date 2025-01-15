import ApiClient from "./ApiClient";
import AuthStore from "../store/AuthStore";
import PocketService from "./PocketService.ts";

class AuthService {
  /**
   * 유저 정보 가져오기
   * @returns {Promise<number>}
   */
  static async check(): Promise<number | undefined> {
    try {
      const setUser = AuthStore.getState().setUser;

      const response = await ApiClient.get("auth/user");
      const data = response.data.data;

      const address = await PocketService.getMyAddress();

      setUser({
        userNickname: data.userNickname,
        alarmStatus: data.alarmStatus,
        fortuneVisibility: data.fortuneVisibility,
        createdAt: data.createdAt,
        address: address,
      });

      //닉네임이 없는 경우(설정 페이지로 이동)
      if (data.userNickname === null) {
        return 1;
      }

      return 2;
    } catch (error) {
      console.error("Error in check", error);
    }
  }

  /**
   * 콜백 후 로그인,유저 정보 받아오기
   * @param {string} code - 로그인 코드
   * @returns {Promise<number>}
   */
  static async login(code: string): Promise<number | undefined> {
    if (code) {
      try {
        await ApiClient.get(`auth/callback?code=${code}`);

        const result = await AuthService.check();

        return result;
      } catch (error) {
        console.error("Error in login", error);
      }
    } else console.error("Error in callback: code is null");
  }

  /**
   * 유저 정보 변경
   * @returns {Promise<void>}
   */
  static async update(): Promise<void> {
    const user = AuthStore.getState().user;

    try {
      await ApiClient.put("auth/user", user);
    } catch (error) {
      console.error("Error in update", error);
    }
  }

  /**
   * 로그아웃
   * @returns {Promise<void>}
   */
  static async logout(): Promise<void> {
    const resetUser = AuthStore.getState().resetUser;

    try {
      await ApiClient.post("auth/logout");
      await resetUser();
    } catch (error) {
      console.error("Error in logout", error);
    }
  }
}

export default AuthService;
