import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ObraSocialSharedModule } from 'app/shared';
import {
    ModuleComponent,
    ModuleDetailComponent,
    ModuleUpdateComponent,
    ModuleDeletePopupComponent,
    ModuleDeleteDialogComponent,
    moduleRoute,
    modulePopupRoute
} from './';

const ENTITY_STATES = [...moduleRoute, ...modulePopupRoute];

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ModuleComponent, ModuleDetailComponent, ModuleUpdateComponent, ModuleDeleteDialogComponent, ModuleDeletePopupComponent],
    entryComponents: [ModuleComponent, ModuleUpdateComponent, ModuleDeleteDialogComponent, ModuleDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialModuleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
