// firebase-messaging-sw.js

importScripts("https://www.gstatic.com/firebasejs/8.10.1/firebase-app.js");
importScripts(
  "https://www.gstatic.com/firebasejs/8.10.1/firebase-messaging.js"
);

fetch("/firebase-config.json")
  .then((response) => response.json())
  .then((config) => {
    console.log("서비스워커");
    console.log(config);
    firebase.initializeApp(config);

    const messaging = firebase.messaging();
    // 백그라운드 메시지 처리
    messaging.onBackgroundMessage((payload) => {
      console.log(
        "[firebase-messaging-sw.js] Received background message ",
        payload
      );
      const notificationTitle =
        payload.notification?.title + "back" || "Default Title";
      const notificationOptions = {
        body: payload.notification?.body || "Default body",
        icon: payload.notification?.icon || "/default-icon.png",
      };

      return self.registration.showNotification(
        notificationTitle,
        notificationOptions
      );
    });
  });
