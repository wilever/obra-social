import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RoleList } from 'app/shared/model/role-list.model';
import { RoleListService } from './role-list.service';
import { RoleListComponent } from './role-list.component';
import { RoleListDetailComponent } from './role-list-detail.component';
import { RoleListUpdateComponent } from './role-list-update.component';
import { RoleListDeletePopupComponent } from './role-list-delete-dialog.component';
import { IRoleList } from 'app/shared/model/role-list.model';

@Injectable({ providedIn: 'root' })
export class RoleListResolve implements Resolve<IRoleList> {
    constructor(private service: RoleListService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRoleList> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RoleList>) => response.ok),
                map((roleList: HttpResponse<RoleList>) => roleList.body)
            );
        }
        return of(new RoleList());
    }
}

export const roleListRoute: Routes = [
    {
        path: '',
        component: RoleListComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obraSocialApp.roleList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoleListDetailComponent,
        resolve: {
            roleList: RoleListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.roleList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoleListUpdateComponent,
        resolve: {
            roleList: RoleListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.roleList.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoleListUpdateComponent,
        resolve: {
            roleList: RoleListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.roleList.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const roleListPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoleListDeletePopupComponent,
        resolve: {
            roleList: RoleListResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.roleList.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
