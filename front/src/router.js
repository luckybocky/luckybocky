import { createBrowserRouter } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import AccountPage from "./pages/AccountPage";
import MyMessagePage from "./pages/SendMessagePage";
import SelectDecoPage from "./pages/SelectDecoPage";
import WritePage from "./pages/WritePage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <LoginPage />,
  },
  {
    path: "/main",
    element: <MainPage />,
  },
  {
    path: "/account",
    element: <AccountPage />,
  },
  {
    path: "/send-message",
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
]);

export default router;
