import React, {useEffect, useState} from "react";
import {IBook} from "../types/IBook";
import {Link} from "react-router-dom"


export default function BookList() {
    const [books, setBooks] = useState([] as IBook[])

    useEffect(() => {
        fetch('http://localhost:8080/api/book')
            .then((response) => response.json())
            .then((response) => setBooks(response.list))
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
                                <Link to={{pathname: `book/${book.id}`, state: book}}>
                                    詳細</Link>
                            </ul>
                        </>
                    );
                })
            }
        </>
    );
}
