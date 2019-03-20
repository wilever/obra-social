/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { RoleTypeUpdateComponent } from 'app/entities/role-type/role-type-update.component';
import { RoleTypeService } from 'app/entities/role-type/role-type.service';
import { RoleType } from 'app/shared/model/role-type.model';

describe('Component Tests', () => {
    describe('RoleType Management Update Component', () => {
        let comp: RoleTypeUpdateComponent;
        let fixture: ComponentFixture<RoleTypeUpdateComponent>;
        let service: RoleTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [RoleTypeUpdateComponent]
            })
                .overrideTemplate(RoleTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoleTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoleTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RoleType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.roleType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RoleType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.roleType = entity;
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
