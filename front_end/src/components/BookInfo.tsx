import React from "react";
import {IProps} from "../types/IProps";
import {IBook} from "../types/IBook";

export default function BookInfo(props: IProps) {
    const book = props.location.state as IBook
    return (
        <ul>
            <li>{book.title.value}</li>
            <li>{book.category.value}</li>
            <li>{book.description.value}</li>
            <li>{book.author.name.value}</li>
            <li>{book.publishedAt.value}</li>
        </ul>
    );
}
