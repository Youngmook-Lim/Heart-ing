import React from "react";
import ReactDOM from "react-dom/client";
import { RecoilRoot } from "recoil";
import "./styles/index.css";
import App from "./App";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <RecoilRoot>
    <div className="mx-auto w-full md:max-w-sm lg:min-h-screen">
      <App />
    </div>
  </RecoilRoot>
);
