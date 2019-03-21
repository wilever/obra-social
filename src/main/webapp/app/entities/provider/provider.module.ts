import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ObraSocialSharedModule } from 'app/shared';
import {
    ProviderComponent,
    ProviderDetailComponent,
    ProviderUpdateComponent,
    ProviderDeletePopupComponent,
    ProviderDeleteDialogComponent,
    providerRoute,
    providerPopupRoute
} from './';

const ENTITY_STATES = [...providerRoute, ...providerPopupRoute];

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProviderComponent,
        ProviderDetailComponent,
        ProviderUpdateComponent,
        ProviderDeleteDialogComponent,
        ProviderDeletePopupComponent
    ],
    entryComponents: [ProviderComponent, ProviderUpdateComponent, ProviderDeleteDialogComponent, ProviderDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialProviderModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
