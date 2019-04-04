/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { ColumnDetailComponent } from 'app/entities/column/column-detail.component';
import { Column } from 'app/shared/model/column.model';

describe('Component Tests', () => {
    describe('Column Management Detail Component', () => {
        let comp: ColumnDetailComponent;
        let fixture: ComponentFixture<ColumnDetailComponent>;
        const route = ({ data: of({ column: new Column(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [ColumnDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ColumnDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ColumnDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.column).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
