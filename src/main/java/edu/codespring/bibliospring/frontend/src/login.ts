const loginButton = document.getElementById("login-button") as HTMLButtonElement;
const registerButton = document.getElementById("register-button") as HTMLButtonElement;
const usernameInput = document.getElementById("username") as HTMLInputElement;
const passwordInput = document.getElementById("password") as HTMLInputElement;

async function handleLogin() {
    const username: string = usernameInput.value.trim();
    const password: string = passwordInput.value.trim();

    if (!username || !password) {
        alert("Please fill in both username and password!");
        return;
    }

    try {
        // Get - login kezelesre
        const response: Response = await fetch(`http://localhost:8080/Bibliospring/api/login?username=${username}&password=${password}`, {
            method: "GET",
            headers: {
                "Accept": "text/plain"
            }
        });

        if (response.ok) {
            const result: string = await response.text();

            if (result === "true") {
                window.location.href = "main.html";
            } else {
                alert("Invalid username or password!");
            }
        } 
    } catch (error) {
        console.error("Error during login:", error);
        alert("An error occurred during login.");
    }
}

// Post - regisztralas
async function handleRegister() {
    const username: string = usernameInput.value.trim();
    const password: string = passwordInput.value.trim();

    if (!username || !password) {
        alert("Please fill in both username and password!");
        return;
    }

    //URL encoded
    const formData = new URLSearchParams();
    formData.append("username", username);
    formData.append("password", password);

    try {
        const response: Response = await fetch("http://localhost:8080/Bibliospring/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: formData.toString()
        });

        if (response.ok) {
            alert("Registration successful! You can now log in.");
        } else {
            alert(`Registration failed: user already exists`);
        }
    } catch (error) {
        console.error("Error during registration:", error);
        alert("An error occurred during registration.");
    }
}

loginButton.addEventListener("click", handleLogin);
registerButton.addEventListener("click", handleRegister);