/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { ColumnUpdateComponent } from 'app/entities/column/column-update.component';
import { ColumnService } from 'app/entities/column/column.service';
import { Column } from 'app/shared/model/column.model';

describe('Component Tests', () => {
    describe('Column Management Update Component', () => {
        let comp: ColumnUpdateComponent;
        let fixture: ComponentFixture<ColumnUpdateComponent>;
        let service: ColumnService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [ColumnUpdateComponent]
            })
                .overrideTemplate(ColumnUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ColumnUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ColumnService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Column(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.column = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Column();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.column = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
