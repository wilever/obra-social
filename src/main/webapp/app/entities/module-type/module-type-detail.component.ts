import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModuleType } from 'app/shared/model/module-type.model';

@Component({
    selector: 'jhi-module-type-detail',
    templateUrl: './module-type-detail.component.html'
})
export class ModuleTypeDetailComponent implements OnInit {
    moduleType: IModuleType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moduleType }) => {
            this.moduleType = moduleType;
        });
    }

    previousState() {
        window.history.back();
    }
}
