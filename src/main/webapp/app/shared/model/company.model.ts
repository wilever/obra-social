export interface ICompany {
    id?: number;
    name?: string;
    description?: string;
}

export class Company implements ICompany {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
