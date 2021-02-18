import {IAuthor} from "./IAuthor";

export type IBook = {
    id: number
    author: IAuthor;
    category: { value: string };
    description: { value: string };
    publishedAt: { value: string };
    score: { value: number };
    title: { value: string };
    url: { value: string };
    createdDate: string;
    updatedDate: string;
}
