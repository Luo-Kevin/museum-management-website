import React, { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { Typography } from "@mui/material";
import { LoginContext } from "../Contexts/LoginContext";
import Home from "../pages/Home";
import { Navigation } from "../layouts/Navigation";
import { Login } from "../pages/Login";
import { Signup } from "../pages/Signup";
import { Profile } from "../pages/Profile";
import { ViewEmployees } from "../pages/ViewEmployees";
import ArtworkBrowsing from "../pages/ArtworkBrowsing";
import ArtworkDetails from "../pages/ArtworkDetails";
import EmployeeCreation from "../pages/EmployeeCreation";
import TicketViewing from "../pages/TicketViewing";
import ViewSchedule from "../pages/ViewSchedule";
import { MuseumInfo } from "../pages/MuseumInfo";
import { ViewMuseumOpeningHours } from "../pages/ViewMuseumOpeningHours";
import { Footer } from "../layouts/Footer";

/**
 * Function that routes the user to the correct page depending on the url
 * @author Kevin
 */
export function Main() {
  //Setting necessary information to be used in other components
  const [loggedIn, setLoggedIn] = useState(
    localStorage.getItem("loggedIn") ?? false
  );
  const [userRole, setUserRole] = useState(
    localStorage.getItem("userRole") ?? ""
  );

  const [userName, setUserName] = useState(
    localStorage.getItem("userName") ?? ""
  );

  const [userEmail, setUserEmail] = useState(
    localStorage.getItem("userEmail") ?? ""
  );

  const [museum, setMuseum] = useState();

  const [userId, setUserId] = useState(localStorage.getItem("userId") ?? "");

  return (
    <LoginContext.Provider
      value={{
        loggedIn,
        setLoggedIn,
        userRole,
        setUserRole,
        userName,
        setUserName,
        userEmail,
        setUserEmail,
        userId,
        setUserId,
        museum,
        setMuseum,
      }}
    >
      <BrowserRouter>
        <Navigation />
        <Routes>
          <Route path="/" index element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route
            path="/profile"
            element={loggedIn ? <Profile /> : <Navigate to="/login" />}
          />
          <Route
            path="/employee"
            element={
              loggedIn && userRole === "manager" ? (
                <ViewEmployees />
              ) : (
                <Typography
                  variant="h3"
                  style={{ margin: "auto", padding: "auto" }}
                >
                  You are not authorized to view this page
                </Typography>
              )
            }
          />
          <Route path="/employee/schedule">
            <Route
              path=":id"
              element={
                loggedIn &&
                (userRole === "employee" || userRole === "manager") ? (
                  <ViewSchedule />
                ) : (
                  <Typography
                    variant="h3"
                    style={{ margin: "auto", padding: "auto" }}
                  >
                    You are not authorized to view this page
                  </Typography>
                )
              }
            />
            <Route
              path=""
              element={
                loggedIn &&
                (userRole === "employee" || userRole === "manager") ? (
                  <ViewSchedule />
                ) : (
                  <Typography
                    variant="h3"
                    style={{ margin: "auto", padding: "auto" }}
                  >
                    You are not authorized to view this page
                  </Typography>
                )
              }
            />
          </Route>
          <Route
            path="/employee/create"
            element={
              loggedIn && userRole === "manager" ? (
                <EmployeeCreation />
              ) : (
                <Typography
                  variant="h3"
                  style={{ margin: "auto", padding: "auto" }}
                >
                  You are not authorized to view this page
                </Typography>
              )
            }
          />
          <Route path="/museum/info" element={<MuseumInfo />} />
          <Route
            path="/museum/info/schedule/:id"
            element={<ViewMuseumOpeningHours />}
          />
          <Route path="/browse/room/:roomId" element={<ArtworkBrowsing />} />
          <Route
            path="/browse/artwork/:artworkId"
            element={<ArtworkDetails />}
          />
          <Route path="/ticket" element={<TicketViewing />} />
          <Route
            path="/loan"
            element={loggedIn ? <Home /> : <Navigate to="/login" />}
          />
        </Routes>
      </BrowserRouter>
      <Footer />
    </LoginContext.Provider>
  );
}
