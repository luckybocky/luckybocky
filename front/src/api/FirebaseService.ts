import { messaging, getToken, onMessage } from "../components/firebase-config";
import ApiClient from "./ApiClient.ts";

class FirebaseService {
  /**
   * 푸시 알림 권한 요청 및 토큰 획득
   * @returns {Promise<boolean | null>} FCM 토큰, 실패 시 null 반환
   */
  static async requestToken(): Promise<boolean | null> {
    try {
      const permission = await Notification.requestPermission();

      if (permission === "granted") {
        const token = await getToken(messaging, {
          vapidKey: process.env.REACT_APP_FIREBASE_VAPID_KEY,
        });

        await this.sendToken(token);

        return true; // 토큰 반환
      } else {
        console.log("Notification permission denied");
        return false;
      }
    } catch (error) {
      console.log("Error getting FCM token:", error);
      return false;
    }
  }

  /**
   * 메시지 수신 처리
   * @param {Function} [callback] - 알림 클릭 시 호출될 콜백 함수
   * @returns {Promise<void>}
   */
  static async setupOnMessageListener(
    callback?: (notificationOptions: NotificationOptions) => void
  ): Promise<void> {
    try {
      onMessage(messaging, (payload) => {
        if (payload && payload.data) {
          const notificationTitle = payload.data.title;
          const notificationOptions = {
            body: payload?.data?.body,
            data: {
              url: payload?.data?.url, // 데이터로 URL을 넘겨받는다
            },
          };

          if (Notification.permission === "granted") {
            const notification = new Notification(
              notificationTitle,
              notificationOptions
            );

            notification.addEventListener("click", (event) => {
              if (event && event.target instanceof Notification) {
                event.preventDefault(); // 기본 클릭 동작을 방지
                const url = event.target.data.url; // 백엔드에서 보낸 절대 URL
                window.open(url, "_blank"); // 새 탭에서 URL 열기
                notification.close();
              }
            });
          }

          if (callback) {
            callback(notificationOptions); // 콜백 호출
          }
        } else {
          console.log("푸시 수신 오류");
        }
      });
    } catch (error) {
      console.log("푸시 수신 오류", error);
    }
  }

  /**
   * FCM 토큰을 백엔드로 전송
   * @param {string} token - FCM 토큰
   * @returns {Promise<void>}
   */
  static async sendToken(token: string): Promise<void> {
    const myKey = { firebaseKey: token };
    try {
      await ApiClient.put(`/auth/firebase`, myKey);
    } catch (error) {
      console.error("Error sending token to backend", error);
    }
  }
}

export default FirebaseService;
