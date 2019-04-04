import { IModule } from 'app/shared/model/module.model';

export interface ICompany {
    id?: number;
    name?: string;
    description?: string;
    companies?: IModule[];
}

export class Company implements ICompany {
    constructor(public id?: number, public name?: string, public description?: string, public companies?: IModule[]) {}
}
