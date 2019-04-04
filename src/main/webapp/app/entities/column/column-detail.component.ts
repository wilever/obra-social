import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IColumn } from 'app/shared/model/column.model';

@Component({
    selector: 'jhi-column-detail',
    templateUrl: './column-detail.component.html'
})
export class ColumnDetailComponent implements OnInit {
    column: IColumn;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ column }) => {
            this.column = column;
        });
    }

    previousState() {
        window.history.back();
    }
}
