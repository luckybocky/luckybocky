import React, { useEffect, useState } from "react";
import { Outlet, useNavigate } from "react-router-dom";

import AuthService from "../api/AuthService.ts";

const CheckUser = () => {
  const navigate = useNavigate();

  const [initialized, setInitialized] = useState(false); // 초기화 상태

  const init = async () => {
    if ((await AuthService.check()) === 1) navigate("/join");
    setInitialized(true);
  };

  useEffect(() => {
    init();
  }, []);

  if (initialized) {
    return <Outlet />;
  }
};

export default CheckUser;
