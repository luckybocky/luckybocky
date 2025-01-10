// src/components/firebase-config.js
import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

const firebaseConfig = {
  apiKey: process.env.REACT_APP_FIREBASE_API_KEY,
  authDomain: process.env.REACT_APP_FIREBASE_AUTH_DONMAIN,
  projectId: process.env.REACT_APP_FIREBASE_PROJECT_ID,
  storageBucket: process.env.REACT_APP_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.REACT_APP_FIREBASE_MESSAING_SENDER_ID,
  appId: process.env.REACT_APP_FIREBASE_ID,
  measurementId: process.env.REACT_APP_FIREBASE_MEASUREMENT_ID,
};
let messaging;
try {
  // Firebase 앱 초기화
  const app = initializeApp(firebaseConfig);

  // Firebase Messaging 인스턴스 생성
  messaging = getMessaging(app);
} catch (error) {
  console.error("Firebase 초기화 중 오류 발생:", error);
  // 오류 발생 시 사용자에게 알림
  alert("Firebase 초기화에 실패했습니다. 잠시 후 다시 시도해 주세요.");
}
export { messaging, getToken, onMessage };
