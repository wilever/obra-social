import { IModule } from 'app/shared/model/module.model';

export interface IColumnModule {
    id?: number;
    tableName?: string;
    columnName?: string;
    activeInd?: boolean;
    dataType?: string;
    description?: string;
    defaultValue?: string;
    module?: IModule;
}

export class ColumnModule implements IColumnModule {
    constructor(
        public id?: number,
        public tableName?: string,
        public columnName?: string,
        public activeInd?: boolean,
        public dataType?: string,
        public description?: string,
        public defaultValue?: string,
        public module?: IModule
    ) {
        this.activeInd = this.activeInd || false;
    }
}
