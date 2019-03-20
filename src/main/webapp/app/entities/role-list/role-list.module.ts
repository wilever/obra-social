import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ObraSocialSharedModule } from 'app/shared';
import {
    RoleListComponent,
    RoleListDetailComponent,
    RoleListUpdateComponent,
    RoleListDeletePopupComponent,
    RoleListDeleteDialogComponent,
    roleListRoute,
    roleListPopupRoute
} from './';

const ENTITY_STATES = [...roleListRoute, ...roleListPopupRoute];

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RoleListComponent,
        RoleListDetailComponent,
        RoleListUpdateComponent,
        RoleListDeleteDialogComponent,
        RoleListDeletePopupComponent
    ],
    entryComponents: [RoleListComponent, RoleListUpdateComponent, RoleListDeleteDialogComponent, RoleListDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialRoleListModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
