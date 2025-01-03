import { messaging, getToken, onMessage } from "../components/firebase-config";
import ApiClient from "./ApiClient";

// 푸시 알림 권한 요청 및 토큰 획득
export const requestFcmToken = async () => {
  try {
    const permission = await Notification.requestPermission();
    if (permission === "granted") {
      const token = await getToken(messaging, {
        vapidKey: process.env.REACT_APP_FIREBASE_VAPID_KEY,
      });
      console.log("FCM success");
      console.log("FCM Token:", token);

      // setupOnMessageListener();

      await sendTokenToBackend(token);

      return token; // 토큰 반환
    } else {
      console.error("Notification permission denied");
      return null;
    }
  } catch (error) {
    console.error("Error getting FCM token:", error);
    return null;
  }
};

// 메시지 수신 처리
export const setupOnMessageListener = (callback) => {
  onMessage(messaging, (payload) => {
    console.log("Message received: ", payload);
    console.log("target Url: ", payload.data.url);
    const notificationTitle = payload.notification.title;
    const notificationOptions = {
      body: payload.notification.body,
      // 알림 클릭 시 이동할 URL 설정
      data: {
        url: payload.data.url, // 데이터로 URL을 넘겨받는다
      },
    };

    // const notificationData = {
    //   title: payload.notification?.title || "알림",
    //   body: payload.notification?.body || "내용 없음",
    // };

    // 푸시 알림 화면 표시 (선택적)
    if (Notification.permission === "granted") {
      const notification = new Notification(
        notificationTitle,
        notificationOptions
      );

      notification.addEventListener("click", (event) => {
        event.preventDefault(); // 기본 클릭 동작을 방지

        // 알림 데이터에서 URL을 가져옴
        const url = event.target.data.url; // 백엔드에서 보낸 절대 URL

        // 해당 URL로 이동
        window.open(url, "_blank"); // 새 탭에서 URL 열기

        notification.close();
      });
    }

    if (callback) {
      callback(notificationOptions); // 콜백 호출
    }
  });
};

export const sendTokenToBackend = async (token) => {
  const myKey = { firebaseKey: token };
  const response = await ApiClient.put(`/auth/firebase`, myKey);
  console.log(response);
  return response.data.data;
};

export const sendArticlePush = async (pocketSeq) => {
  const payload = {
    type: "article",
    contentSeq: pocketSeq,
    url: `${window.location.origin}${window.location.pathname}`,
  };
  const response = await ApiClient.post(`/push`, payload);
  console.log(response);
  return response.data.data;
};

export const sendCommentPush = async (articleSeq) => {
  const payload = {
    type: "comment",
    contentSeq: articleSeq,
    url: `${window.location.origin}${window.location.pathname}`,
  };
  const response = await ApiClient.post(`/push`, payload);
  console.log(response);
  return response.data.data;
};
