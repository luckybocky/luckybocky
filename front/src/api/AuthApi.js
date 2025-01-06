import ApiClient from "./ApiClient";
import AuthStore from "../store/AuthStore";
import { myPocketAddress, createPocket } from "./PocketApi";

export const checkLogin = async () => {
  console.log("login check");
  try {
    const setUser = AuthStore.getState().setUser;

    const response = await ApiClient.get("auth/user");
    const data = response.data;

    let address = "";

    try {
      address = await myPocketAddress();
    } catch (error) {
      address = await createPocket();
    }

    setUser({
      userNickname: data.userNickname,
      alarmStatus: data.alarmStatus,
      fortuneVisibility: data.fortuneVisibility,
      createdAt: data.createdAt,
      address: address,
    });

    //닉네임이 없는 경우(설정 페이지로 이동)
    if (data.userNickname == null) {
      return 1;
    }

    return 2;
  } catch (error) {
    console.error("check login error", error);
  }
};

export const callback = async (code) => {
  if (code) {
    try {
      await ApiClient.get(`auth/callback?code=${code}`);

      const result = await checkLogin();

      return result;
    } catch (error) {
      console.error("Error during Kakao login", error);
    }
  }
  return false;
};

export const updateUser = async () => {
  const user = AuthStore.getState().user;

  try {
    await ApiClient.put("auth/user", user);
  } catch (error) {
    console.error("update error", error);
  }
};

export const logout = async () => {
  try {
    await ApiClient.post("auth/logout");
  } catch (error) {
    console.error("logout error", error);
  }
};
