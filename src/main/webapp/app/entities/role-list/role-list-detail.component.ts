import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoleList } from 'app/shared/model/role-list.model';

@Component({
    selector: 'jhi-role-list-detail',
    templateUrl: './role-list-detail.component.html'
})
export class RoleListDetailComponent implements OnInit {
    roleList: IRoleList;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roleList }) => {
            this.roleList = roleList;
        });
    }

    previousState() {
        window.history.back();
    }
}
