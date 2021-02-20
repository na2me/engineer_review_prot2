import React, {useEffect, useState} from "react";
import {IBook} from "../../types/IBook";
import {createStyles, GridList, GridListTile, GridListTileBar, makeStyles, Theme} from "@material-ui/core";

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            display: 'flex',
            flexWrap: 'wrap',
            justifyContent: 'space-around',
            overflow: 'hidden',
            backgroundColor: theme.palette.background.paper,
        },
        gridList: {
            width: 500,
            height: 450,
        },
    }),
);

export default function BookList() {
    const [books, setBooks] = useState([] as IBook[])
    const classes = useStyles();

    useEffect(() => {
        fetch('http://localhost:8080/api/book')
            .then((response) => response.json())
            .then((response) => setBooks(response.list))
            .catch(console.error)
    }, [])

    return (
        <div className={classes.root}>
            <GridList cellHeight={180} className={classes.gridList}>
                {books.map((book) => (
                    <GridListTile key={book.id}>
                        <img src={book.url.value} alt="画像"/>
                        <GridListTileBar
                            title={book.title.value}
                            subtitle={<span>by: {book.author.name.value}</span>}
                        />
                    </GridListTile>
                ))}
            </GridList>
        </div>
    );
}
