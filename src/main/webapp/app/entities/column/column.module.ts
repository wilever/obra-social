import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ObraSocialSharedModule } from 'app/shared';
import {
    ColumnComponent,
    ColumnDetailComponent,
    ColumnUpdateComponent,
    ColumnDeletePopupComponent,
    ColumnDeleteDialogComponent,
    columnRoute,
    columnPopupRoute
} from './';

const ENTITY_STATES = [...columnRoute, ...columnPopupRoute];

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ColumnComponent, ColumnDetailComponent, ColumnUpdateComponent, ColumnDeleteDialogComponent, ColumnDeletePopupComponent],
    entryComponents: [ColumnComponent, ColumnUpdateComponent, ColumnDeleteDialogComponent, ColumnDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialColumnModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
