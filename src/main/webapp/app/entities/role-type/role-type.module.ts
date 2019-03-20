import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ObraSocialSharedModule } from 'app/shared';
import {
    RoleTypeComponent,
    RoleTypeDetailComponent,
    RoleTypeUpdateComponent,
    RoleTypeDeletePopupComponent,
    RoleTypeDeleteDialogComponent,
    roleTypeRoute,
    roleTypePopupRoute
} from './';

const ENTITY_STATES = [...roleTypeRoute, ...roleTypePopupRoute];

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoleTypeComponent,
        RoleTypeDetailComponent,
        RoleTypeUpdateComponent,
        RoleTypeDeleteDialogComponent,
        RoleTypeDeletePopupComponent
    ],
    entryComponents: [RoleTypeComponent, RoleTypeUpdateComponent, RoleTypeDeleteDialogComponent, RoleTypeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialRoleTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
