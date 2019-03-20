import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from './role-type.service';

@Component({
    selector: 'jhi-role-type-update',
    templateUrl: './role-type-update.component.html'
})
export class RoleTypeUpdateComponent implements OnInit {
    roleType: IRoleType;
    isSaving: boolean;

    constructor(protected roleTypeService: RoleTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ roleType }) => {
            this.roleType = roleType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.roleType.id !== undefined) {
            this.subscribeToSaveResponse(this.roleTypeService.update(this.roleType));
        } else {
            this.subscribeToSaveResponse(this.roleTypeService.create(this.roleType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleType>>) {
        result.subscribe((res: HttpResponse<IRoleType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
