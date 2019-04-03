/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { ModuleTypeUpdateComponent } from 'app/entities/module-type/module-type-update.component';
import { ModuleTypeService } from 'app/entities/module-type/module-type.service';
import { ModuleType } from 'app/shared/model/module-type.model';

describe('Component Tests', () => {
    describe('ModuleType Management Update Component', () => {
        let comp: ModuleTypeUpdateComponent;
        let fixture: ComponentFixture<ModuleTypeUpdateComponent>;
        let service: ModuleTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [ModuleTypeUpdateComponent]
            })
                .overrideTemplate(ModuleTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ModuleTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModuleTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ModuleType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moduleType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ModuleType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.moduleType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
