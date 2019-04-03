import { IModule } from 'app/shared/model/module.model';
import { IModuleType } from 'app/shared/model/module-type.model';
import { ITag } from 'app/shared/model/tag.model';

export interface IModule {
    id?: number;
    name?: string;
    module?: IModule;
    names?: IModule[];
    moduleType?: IModuleType;
    tag?: ITag;
}

export class Module implements IModule {
    constructor(
        public id?: number,
        public name?: string,
        public module?: IModule,
        public names?: IModule[],
        public moduleType?: IModuleType,
        public tag?: ITag
    ) {}
}
