import { IUser } from './User';

export interface IOrder {
    id: number;
    user_id: number;
    customer: IUser,
    salonname: string;
    date: Date;
    totalcost: number;
    status: number;
    srate: number;
    paymentType: number;
    pcount: number;
    orderdetail: IOrderDetials[];
}

export interface IOrderDetials{
    cost: number;
    serviceId: number;
    serviceTitle: string;
}