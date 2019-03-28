import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ObraSocialSharedModule } from 'app/shared';
import { COMPANY_ROUTE, CompanyComponent } from './';

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forRoot([COMPANY_ROUTE], { useHash: true })],
    declarations: [CompanyComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialCompanyModule {}
