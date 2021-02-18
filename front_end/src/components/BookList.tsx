import React, {useEffect, useState} from "react";
import {IBook} from "../types/IBook";
import {BrowserRouter, Link, Route, Switch} from "react-router-dom"
import BookInfo from "./BookInfo";


// @ts-ignore
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
        <>
            {
                books.map((book: IBook) => {
                    return (
                        <>
                            <ul>
                                <li key={book.id}>{book.title.value}</li>
                                <Link to={`book/${book.id}`}>詳細</Link>
                            </ul>
                        </>
                    );
                })
            }
        </>
    );
}
