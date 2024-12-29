import { createBrowserRouter } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import AccountPage from "./pages/AccountPage";
import MyMessagePage from "./pages/MyArticlePage";
import SelectDecoPage from "./pages/SelectDecoPage";
import WritePage from "./pages/WritePage";
import CallBack from "./pages/CallBack";
import ErrorPage from "./pages/ErrorPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <LoginPage />,
  },
  {
    path: "/:address",
    element: <MainPage />,
  },
  {
    path: "/account",
    element: <AccountPage />,
  },
  {
    path: "/my-message",
    element: <MyMessagePage />,
  },
  {
    path: "/select-deco",
    element: <SelectDecoPage />,
  },
  {
    path: "/write",
    element: <WritePage />,
  },
  {
    path: "/callback",
    element: <CallBack />,
  },
  {
    path: "*",
    element: <ErrorPage />,
  },
]);

export default router;
