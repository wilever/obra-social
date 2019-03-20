/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { RoleListUpdateComponent } from 'app/entities/role-list/role-list-update.component';
import { RoleListService } from 'app/entities/role-list/role-list.service';
import { RoleList } from 'app/shared/model/role-list.model';

describe('Component Tests', () => {
    describe('RoleList Management Update Component', () => {
        let comp: RoleListUpdateComponent;
        let fixture: ComponentFixture<RoleListUpdateComponent>;
        let service: RoleListService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [RoleListUpdateComponent]
            })
                .overrideTemplate(RoleListUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RoleListUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoleListService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RoleList(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.roleList = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RoleList();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.roleList = entity;
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
