export interface IRoleType {
    id?: number;
    type?: string;
    description?: string;
}

export class RoleType implements IRoleType {
    constructor(public id?: number, public type?: string, public description?: string) {}
}
