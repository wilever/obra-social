import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { WizzardComponent } from './component';

export const WIZZARD_ROUTE: Route = {
    path: 'wizzard',
    component: WizzardComponent,
    data: {
        authorities: [],
        pageTitle: 'wizzard.page.title'
    },
    canActivate: [UserRouteAccessService]
};
