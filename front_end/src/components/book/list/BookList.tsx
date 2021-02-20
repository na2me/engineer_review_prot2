import React, {useEffect, useState} from "react";
import {IBook} from "../../../types/IBook";
import "./BookList.css";
import Book from "./Book";


export default function BookList() {
    const [books, setBooks] = useState([] as IBook[])

    useEffect(() => {
        fetch('http://localhost:8080/api/book')
            .then((response) => response.json())
            .then((response) => setBooks(response.list))
            .catch(console.error)
    }, [])

    return (
        <div className={"book__row"}>
            {
                books.map((book: IBook) => {
                    return (
                        <>
                            <Book book={book} />
                        </>
                    );
                })
            }
        </div>
    );
}
