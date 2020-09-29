export interface IService {
    id: number;
    title: string;
    ownerId: number;
    salonname: string;
    details: string;
    cost: number;
    status: number;
    srate: number;
    pcount: number;
    imagelist: IServiceDetials[];
}

export interface IServiceDetials{
    picturename: string;
    picturepath: string;
}