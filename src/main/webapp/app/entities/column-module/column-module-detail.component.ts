import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IColumnModule } from 'app/shared/model/column-module.model';

@Component({
    selector: 'jhi-column-module-detail',
    templateUrl: './column-module-detail.component.html'
})
export class ColumnModuleDetailComponent implements OnInit {
    columnModule: IColumnModule;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ columnModule }) => {
            this.columnModule = columnModule;
        });
    }

    previousState() {
        window.history.back();
    }
}
