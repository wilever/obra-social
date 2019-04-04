import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IColumnModule } from 'app/shared/model/column-module.model';
import { ColumnModuleService } from './column-module.service';
import { IModule } from 'app/shared/model/module.model';
import { ModuleService } from 'app/entities/module';

@Component({
    selector: 'jhi-column-module-update',
    templateUrl: './column-module-update.component.html'
})
export class ColumnModuleUpdateComponent implements OnInit {
    columnModule: IColumnModule;
    isSaving: boolean;

    modules: IModule[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected columnModuleService: ColumnModuleService,
        protected moduleService: ModuleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ columnModule }) => {
            this.columnModule = columnModule;
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
        if (this.columnModule.id !== undefined) {
            this.subscribeToSaveResponse(this.columnModuleService.update(this.columnModule));
        } else {
            this.subscribeToSaveResponse(this.columnModuleService.create(this.columnModule));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IColumnModule>>) {
        result.subscribe((res: HttpResponse<IColumnModule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
