import React, {createContext} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';


const books = fetch('http://localhost:8080/api/book')
    .then((response) => response.json())
    .then((response) => response.list)
    .catch(console.error)

export const BookContext = createContext(books);

ReactDOM.render(
  <BookContext.Provider value={books}>
    <App />
  </BookContext.Provider>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
