/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { ColumnModuleUpdateComponent } from 'app/entities/column-module/column-module-update.component';
import { ColumnModuleService } from 'app/entities/column-module/column-module.service';
import { ColumnModule } from 'app/shared/model/column-module.model';

describe('Component Tests', () => {
    describe('ColumnModule Management Update Component', () => {
        let comp: ColumnModuleUpdateComponent;
        let fixture: ComponentFixture<ColumnModuleUpdateComponent>;
        let service: ColumnModuleService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [ColumnModuleUpdateComponent]
            })
                .overrideTemplate(ColumnModuleUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ColumnModuleUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ColumnModuleService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ColumnModule(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.columnModule = entity;
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
                    const entity = new ColumnModule();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.columnModule = entity;
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
