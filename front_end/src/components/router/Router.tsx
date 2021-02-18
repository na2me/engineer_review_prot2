import {BrowserRouter, Route, Switch} from "react-router-dom";
import BookList from "../Book/List/BookList";
import BookInfo from "../Book/BookInfo";
import React from "react";

export default function Router() {
    return (
        <BrowserRouter>
            <Switch>
                <Route exact path={"/book"} component={BookList}/>
                <Route path={`/book/:bookId`} component={BookInfo}/>
            </Switch>
        </BrowserRouter>
    )
}
