import { create } from "zustand";

const AuthStore = create((set) => ({
  user: {
    userNickname: "",
    alarmStatus: false,
    fortuneVisibility: false,
    createdAt: "",
    address: "",
  },

  setUser: (user) => set({ user }),
  resetUser: () =>
    set({
      user: {
        userNickname: "",
        alarmStatus: false,
        fortuneVisibility: false,
        createdAt: "",
        address: "",
      },
    }),
}));

export default AuthStore;
