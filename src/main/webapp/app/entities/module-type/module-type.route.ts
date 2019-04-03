import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ModuleType } from 'app/shared/model/module-type.model';
import { ModuleTypeService } from './module-type.service';
import { ModuleTypeComponent } from './module-type.component';
import { ModuleTypeDetailComponent } from './module-type-detail.component';
import { ModuleTypeUpdateComponent } from './module-type-update.component';
import { ModuleTypeDeletePopupComponent } from './module-type-delete-dialog.component';
import { IModuleType } from 'app/shared/model/module-type.model';

@Injectable({ providedIn: 'root' })
export class ModuleTypeResolve implements Resolve<IModuleType> {
    constructor(private service: ModuleTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IModuleType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ModuleType>) => response.ok),
                map((moduleType: HttpResponse<ModuleType>) => moduleType.body)
            );
        }
        return of(new ModuleType());
    }
}

export const moduleTypeRoute: Routes = [
    {
        path: '',
        component: ModuleTypeComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obraSocialApp.moduleType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ModuleTypeDetailComponent,
        resolve: {
            moduleType: ModuleTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.moduleType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ModuleTypeUpdateComponent,
        resolve: {
            moduleType: ModuleTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.moduleType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ModuleTypeUpdateComponent,
        resolve: {
            moduleType: ModuleTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.moduleType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moduleTypePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ModuleTypeDeletePopupComponent,
        resolve: {
            moduleType: ModuleTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.moduleType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
