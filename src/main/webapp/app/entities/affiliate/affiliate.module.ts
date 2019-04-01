import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ObraSocialSharedModule } from 'app/shared';
import {
    AffiliateComponent,
    AffiliateDetailComponent,
    AffiliateUpdateComponent,
    AffiliateDeletePopupComponent,
    AffiliateDeleteDialogComponent,
    affiliateRoute,
    affiliatePopupRoute
} from './';

const ENTITY_STATES = [...affiliateRoute, ...affiliatePopupRoute];

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AffiliateComponent,
        AffiliateDetailComponent,
        AffiliateUpdateComponent,
        AffiliateDeleteDialogComponent,
        AffiliateDeletePopupComponent
    ],
    entryComponents: [AffiliateComponent, AffiliateUpdateComponent, AffiliateDeleteDialogComponent, AffiliateDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialAffiliateModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
