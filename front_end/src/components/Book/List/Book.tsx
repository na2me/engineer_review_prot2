import {Link} from "react-router-dom";
import React from "react";
import "./Book.css";

// @ts-ignore
export default function Book({ book }) {
    return (
        <div className="product">
            <div className="product__info">
                <p>{book.title.value}</p>
                <p className="product__price">
                    <small>$</small>
                    <strong>{1000}</strong>
                </p>
                <div className="product__rating">
                    {book.score.value}
                </div>
            </div>

            <img src={"BOOKURL"} alt="" />
            <Link to={{pathname: `book/${book.id}`, state: book}}>
                詳細</Link>
        </div>
    )
}
