import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ObraSocialSharedModule } from 'app/shared';
import {
    ColumnModuleComponent,
    ColumnModuleDetailComponent,
    ColumnModuleUpdateComponent,
    ColumnModuleDeletePopupComponent,
    ColumnModuleDeleteDialogComponent,
    columnModuleRoute,
    columnModulePopupRoute
} from './';

const ENTITY_STATES = [...columnModuleRoute, ...columnModulePopupRoute];

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ColumnModuleComponent,
        ColumnModuleDetailComponent,
        ColumnModuleUpdateComponent,
        ColumnModuleDeleteDialogComponent,
        ColumnModuleDeletePopupComponent
    ],
    entryComponents: [
        ColumnModuleComponent,
        ColumnModuleUpdateComponent,
        ColumnModuleDeleteDialogComponent,
        ColumnModuleDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialColumnModuleModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
