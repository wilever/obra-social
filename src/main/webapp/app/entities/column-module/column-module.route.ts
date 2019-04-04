import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ColumnModule } from 'app/shared/model/column-module.model';
import { ColumnModuleService } from './column-module.service';
import { ColumnModuleComponent } from './column-module.component';
import { ColumnModuleDetailComponent } from './column-module-detail.component';
import { ColumnModuleUpdateComponent } from './column-module-update.component';
import { ColumnModuleDeletePopupComponent } from './column-module-delete-dialog.component';
import { IColumnModule } from 'app/shared/model/column-module.model';

@Injectable({ providedIn: 'root' })
export class ColumnModuleResolve implements Resolve<IColumnModule> {
    constructor(private service: ColumnModuleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IColumnModule> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ColumnModule>) => response.ok),
                map((columnModule: HttpResponse<ColumnModule>) => columnModule.body)
            );
        }
        return of(new ColumnModule());
    }
}

export const columnModuleRoute: Routes = [
    {
        path: '',
        component: ColumnModuleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obraSocialApp.columnModule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ColumnModuleDetailComponent,
        resolve: {
            columnModule: ColumnModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.columnModule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ColumnModuleUpdateComponent,
        resolve: {
            columnModule: ColumnModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.columnModule.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ColumnModuleUpdateComponent,
        resolve: {
            columnModule: ColumnModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.columnModule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const columnModulePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ColumnModuleDeletePopupComponent,
        resolve: {
            columnModule: ColumnModuleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.columnModule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
