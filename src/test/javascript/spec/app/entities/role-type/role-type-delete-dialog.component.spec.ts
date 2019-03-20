/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ObraSocialTestModule } from '../../../test.module';
import { RoleTypeDeleteDialogComponent } from 'app/entities/role-type/role-type-delete-dialog.component';
import { RoleTypeService } from 'app/entities/role-type/role-type.service';

describe('Component Tests', () => {
    describe('RoleType Management Delete Component', () => {
        let comp: RoleTypeDeleteDialogComponent;
        let fixture: ComponentFixture<RoleTypeDeleteDialogComponent>;
        let service: RoleTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [RoleTypeDeleteDialogComponent]
            })
                .overrideTemplate(RoleTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoleTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoleTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
