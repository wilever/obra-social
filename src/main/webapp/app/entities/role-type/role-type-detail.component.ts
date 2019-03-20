import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoleType } from 'app/shared/model/role-type.model';

@Component({
    selector: 'jhi-role-type-detail',
    templateUrl: './role-type-detail.component.html'
})
export class RoleTypeDetailComponent implements OnInit {
    roleType: IRoleType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roleType }) => {
            this.roleType = roleType;
        });
    }

    previousState() {
        window.history.back();
    }
}
