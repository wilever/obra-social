import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ObraSocialSharedModule } from 'app/shared';
import { WIZZARD_ROUTE, WizzardFormComponent } from './';
import { MatButtonModule, MatCheckboxModule } from '@angular/material';
import { DemoMaterialModule } from '../material-module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        DemoMaterialModule,
        MatButtonModule,
        MatCheckboxModule,
        ObraSocialSharedModule,
        RouterModule.forRoot([WIZZARD_ROUTE], { useHash: true })
    ],
    declarations: [WizzardFormComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialWizzardFormModule {}
