import React from "react";
import "./App.css";
import { RouterProvider } from "react-router-dom";
import router from "./router";
import Footer from "./components/Footer";

const App = () => {
  return (
    <div className="app">
      {/* <div className="language-selector">
        <button className="language-button">🌐</button>
      </div> */}
      <RouterProvider router={router} />
      <Footer></Footer>
    </div>
  );
};

export default App;
