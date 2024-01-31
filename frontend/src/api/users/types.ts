export interface User {
    email: string;
    username: string;
    url?: string;
    id: number;
}

export interface CreateUserParameters {
    username: string;
    email: string;
    password: string;
}

export interface UpdateUserParameters {
    id: number;
    username: string;
}

export interface VerifyUserParameters{
    token: string | null;
    callback: Function;
}
