/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        sans: ["JalnanW,Jalnan"],
      },
      textShadow: {
        outline:
          "1px 1px 0px rgba(0, 0, 0, 0.3), 0px 0px 0px #000, 0px 0px 0px #000, 0px 0px 0px #000",
      },
    },
  },
  plugins: [require("tailwindcss-textshadow")],
};
