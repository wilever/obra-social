import { IModule } from 'app/shared/model/module.model';

export interface IModuleType {
    id?: number;
    type?: string;
    description?: string;
    types?: IModule[];
}

export class ModuleType implements IModuleType {
    constructor(public id?: number, public type?: string, public description?: string, public types?: IModule[]) {}
}
