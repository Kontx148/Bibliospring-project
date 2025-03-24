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
const addButton = document.getElementById("add-button");
const titleInput = document.getElementById("title-input");
const isbnInput = document.getElementById("isbn-input");
const authorInput = document.getElementById("author-input");
const todoList = document.getElementById("todo-list");
const deleteButton = document.getElementById("delete-button");
const deleteCriteria = document.getElementById("delete-criteria");
const deleteValue = document.getElementById("delete-value");
const searchButton = document.getElementById("search-button");
const clearSearchButton = document.getElementById("clear-search-button");
const searchBy = document.getElementById("search-by");
const searchValue = document.getElementById("search-value");
// Konyvek betoltese a szerverrol
function fetchAndDisplayBooks() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            // Konyvek lekerese
            const response = yield fetch("http://localhost:8080/Bibliospring/api/books", {
                method: "GET",
                headers: {
                    "Accept": "application/json"
                }
            });
            if (response.ok) {
                // kapott JSON feldolgoozasa
                const books = yield response.json();
                todoList.innerHTML = "";
                // Listahoz hozzaadjuk a konyveket
                books.forEach(book => {
                    const li = document.createElement("li");
                    li.className = "book-item";
                    const bookDetails = document.createElement("div");
                    bookDetails.innerHTML = `
                    <span class="title">${book.title}</span>
                    <span class="author">${Array.isArray(book.authors) ? book.authors.join(", ") : book.authors}</span>
                    <span>ISBN: ${book.isbn}</span>
                `;
                    const deleteBtn = document.createElement("button");
                    deleteBtn.className = "delete-button";
                    deleteBtn.textContent = "Delete";
                    deleteBtn.addEventListener("click", () => __awaiter(this, void 0, void 0, function* () {
                        try {
                            // DELETE - keres, az adott konyv torlesere
                            const deleteResponse = yield fetch(`http://localhost:8080/Bibliospring/api/modify?title=${encodeURIComponent(book.title)}`, {
                                method: "DELETE",
                            });
                            if (deleteResponse.ok) {
                                yield fetchAndDisplayBooks(); // Frissitjuk a listat
                                alert("Book deleted successfully!");
                            }
                            else {
                                const errorMessage = yield deleteResponse.text();
                                alert(`Failed to delete book: ${errorMessage}`);
                            }
                        }
                        catch (error) {
                            console.error("Error:", error);
                            alert("An error occurred while sending the request.");
                        }
                    }));
                    li.appendChild(bookDetails);
                    li.appendChild(deleteBtn);
                    todoList.appendChild(li);
                });
            }
        }
        catch (error) {
            console.error("Error fetching books:", error);
        }
    });
}
// Konyvek betoltese mikor betolt az oldal
window.addEventListener("load", fetchAndDisplayBooks);
addButton.addEventListener("click", () => __awaiter(void 0, void 0, void 0, function* () {
    const title = titleInput.value.trim();
    const isbn = isbnInput.value.trim();
    const author = authorInput.value.trim();
    if (!title || !isbn || !author) {
        alert("Incomplete book data");
        return;
    }
    // Az irokat ',' szerint elvalasztjuk es listaba tesszuk
    const authors = author.split(",").map(a => a.trim());
    const bookData = {
        title,
        isbn,
        authors: authors
    };
    try {
        const response = yield fetch("http://localhost:8080/Bibliospring/api/books", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(bookData)
        });
        if (response.ok) {
            // Lista frissitese
            yield fetchAndDisplayBooks();
            titleInput.value = "";
            isbnInput.value = "";
            authorInput.value = "";
            alert("Book and author created successfully!");
        }
        else {
            const errorMessage = yield response.text();
            alert(`Failed to create book and author: ${errorMessage}`);
        }
    }
    catch (error) {
        console.error("Error:", error);
        alert("An error occurred while sending the request.");
    }
}));
deleteButton.addEventListener("click", () => __awaiter(void 0, void 0, void 0, function* () {
    const criteria = deleteCriteria.value; // "title" vagy "author"
    const value = deleteValue.value.trim();
    if (!value) {
        alert("Please enter a value to delete!");
        return;
    }
    try {
        const response = yield fetch(`http://localhost:8080/Bibliospring/api/modify?${criteria}=${(value)}`, {
            method: "DELETE",
        });
        if (response.ok) {
            yield fetchAndDisplayBooks();
            deleteValue.value = "";
            alert("Book deleted successfully!");
        }
        else {
            const errorMessage = yield response.text();
            alert(`Failed to delete book: ${errorMessage}`);
        }
    }
    catch (error) {
        console.error("Error:", error);
        alert("An error occurred while sending the request.");
    }
}));
searchButton.addEventListener("click", () => __awaiter(void 0, void 0, void 0, function* () {
    const criteria = searchBy.value; // "title" vagy "author"
    const value = searchValue.value.trim();
    if (!value) {
        alert("Please enter a value to search!");
        return;
    }
    try {
        const response = yield fetch(`http://localhost:8080/Bibliospring/api/books`, {
            method: "GET",
            headers: {
                "Accept": "application/json"
            }
        });
        if (response.ok) {
            const books = yield response.json();
            // konyvek szurese a kriterium alapjan
            const filteredBooks = books.filter(book => {
                if (criteria === "title") {
                    return book.title.toLowerCase().includes(value.toLowerCase());
                }
                else if (criteria === "author") {
                    return Array.isArray(book.authors) && book.authors.some(a => a.toLowerCase().includes(value.toLowerCase()));
                }
                return true;
            });
            todoList.innerHTML = "";
            filteredBooks.forEach(book => {
                const li = document.createElement("li");
                li.className = "book-item";
                const bookDetails = document.createElement("div");
                bookDetails.innerHTML = `
                    <span class="title">${book.title}</span>
                    <span class="author">${Array.isArray(book.authors) ? book.authors.join(", ") : book.authors}</span>
                    <span>ISBN: ${book.isbn}</span>
                `;
                const deleteBtn = document.createElement("button");
                deleteBtn.className = "delete-button";
                deleteBtn.textContent = "Delete";
                deleteBtn.addEventListener("click", () => __awaiter(void 0, void 0, void 0, function* () {
                    try {
                        const deleteResponse = yield fetch(`http://localhost:8080/Bibliospring/api/modify?title=${encodeURIComponent(book.title)}`, {
                            method: "DELETE",
                        });
                        if (deleteResponse.ok) {
                            yield fetchAndDisplayBooks();
                            alert("Book deleted successfully!");
                        }
                        else {
                            const errorMessage = yield deleteResponse.text();
                            alert(`Failed to delete book: ${errorMessage}`);
                        }
                    }
                    catch (error) {
                        console.error("Error:", error);
                        alert("An error occurred while sending the request.");
                    }
                }));
                li.appendChild(bookDetails);
                li.appendChild(deleteBtn);
                todoList.appendChild(li);
            });
        }
        else {
            const errorMessage = yield response.text();
            console.error(`Failed to fetch books: ${errorMessage}`);
        }
    }
    catch (error) {
        console.error("Error fetching books:", error);
    }
}));
clearSearchButton.addEventListener("click", () => {
    searchValue.value = "";
    fetchAndDisplayBooks();
});
