"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
const loginButton = document.getElementById("login-button");
const registerButton = document.getElementById("register-button");
const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
function handleLogin() {
    return __awaiter(this, void 0, void 0, function* () {
        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();
        if (!username || !password) {
            alert("Please fill in both username and password!");
            return;
        }
        try {
            // Get - login kezelesre
            const response = yield fetch(`http://localhost:8080/Bibliospring/api/login?username=${username}&password=${password}`, {
                method: "GET",
                headers: {
                    "Accept": "text/plain"
                }
            });
            if (response.ok) {
                const result = yield response.text();
                if (result === "true") {
                    window.location.href = "main.html";
                }
                else {
                    alert("Invalid username or password!");
                }
            }
        }
        catch (error) {
            console.error("Error during login:", error);
            alert("An error occurred during login.");
        }
    });
}
// Post - regisztralas
function handleRegister() {
    return __awaiter(this, void 0, void 0, function* () {
        const username = usernameInput.value.trim();
        const password = passwordInput.value.trim();
        if (!username || !password) {
            alert("Please fill in both username and password!");
            return;
        }
        //URL encoded
        const formData = new URLSearchParams();
        formData.append("username", username);
        formData.append("password", password);
        try {
            const response = yield fetch("http://localhost:8080/Bibliospring/api/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: formData.toString()
            });
            if (response.ok) {
                alert("Registration successful! You can now log in.");
            }
            else {
                alert(`Registration failed: user already exists`);
            }
        }
        catch (error) {
            console.error("Error during registration:", error);
            alert("An error occurred during registration.");
        }
    });
}
loginButton.addEventListener("click", handleLogin);
registerButton.addEventListener("click", handleRegister);
