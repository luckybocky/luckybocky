import { create } from "zustand";

const AuthStore = create((set) => ({
  user: {
    userNickname: null,
    alarmStatus: null,
    fortuneVisibility: null,
    createdAt: null,
  },
  setUser: (user) => set({ user }),
  resetUser: () =>
    set({
      user: {
        userNickname: null,
        alarmStatus: null,
        fortuneVisibility: null,
        createdAt: null,
      },
    }),
}));

export default AuthStore;
