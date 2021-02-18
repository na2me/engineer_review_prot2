import React, {useEffect, useState} from "react";
import {IBook} from "../types/IBook";


export default function BookList() {
    const [books, setBooks] = useState([] as IBook[])

    useEffect(() => {
        fetch('http://localhost:8080/api/book')
            .then((response) => response.json())
            .then((response) => response.list)
            .then((response) => setBooks(response))
            .catch(console.error)
    }, [])

    return (
        <ul>
            {
                books.map((book) => {
                    return (
                        <li key={book.id}>{book.title.value}</li>
                    );
                })
            }
        </ul>
    );
}
