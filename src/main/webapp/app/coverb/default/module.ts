import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ObraSocialSharedModule } from 'app/shared';
import { DEFAULT_ROUTE, DefaultComponent } from './';

@NgModule({
    imports: [ObraSocialSharedModule, RouterModule.forRoot([DEFAULT_ROUTE], { useHash: true })],
    declarations: [DefaultComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ObraSocialDefaultModule {}
