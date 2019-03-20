import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from './role-type.service';
import { RoleTypeComponent } from './role-type.component';
import { RoleTypeDetailComponent } from './role-type-detail.component';
import { RoleTypeUpdateComponent } from './role-type-update.component';
import { RoleTypeDeletePopupComponent } from './role-type-delete-dialog.component';
import { IRoleType } from 'app/shared/model/role-type.model';

@Injectable({ providedIn: 'root' })
export class RoleTypeResolve implements Resolve<IRoleType> {
    constructor(private service: RoleTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRoleType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoleType>) => response.ok),
                map((roleType: HttpResponse<RoleType>) => roleType.body)
            );
        }
        return of(new RoleType());
    }
}

export const roleTypeRoute: Routes = [
    {
        path: '',
        component: RoleTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obraSocialApp.roleType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoleTypeDetailComponent,
        resolve: {
            roleType: RoleTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.roleType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoleTypeUpdateComponent,
        resolve: {
            roleType: RoleTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.roleType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoleTypeUpdateComponent,
        resolve: {
            roleType: RoleTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.roleType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roleTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoleTypeDeletePopupComponent,
        resolve: {
            roleType: RoleTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.roleType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
