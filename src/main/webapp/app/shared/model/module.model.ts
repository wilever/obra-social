import { IModule } from 'app/shared/model/module.model';
import { IColumnModule } from 'app/shared/model/column-module.model';
import { IModuleType } from 'app/shared/model/module-type.model';
import { ITag } from 'app/shared/model/tag.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IModule {
    id?: number;
    name?: string;
    description?: string;
    module?: IModule;
    modules?: IModule[];
    modules?: IColumnModule[];
    moduleType?: IModuleType;
    tag?: ITag;
    company?: ICompany;
}

export class Module implements IModule {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public module?: IModule,
        public modules?: IModule[],
        public modules?: IColumnModule[],
        public moduleType?: IModuleType,
        public tag?: ITag,
        public company?: ICompany
    ) {}
}
