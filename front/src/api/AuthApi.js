import ApiClient from "./ApiClient";
import AuthStore from "../store/AuthStore";

export const checkLogin = async () => {
  try {
    const setUser = AuthStore.getState().setUser;

    const response = await ApiClient.get("auth/user");
    const data = response.data;
    setUser({
      userNickname: data.userNickname,
      alarmStatus: data.alarmStatus,
      fortuneVisibility: data.fortuneVisibility,
      createdAt: data.createdAt,
    });
  } catch {
    //서버 오류 or 비로그인
  }
};
