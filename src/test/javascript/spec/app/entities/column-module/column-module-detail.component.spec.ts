/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { ColumnModuleDetailComponent } from 'app/entities/column-module/column-module-detail.component';
import { ColumnModule } from 'app/shared/model/column-module.model';

describe('Component Tests', () => {
    describe('ColumnModule Management Detail Component', () => {
        let comp: ColumnModuleDetailComponent;
        let fixture: ComponentFixture<ColumnModuleDetailComponent>;
        const route = ({ data: of({ columnModule: new ColumnModule(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [ColumnModuleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ColumnModuleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ColumnModuleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.columnModule).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
