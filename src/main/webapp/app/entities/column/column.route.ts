import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Column } from 'app/shared/model/column.model';
import { ColumnService } from './column.service';
import { ColumnComponent } from './column.component';
import { ColumnDetailComponent } from './column-detail.component';
import { ColumnUpdateComponent } from './column-update.component';
import { ColumnDeletePopupComponent } from './column-delete-dialog.component';
import { IColumn } from 'app/shared/model/column.model';

@Injectable({ providedIn: 'root' })
export class ColumnResolve implements Resolve<IColumn> {
    constructor(private service: ColumnService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IColumn> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Column>) => response.ok),
                map((column: HttpResponse<Column>) => column.body)
            );
        }
        return of(new Column());
    }
}

export const columnRoute: Routes = [
    {
        path: '',
        component: ColumnComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obraSocialApp.column.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ColumnDetailComponent,
        resolve: {
            column: ColumnResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.column.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ColumnUpdateComponent,
        resolve: {
            column: ColumnResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.column.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ColumnUpdateComponent,
        resolve: {
            column: ColumnResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.column.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const columnPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ColumnDeletePopupComponent,
        resolve: {
            column: ColumnResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.column.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
