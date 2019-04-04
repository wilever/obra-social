import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IColumn } from 'app/shared/model/column.model';
import { ColumnService } from './column.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module';

@Component({
    selector: 'jhi-column-update',
    templateUrl: './column-update.component.html'
})
export class ColumnUpdateComponent implements OnInit {
    column: IColumn;
    isSaving: boolean;

    modules: IModule[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected columnService: ColumnService,
        protected moduleService: ModuleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ column }) => {
            this.column = column;
        });
        this.moduleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IModule[]>) => mayBeOk.ok),
                map((response: HttpResponse<IModule[]>) => response.body)
            )
            .subscribe((res: IModule[]) => (this.modules = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.column.id !== undefined) {
            this.subscribeToSaveResponse(this.columnService.update(this.column));
        } else {
            this.subscribeToSaveResponse(this.columnService.create(this.column));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IColumn>>) {
        result.subscribe((res: HttpResponse<IColumn>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackModuleById(index: number, item: IModule) {
        return item.id;
    }
}
