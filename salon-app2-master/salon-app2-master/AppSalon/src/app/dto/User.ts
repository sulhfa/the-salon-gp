
export interface IUser {
    user_id: number;
    username: string;
    password: string;
    usertype: number;
    status: number;
    fullname: string;
    phone: string;
    security_question: number;
    security_answer: string;
    location:  string;
    address: string;
    salonname: string;
}
export interface INewPassword {
    username: string;
    password: string;
    newpassword:string;
}
