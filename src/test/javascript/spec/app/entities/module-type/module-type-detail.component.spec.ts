/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { ModuleTypeDetailComponent } from 'app/entities/module-type/module-type-detail.component';
import { ModuleType } from 'app/shared/model/module-type.model';

describe('Component Tests', () => {
    describe('ModuleType Management Detail Component', () => {
        let comp: ModuleTypeDetailComponent;
        let fixture: ComponentFixture<ModuleTypeDetailComponent>;
        const route = ({ data: of({ moduleType: new ModuleType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [ModuleTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ModuleTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ModuleTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.moduleType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
