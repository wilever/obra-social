import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'company',
                loadChildren: './company/company.module#ObraSocialCompanyModule'
            },
            {
                path: 'affiliate',
                loadChildren: './affiliate/affiliate.module#ObraSocialAffiliateModule'
            },
            {
                path: 'provider',
                loadChildren: './provider/provider.module#ObraSocialProviderModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialEntityModule {}
