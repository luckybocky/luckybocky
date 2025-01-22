import ApiClient from "./ApiClient";
import AuthStore from "../store/AuthStore";
import PocketService from "./PocketService.ts";
import FirebaseService from "./FirebaseService.ts";

class AuthService {
  /**
   * 유저 정보 가져오기
   * @returns {Promise<number>} 0: 로그인 안됨, 1: 닉네임 없음, 2: 로그인 됨
   */
  static async check(): Promise<number | undefined> {
    try {
      const setUser = AuthStore.getState().setUser;

      const response = await ApiClient.get("auth/user");
      const data = response.data.data;

      if (data.login) {
        const address = await PocketService.getMyAddress();

        setUser({
          userNickname: data.userInfo.userNickname,
          alarmStatus: data.userInfo.alarmStatus,
          fortuneVisibility: data.userInfo.fortuneVisibility,
          createdAt: data.userInfo.createdAt,
          address: address,
        });

        //닉네임이 없는 경우(설정 페이지로 이동)
        if (data.userInfo.userNickname === null) {
          return 1;
        }

        return 2;
      }

      return 0;
    } catch (error) {
      console.error("Error in check", error);
    }
  }

  /**
   * 콜백 후 로그인,유저 정보 받아오기
   * @param {string} code 로그인 코드
   * @returns {Promise<number>} 0: 로그인 안됨, 1: 닉네임 없음, 2: 로그인 됨
   */
  static async login(code: string): Promise<number | undefined> {
    if (code) {
      try {
        await ApiClient.get(`auth/callback?code=${code}`);

        const result = await AuthService.check();

        try {
          const user = AuthStore.getState().user;
          const setUser = AuthStore.getState().setUser;

          //알람 허용인 경우
          if (user.alarmStatus) {
            const permission = await FirebaseService.requestToken();

            if (!permission) {
              alert("브라우저 설정에 따라 알림이 거부됩니다.");
              setUser({
                ...user,
                alarmStatus: !user.alarmStatus,
              });

              await this.update();
            }
          }
        } catch (error) {
          console.error("알림 권한 변경 실패", error);
        }

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
