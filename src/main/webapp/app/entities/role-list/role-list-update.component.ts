import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoleList } from 'app/shared/model/role-list.model';
import { RoleListService } from './role-list.service';
import { IUser, UserService } from 'app/core';
import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from 'app/entities/role-type';

@Component({
    selector: 'jhi-role-list-update',
    templateUrl: './role-list-update.component.html'
})
export class RoleListUpdateComponent implements OnInit {
    roleList: IRoleList;
    isSaving: boolean;

    users: IUser[];

    roletypes: IRoleType[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roleListService: RoleListService,
        protected userService: UserService,
        protected roleTypeService: RoleTypeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ roleList }) => {
            this.roleList = roleList;
        });
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.roleTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoleType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoleType[]>) => response.body)
            )
            .subscribe((res: IRoleType[]) => (this.roletypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.roleList.id !== undefined) {
            this.subscribeToSaveResponse(this.roleListService.update(this.roleList));
        } else {
            this.subscribeToSaveResponse(this.roleListService.create(this.roleList));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoleList>>) {
        result.subscribe((res: HttpResponse<IRoleList>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackRoleTypeById(index: number, item: IRoleType) {
        return item.id;
    }
}
