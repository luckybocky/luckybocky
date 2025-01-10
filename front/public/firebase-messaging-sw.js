// firebase-messaging-sw.js

importScripts("https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js");
importScripts(
  "https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js"
);

fetch("/firebase-config.json")
  .then((response) => response.json())
  .then((config) => {
    try {
      firebase.initializeApp(config);

      const messaging = firebase.messaging();
      // 백그라운드 메시지 처리
      messaging.onBackgroundMessage((payload) => {
        console.log("backgorund push");
        const notificationTitle =
          "백그라운드 푸시" + payload.notification.title;
        const notificationOptions = {
          body: payload.notification.body,
          // 알림 클릭 시 이동할 URL 설정
          data: {
            url: payload.data.url, // 데이터로 URL을 넘겨받는다
          },
        };

        // 푸시 알림 표시
        self.registration.showNotification(
          notificationTitle,
          notificationOptions
        );
      });
    } catch (error) {
      console.log("서비스워커 파이어베이스 초기화 오류");
    }
  });
// 클릭 이벤트 처리 (알림 클릭 시)
self.addEventListener("notificationclick", (event) => {
  event.notification.close();

  const url = event.notification.data.url; // 데이터에서 URL 가져오기
  if (url) {
    clients.openWindow(url); // 해당 URL로 새 탭 열기
  }
});
