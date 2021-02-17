import React, {useEffect, useState} from "react";
import {log} from "util";

export type IBook = {
    id: number
    author: IAuthor;
    category: {value: string};
    description: {value: string};
    publishedAt: {value: string};
    score: {value: number};
    title: {value: string};
    url: {value: string};
    createdDate: string;
    updatedDate: string;
}

export type IAuthor = {
    id: number;
    name: {value: string};
    biography: {value: string};
    createdDate: string;
    updatedDate: string;
}

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
      <div>
          <p>
              comone
              {books.length}
          </p>
          {
              books.map((book) => {
                  return(
                    <ul>
                        <li>{book.title.value}</li>
                    </ul>
                  );
              })
          }
      </div>
    );
}
