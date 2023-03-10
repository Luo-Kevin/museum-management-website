import React, { useContext, useEffect, useState } from "react";
import {
  Button,
  AppBar,
  Box,
  Toolbar,
  Typography,
  Container,
  Menu,
  MenuItem,
} from "@mui/material";
import { Link } from "react-router-dom";
import axios from "axios";
import { LoginContext } from "../Contexts/LoginContext";
import { useNavigate } from "react-router-dom";
import RQ_logo from "../assets/RQ_logo.svg";

const generalPage = ["Ticket", "Loan"];

/**
 * Navigation bar
 * @returns Navigation bar
 * @author Kevin
 */

export function Navigation() {
  const { loggedIn, setLoggedIn } = useContext(LoginContext);
  const [rooms, setRooms] = useState([]);
  const [anchorElNav, setAnchorElNav] = useState(null);
  const [anchorElUser, setAnchorElUser] = useState(null);
  const { setMuseum } = useContext(LoginContext);

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };

  useEffect(() => {
    axios
      .get("/api/room")
      .then((response) => {
        let allRooms = response.data;
        setMuseum(allRooms[0].museum);
        localStorage.setItem("museum", JSON.stringify(allRooms[0].museum));
        allRooms.sort((a, b) => {
          if (a.roomName === "Storage") {
            return 1;
          } else if (b.roomName === "Storage") {
            return -1;
          } else {
            return 0;
          }
        });
        setRooms(allRooms);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const navigate = useNavigate();

  /**
   * Logs user out and redirects to home page
   * @author Kevin
   */

  const handleLogout = () => {
    axios
      .post("/api/auth/logout")
      .then(function (response) {
        if (response.status === 200) {
          setLoggedIn(false);
          localStorage.removeItem("userName");
          localStorage.removeItem("userEmail");
          localStorage.removeItem("userRole");
          localStorage.removeItem("userId");
          localStorage.setItem("loggedIn", false);
          navigate("/");
        }
      })
      .catch(function (error) {
        setLoggedIn(true);
      });
  };

  /**
   * Navigation bar when user is not logged in
   * @author Kevin
   */

  const notSignedInButton = () => {
    return (
      <>
        <Box>
          <Button component={Link} style={{ color: "black" }} to="/login">
            Login
          </Button>
          <Button component={Link} style={{ color: "black" }} to="/signup">
            Sign up
          </Button>
        </Box>
      </>
    );
  };

  /**
   * Navigation bar when user is  logged in
   * @author Kevin
   */

  const signedInButton = () => {
    return (
      <>
        <Box>
          <Button component={Link} to="/profile" style={{ color: "black" }}>
            Profile
          </Button>
          <Button style={{ color: "black" }} onClick={handleLogout}>
            Logout
          </Button>
        </Box>
      </>
    );
  };

  return (
    <AppBar position="static" style={{ background: "#fff" }}>
      <Container maxWidth="xl" style={{ position: "relative" }}>
        <Toolbar disableGutters>
          <Typography
            variant="h6"
            noWrap
            component={Link}
            to="/"
            sx={{
              mr: 2,
              display: { xs: "none", md: "flex" },
              fontWeight: 700,
              letterSpacing: ".3rem",
              color: "#000",
              textDecoration: "none",
            }}
          >
            <img src={RQ_logo} alt="RQ logo"></img> {/*RQ Museum logo*/}
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
            <Button
              style={{ color: "black" }}
              onClick={handleOpenUserMenu}
              sx={{ p: 0 }}
            >
              Rooms
            </Button>
            <Menu
              sx={{ mt: "45px" }}
              id="menu-appbar"
              anchorEl={anchorElUser}
              anchorOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              transformOrigin={{
                vertical: "top",
                horizontal: "right",
              }}
              open={Boolean(anchorElUser)}
              onClose={handleCloseUserMenu}
            >
              {rooms.map((room) => (
                <Link
                  style={{ color: "black", textDecoration: "none" }}
                  to={`/browse/room/${room.roomId}`}
                  key={room.roomId}
                >
                  <MenuItem key={room.roomId}>{room.roomName}</MenuItem>
                </Link>
              ))}
              <Link
                style={{ color: "black", textDecoration: "none" }}
                to="/browse/room/all"
              >
                <MenuItem>All Rooms</MenuItem>
              </Link>
            </Menu>
            {generalPage.map((page) => (
              <Button
                key={page}
                component={Link}
                to={page === "Home" ? "/" : `/${page.toLowerCase()}`}
                sx={{ my: 2, color: "black", display: "block" }}
              >
                {page}
              </Button>
            ))}
          </Box>
          {loggedIn ? signedInButton() : notSignedInButton()}
        </Toolbar>
      </Container>
    </AppBar>
  );
}
