import React from "react";
import "./App.css";
import Router from "./components/router/Router";
import Header from "./components/header/Header";

function App() {
    return (
        <div className="App">
            <Header />
            <Router/>
        </div>
    );
}

export default App;
