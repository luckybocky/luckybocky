import { messaging, getToken, onMessage } from "../components/firebase-config";
import ApiClient from "./ApiClient";

// iOS 환경 감지 함수
const isIos = () => {
  // const ua = navigator.userAgent;
  // const isIosDevice = /iPhone|iPad|iPod/i.test(ua);
  // const isKakaoWebView = /KAKAOTALK/i.test(ua);
  // return isIosDevice && isKakaoWebView;
  // return isIosDevice;
};

// 푸시 알림 권한 요청 및 토큰 획득
export const requestFcmToken = async () => {
  // console.log("requestFcmToken");
  // console.log(navigator.userAgent);
  // console.log(isIos());
  //   if (isIos()) {
  //     console.log("iOS WebView에서는 FCM 토큰 요청을 생략합니다.");
  //     return null;
  //   }
  //   try {
  //     const permission = await Notification.requestPermission();
  //     if (permission === "granted") {
  //       const token = await getToken(messaging, {
  //         vapidKey: process.env.REACT_APP_FIREBASE_VAPID_KEY,
  //       });
  //       console.log("FCM success");
  //       // console.log("FCM Token:", token);
  //       // setupOnMessageListener();
  //       await sendTokenToBackend(token);
  //       return token; // 토큰 반환
  //     } else {
  //       console.log("Notification permission denied");
  //       return null;
  //     }
  //   } catch (error) {
  //     console.log("Error getting FCM token:", error);
  //     return null;
  //   }
  // };
  // // 메시지 수신 처리
  // export const setupOnMessageListener = (callback) => {
  //   if (isIos()) {
  //     console.log("iOS WebView에서는 FCM 토큰 요청을 생략합니다.");
  //     return null;
  //   }
  //   onMessage(messaging, (payload) => {
  //     // console.log("Message received: ", payload);
  //     // console.log("target Url: ", payload.data.url);
  //     const notificationTitle = payload.notification.title;
  //     const notificationOptions = {
  //       body: payload.notification.body,
  //       // 알림 클릭 시 이동할 URL 설정
  //       data: {
  //         url: payload.data.url, // 데이터로 URL을 넘겨받는다
  //       },
  //     };
  //     // const notificationData = {
  //     //   title: payload.notification?.title || "알림",
  //     //   body: payload.notification?.body || "내용 없음",
  //     // };
  //     // 푸시 알림 화면 표시 (선택적)
  //     if (Notification.permission === "granted") {
  //       const notification = new Notification(
  //         notificationTitle,
  //         notificationOptions
  //       );
  //       notification.addEventListener("click", (event) => {
  //         event.preventDefault(); // 기본 클릭 동작을 방지
  //         // 알림 데이터에서 URL을 가져옴
  //         const url = event.target.data.url; // 백엔드에서 보낸 절대 URL
  //         // 해당 URL로 이동
  //         window.open(url, "_blank"); // 새 탭에서 URL 열기
  //         notification.close();
  //       });
  //     }
  //     if (callback) {
  //       callback(notificationOptions); // 콜백 호출
  //     }
  //   });
};

export const sendTokenToBackend = async (token) => {
  const myKey = { firebaseKey: token };
  try {
    await ApiClient.put(`/auth/firebase`, myKey);
  } catch (error) {
    console.error("Error sending token to backend", error);
  }
};

export const sendArticlePush = async (pocketSeq) => {
  const payload = {
    type: "article",
    contentSeq: pocketSeq,
    url: `${window.location.origin}${window.location.pathname}`,
  };
  try {
    await ApiClient.post(`/push`, payload);
  } catch (error) {
    console.error("push error", error);
  }
};

export const sendCommentPush = async (articleSeq) => {
  const payload = {
    type: "comment",
    contentSeq: articleSeq,
    url: `${window.location.origin}${window.location.pathname}`,
  };
  try {
    await ApiClient.post(`/push`, payload);
  } catch (error) {
    console.error("push error", error);
  }
};
export const sendLog = async (msg) => {
  console.log(msg);
  const payload = {
    message: msg,
  };
  try {
    await ApiClient.post(`/push/log`, payload);
  } catch (error) {
    console.error("log error", error);
  }
};
