// src/pages/Push.js
import React, { useEffect, useState } from "react";
import { messaging, getToken, onMessage } from "../components/firebase-config"; // firebase-config에서 getToken과 onMessage 가져오기

const Push = () => {
  const [token, setToken] = useState("");

  // 페이지가 로드될 때 푸시 토큰을 받아옵니다.
  useEffect(() => {
    // 푸시 알림 권한 요청 및 토큰 획득
    const getFcmToken = async () => {
      try {
        // 사용자에게 알림 권한 요청
        const permission = await Notification.requestPermission();
        if (permission === "granted") {
          // 권한이 허용되면 푸시 토큰을 가져옵니다.
          const token = await getToken(messaging, {
            // vapidKey: process.env.REACT_APP_FIREBASE_VAPID_KEY, // VAPID 키를 넣어주세요 (Firebase 콘솔에서 설정)
            vapidKey: process.env.REACT_APP_FIREBASE_VAPID_KEY, // VAPID 키를 넣어주세요 (Firebase 콘솔에서 설정)
          });
          console.log("FCM Token:", token);
          setToken(token);
        } else {
          console.error("Notification permission denied");
        }
      } catch (error) {
        console.error("Error getting FCM token:", error);
      }
    };

    getFcmToken();

    // 메시지 수신 시 호출되는 함수 (포그라운드 상태에서 알림 받기)
    onMessage(messaging, (payload) => {
      console.log("Message received. ", payload);
      console.log("Message received notification ", payload.notification);
      console.log("Message received data ", payload.data);
      // 푸시 알림을 화면에 표시하는 로직 (예: UI 업데이트, 알림 띄우기 등)
      const notificationTitle = payload.notification.title;
      const notificationOptions = {
        body: payload.notification.body,
      };
      if (Notification.permission === "granted") {
        new Notification(notificationTitle, notificationOptions);
      }
    });
  }, []);

  return (
    <div>
      <h1>Push Notifications</h1>
      {token ? (
        <div>
          <p>FCM Token: {token}</p>
          {/* 이곳에 토큰을 백엔드 서버로 보내는 로직을 추가할 수 있습니다 */}
        </div>
      ) : (
        <p>FCM 토큰을 받아오는 중입니다...</p>
      )}
    </div>
  );
};

export default Push;
