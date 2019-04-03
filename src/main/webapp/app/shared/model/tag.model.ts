import { IModule } from 'app/shared/model/module.model';

export interface ITag {
    id?: number;
    name?: string;
    description?: string;
    names?: IModule[];
}

export class Tag implements ITag {
    constructor(public id?: number, public name?: string, public description?: string, public names?: IModule[]) {}
}
