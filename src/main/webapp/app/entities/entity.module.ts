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
            },
            {
                path: 'module',
                loadChildren: './module/module.module#ObraSocialModuleModule'
            },
            {
                path: 'module-type',
                loadChildren: './module-type/module-type.module#ObraSocialModuleTypeModule'
            },
            {
                path: 'tag',
                loadChildren: './tag/tag.module#ObraSocialTagModule'
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
