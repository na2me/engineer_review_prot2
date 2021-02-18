import {BrowserRouter, Route, Switch} from "react-router-dom";
import BookList from "../BookList";

export default function Router() {
    return (
        <BrowserRouter>
            <Switch>
                <Route path="/book" component={BookList} />
            </Switch>
        </BrowserRouter>
    )
}
