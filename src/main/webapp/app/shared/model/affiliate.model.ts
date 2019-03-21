import { Moment } from 'moment';
import { ICompany } from 'app/shared/model/company.model';

export interface IAffiliate {
    id?: number;
    startDate?: Moment;
    endDate?: Moment;
    number?: number;
    pay?: boolean;
    remark?: string;
    company?: ICompany;
}

export class Affiliate implements IAffiliate {
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
