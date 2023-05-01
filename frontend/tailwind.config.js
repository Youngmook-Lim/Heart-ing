/** @type {import('tailwindcss').Config} */

const plugin = require("tailwindcss/plugin");

module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      sans: ["NeoDunggeunmo", "Arial", "sans-serif"],
      colors: {
        hrtColorOutline: "#43316b",
        hrtColorOutline100: "#1d005c",
        hrtColorOutline200: "#260b60",
        hrtColorOutline300: "#2f1763",
        hrtColorOutline400: "#392467",
        hrtColorOutline500: "#43316b",
        hrtColorOutline600: "#634f90",
        hrtColorOutline700: "#8772b5",
        hrtColorOutline800: "#b09cda",
        hrtColorOutline900: "#dcccff",
        hrtColorPurple: "#d5a7ea",
        hrtColorPink: "#fb8bb0",
        hrtColorYellow: "#fff0b2",
        hrtColorRed: "#db443a",
        hrtColorBackground: "#dbf0ff",
        hrtColorGray: "#dbdbdb",
        hrtColorLightPurple: "#F5F4F7",
        hrtColorLightPink: "#F9ECF5",
        hrtColorNewRed: "#DB443A",
        hrtColorWhiteTrans: "rgba(255,255,255,0.8)",
      },
    },
  },
  plugins: [
    plugin(function ({ addComponents }) {
      addComponents({
        ".modal": {
          // borderWidth: "0.25rem",
          // borderRadius: "0.125rem",
          backgroundColor: "#fff",
          // outlineStyle: "solid",
          // outlineWidth: "4px",
          boxShadow: "0 0 0 0.25rem #43316b",
        },
        ".modal-header": {
          color: "#fff",
          height: "2.5rem",
          fontSize: "1.25rem" /* 16px */,
          lineHeight: "2.5rem",
          textAlign: "left",
          paddingLeft: "0.5rem",
          paddingRight: "0.125rem",
          // borderBottomWidth: "0.25rem",
          // outlineStyle: "solid",
          // boxSizing: "border-box",
          boxShadow: "0 0 0 0.25rem #43316b",
          // margin: "-0.25rem",
        },
        ".modal-button": {
          borderWidth: "2px",
          borderRadius: "0.25rem",
          borderColor: "#43316b",
          width: "50%",
          height: "2.5rem" /* 40px */,
          lineHeight: "2.25rem",
          fontSize: "1rem" /* 16px */,
        },
        ".heartBoard": {
          backgroundColor: "#fff",
          boxShadow: "0 0 0 0.25rem #fb8bb0",
        },
        ".heartBoard-header": {
          color: "#fff",
          height: "2.5rem",
          fontSize: "1.25rem" /* 16px */,
          lineHeight: "2.5rem",
          textAlign: "left",
          paddingLeft: "0.5rem",
          paddingRight: "0.5rem",
          boxShadow: "0 0 0 0.25rem #fb8bb0",
        },
      });
    }),
  ],
};
