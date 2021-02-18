export type IProps = {
    history: object;
    location: {
        hash: string;
        key: string;
        pathname: string;
        search: string;
        state: object
    };
    match: object;
}
