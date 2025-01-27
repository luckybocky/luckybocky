import { createBrowserRouter } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import MainPage from "./pages/MainPage";
import AccountPage from "./pages/AccountPage";
import MyMessagePage from "./pages/MyArticlePage";
// import SelectDecoPage from "./pages/SelectDecoPage";
import WritePage from "./pages/WritePage";
import CallBack from "./pages/CallBack";
import ErrorPage from "./pages/ErrorPage";
import JoinPage from "./pages/JoinPage";
import CheckUser from "./components/CheckUser";
import QnaBoardPage from "./pages/QnaBoardPage";
import QnaDetailPage from "./pages/QnaDetailPage";
import ShareArticlePage from "./pages/shareArticlePage.tsx";

const options = {
  future: {
    v7_relativeSplatPath: true,
    v7_fetcherPersist: true,
    v7_normalizeFormMethod: true,
    v7_partialHydration: true,
    v7_skipActionErrorRevalidation: true,
  },
}

const router = createBrowserRouter([
  {
    element: <CheckUser />,
    children: [
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
      // {
      //   path: "/select-deco",
      //   element: <SelectDecoPage />,
      // },
      {
        path: "/write",
        element: <WritePage />,
      },
      {
        path: "/callback",
        element: <CallBack />,
      },
      {
        path: "/error",
        element: <ErrorPage />,
      },
      {
        path: "/join",
        element: <JoinPage />,
      },
      {
        path: "/qna",
        element: <QnaBoardPage />,
      },
      {
        path: "/qna/:id",
        element: <QnaDetailPage />,
      },
      {
        path: "/share/:id",
        element: <ShareArticlePage />,
      },
    ],
  },
], options);

export default router;
