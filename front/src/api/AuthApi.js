import ApiClient from "./ApiClient";
import AuthStore from "../store/AuthStore";
import { myPocketAddress } from "./PocketApi";

export const checkLogin = async () => {
  console.log("login check");
  try {
    const setUser = AuthStore.getState().setUser;

    const response = await ApiClient.get("auth/user");
    const data = response.data;
    const address = await myPocketAddress();

    setUser({
      userNickname: data.userNickname,
      alarmStatus: data.alarmStatus,
      fortuneVisibility: data.fortuneVisibility,
      createdAt: data.createdAt,
      address: address,
    });
  } catch {
    //서버 오류 or 비로그인
  }
};

export const callback = async (code) => {
  if (code) {
    try {
      const response = await ApiClient.get(`auth/callback?code=${code}`);

      await checkLogin();
      return true;
    } catch (error) {
      console.error("Error during Kakao login", error);
      return false;
    }
  }
  return false;
};

export const updateUser = async () => {
  const user = AuthStore.getState().user;

  try {
    const response = await ApiClient.put("auth/user", user);
  } catch (error) {
    console.error("update error", error);
  }
};
