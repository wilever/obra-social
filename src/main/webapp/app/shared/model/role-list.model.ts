import { IUser } from 'app/core/user/user.model';
import { IRoleType } from 'app/shared/model/role-type.model';

export interface IRoleList {
    id?: number;
    role?: string;
    description?: string;
    resource?: string;
    user?: IUser;
    roleType?: IRoleType;
}

export class RoleList implements IRoleList {
    constructor(
        public id?: number,
        public role?: string,
        public description?: string,
        public resource?: string,
        public user?: IUser,
        public roleType?: IRoleType
    ) {}
}
