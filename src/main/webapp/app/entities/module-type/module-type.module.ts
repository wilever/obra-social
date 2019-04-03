import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ObraSocialSharedModule } from 'app/shared';
import {
    ModuleTypeComponent,
    ModuleTypeDetailComponent,
    ModuleTypeUpdateComponent,
    ModuleTypeDeletePopupComponent,
    ModuleTypeDeleteDialogComponent,
    moduleTypeRoute,
    moduleTypePopupRoute
} from './';

const ENTITY_STATES = [...moduleTypeRoute, ...moduleTypePopupRoute];

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ModuleTypeComponent,
        ModuleTypeDetailComponent,
        ModuleTypeUpdateComponent,
        ModuleTypeDeleteDialogComponent,
        ModuleTypeDeletePopupComponent
    ],
    entryComponents: [ModuleTypeComponent, ModuleTypeUpdateComponent, ModuleTypeDeleteDialogComponent, ModuleTypeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialModuleTypeModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
