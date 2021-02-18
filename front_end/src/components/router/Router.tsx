import {BrowserRouter, Route, Switch} from "react-router-dom";
import BookList from "../BookList";
import BookInfo from "../BookInfo";
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
