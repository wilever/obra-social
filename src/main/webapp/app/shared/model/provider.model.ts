import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';

export interface IProvider {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    number?: number;
    pay?: boolean;
    remark?: string;
    company?: ICompany;
}

export class Provider implements IProvider {
    constructor(
        public id?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public number?: number,
        public pay?: boolean,
        public remark?: string,
        public company?: ICompany
    ) {
        this.pay = this.pay || false;
    }
}
