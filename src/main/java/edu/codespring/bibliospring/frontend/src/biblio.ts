const addButton = document.getElementById("add-button") as HTMLButtonElement;
const titleInput = document.getElementById("title-input") as HTMLInputElement;
const isbnInput = document.getElementById("isbn-input") as HTMLInputElement;
const authorInput = document.getElementById("author-input") as HTMLInputElement;
const todoList = document.getElementById("todo-list") as HTMLUListElement;

const deleteButton = document.getElementById("delete-button") as HTMLButtonElement;
const deleteCriteria = document.getElementById("delete-criteria") as HTMLSelectElement;
const deleteValue = document.getElementById("delete-value") as HTMLInputElement;

const searchButton = document.getElementById("search-button") as HTMLButtonElement;
const clearSearchButton = document.getElementById("clear-search-button") as HTMLButtonElement;
const searchBy = document.getElementById("search-by") as HTMLSelectElement;
const searchValue = document.getElementById("search-value") as HTMLInputElement;

// Konyvek betoltese a szerverrol
async function fetchAndDisplayBooks() {
    try {
        // Konyvek lekerese
        const response: Response = await fetch("http://localhost:8080/Bibliospring/api/books", {
            method: "GET",
            headers: {
                "Accept": "application/json"
            }
        });

        if (response.ok) {
            // kapott JSON feldolgoozasa
            const books: { title: string; isbn: string; authors: string[] }[] = await response.json();

            todoList.innerHTML = "";

            // Listahoz hozzaadjuk a konyveket
            books.forEach(book => {
                const li: HTMLLIElement = document.createElement("li");
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
                deleteBtn.addEventListener("click", async () => {
                    try {
                        // DELETE - keres, az adott konyv torlesere
                        const deleteResponse: Response = await fetch(`http://localhost:8080/Bibliospring/api/modify?title=${encodeURIComponent(book.title)}`, {
                            method: "DELETE",
                        });

                        if (deleteResponse.ok) {
                            await fetchAndDisplayBooks(); // Frissitjuk a listat
                            alert("Book deleted successfully!");
                        } else {
                            const errorMessage: string = await deleteResponse.text();
                            alert(`Failed to delete book: ${errorMessage}`);
                        }
                    } catch (error) {
                        console.error("Error:", error);
                        alert("An error occurred while sending the request.");
                    }
                });

                li.appendChild(bookDetails);
                li.appendChild(deleteBtn);
                todoList.appendChild(li);
            });
        } 
    } catch (error) {
        console.error("Error fetching books:", error);
    }
}

// Konyvek betoltese mikor betolt az oldal
window.addEventListener("load", fetchAndDisplayBooks);

addButton.addEventListener("click", async () => {
    const title: string = titleInput.value.trim();
    const isbn: string = isbnInput.value.trim();
    const author: string = authorInput.value.trim();

    if (!title || !isbn || !author) {
        alert("Incomplete book data");
        return;
    }

    // Az irokat ',' szerint elvalasztjuk es listaba tesszuk
    const authors: string[] = author.split(",").map(a => a.trim());

    const bookData = {
        title,
        isbn,
        authors: authors 
    };

    try {
        const response: Response = await fetch("http://localhost:8080/Bibliospring/api/books", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(bookData)
        });

        if (response.ok) {
            // Lista frissitese
            await fetchAndDisplayBooks();

            titleInput.value = "";
            isbnInput.value = "";
            authorInput.value = "";

            alert("Book and author created successfully!");
        } else {
            const errorMessage: string = await response.text();
            alert(`Failed to create book and author: ${errorMessage}`);
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred while sending the request.");
    }
});

deleteButton.addEventListener("click", async () => {
    const criteria: string = deleteCriteria.value; // "title" vagy "author"
    const value: string = deleteValue.value.trim(); 

    if (!value) {
        alert("Please enter a value to delete!");
        return;
    }

    try {
        const response: Response = await fetch(`http://localhost:8080/Bibliospring/api/modify?${criteria}=${(value)}`, {
            method: "DELETE",
        });


        if (response.ok) {
            await fetchAndDisplayBooks();
            deleteValue.value = ""; 
            alert("Book deleted successfully!");
        } else {
            const errorMessage: string = await response.text();
            alert(`Failed to delete book: ${errorMessage}`);
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred while sending the request.");
    }
});

searchButton.addEventListener("click", async () => {
    const criteria: string = searchBy.value; // "title" vagy "author"
    const value: string = searchValue.value.trim(); 

    if (!value) {
        alert("Please enter a value to search!");
        return;
    }

    try {
        const response: Response = await fetch(`http://localhost:8080/Bibliospring/api/books`, {
            method: "GET",
            headers: {
                "Accept": "application/json"
            }
        });

        if (response.ok) {
            const books: { title: string; isbn: string; authors: string[] }[] = await response.json();

            // konyvek szurese a kriterium alapjan
            const filteredBooks = books.filter(book => {
                if (criteria === "title") {
                    return book.title.toLowerCase().includes(value.toLowerCase());
                } else if (criteria === "author") {
                    return Array.isArray(book.authors) && book.authors.some(a => a.toLowerCase().includes(value.toLowerCase()));
                }
                return true;
            });

            todoList.innerHTML = "";

            filteredBooks.forEach(book => {
                const li: HTMLLIElement = document.createElement("li");
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
                deleteBtn.addEventListener("click", async () => {
                    try {
                        const deleteResponse: Response = await fetch(`http://localhost:8080/Bibliospring/api/modify?title=${encodeURIComponent(book.title)}`, {
                            method: "DELETE",
                        });

                        if (deleteResponse.ok) {
                            await fetchAndDisplayBooks();
                            alert("Book deleted successfully!");
                        } else {
                            const errorMessage: string = await deleteResponse.text();
                            alert(`Failed to delete book: ${errorMessage}`);
                        }
                    } catch (error) {
                        console.error("Error:", error);
                        alert("An error occurred while sending the request.");
                    }
                });

                li.appendChild(bookDetails);
                li.appendChild(deleteBtn);
                todoList.appendChild(li);
            });
        } else {
            const errorMessage: string = await response.text();
            console.error(`Failed to fetch books: ${errorMessage}`);
        }
    } catch (error) {
        console.error("Error fetching books:", error);
    }
});

clearSearchButton.addEventListener("click", () => {
    searchValue.value = "";
    fetchAndDisplayBooks();
});