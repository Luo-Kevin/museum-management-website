import React, { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Home from "../pages/Home";
import { Navigation } from "../layouts/Navigation";
import { Login } from "../pages/Login";
import { Signup } from "../pages/Signup";
import { LoginContext } from "../Contexts/LoginContext";
import { Schedule } from "../pages/Schedule";
import { ViewEmployees } from "../pages/ViewEmployees";

export function Main() {
  const [loggedIn, setLoggedIn] = useState(
    sessionStorage.getItem("loggedIn") ?? false
  );
  const [userRole, setUserRole] = useState(
    sessionStorage.getItem("userRole") ?? ""
  );
  return (
    <LoginContext.Provider
      value={{ loggedIn, setLoggedIn, userRole, setUserRole }}
    >
      <BrowserRouter>
        <Navigation />
        <Routes>
          <Route path="/" index element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/schedule/:id" element={<Schedule />} />
          <Route path="/employees" element={<ViewEmployees />} />s
          {/* <Route
            path="/employee"
            element={
              userRole === "employee" ? <Employee /> : <Navigate to="/login" />
            }
          /> */}
          {/* <Route
            path="/manager"
            element={
              userRole === "employee" ? <Manager /> : <Navigate to="/login" />
            }
          /> */}
        </Routes>
      </BrowserRouter>
    </LoginContext.Provider>
  );
}
