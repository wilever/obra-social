import { Route } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { WizzardFormComponent } from './component';

export const WIZZARD_ROUTE: Route = {
    path: 'wizzard-form',
    component: WizzardFormComponent,
    data: {
        authorities: [],
        pageTitle: 'wizzard.page.title'
    },
    canActivate: [UserRouteAccessService]
};
