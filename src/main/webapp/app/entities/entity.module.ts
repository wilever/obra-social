import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'role-list',
                loadChildren: './role-list/role-list.module#ObraSocialRoleListModule'
            },
            {
                path: 'role-type',
                loadChildren: './role-type/role-type.module#ObraSocialRoleTypeModule'
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
