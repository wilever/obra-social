import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Affiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from './affiliate.service';
import { AffiliateComponent } from './affiliate.component';
import { AffiliateDetailComponent } from './affiliate-detail.component';
import { AffiliateUpdateComponent } from './affiliate-update.component';
import { AffiliateDeletePopupComponent } from './affiliate-delete-dialog.component';
import { IAffiliate } from 'app/shared/model/affiliate.model';

@Injectable({ providedIn: 'root' })
export class AffiliateResolve implements Resolve<IAffiliate> {
    constructor(private service: AffiliateService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAffiliate> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Affiliate>) => response.ok),
                map((affiliate: HttpResponse<Affiliate>) => affiliate.body)
            );
        }
        return of(new Affiliate());
    }
}

export const affiliateRoute: Routes = [
    {
        path: '',
        component: AffiliateComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'obraSocialApp.affiliate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AffiliateDetailComponent,
        resolve: {
            affiliate: AffiliateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.affiliate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AffiliateUpdateComponent,
        resolve: {
            affiliate: AffiliateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.affiliate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AffiliateUpdateComponent,
        resolve: {
            affiliate: AffiliateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.affiliate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const affiliatePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AffiliateDeletePopupComponent,
        resolve: {
            affiliate: AffiliateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'obraSocialApp.affiliate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
