import React, { useEffect } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { checkLogin } from "../api/AuthApi";

const CheckUser = () => {
  const navigate = useNavigate();

  const init = async () => {
    if ((await checkLogin()) === 1) navigate("/join");
  };

  useEffect(() => {
    init();
  }, []);

  return <Outlet />;
};

export default CheckUser;
