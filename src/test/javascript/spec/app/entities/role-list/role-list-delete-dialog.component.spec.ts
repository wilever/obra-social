/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ObraSocialTestModule } from '../../../test.module';
import { RoleListDeleteDialogComponent } from 'app/entities/role-list/role-list-delete-dialog.component';
import { RoleListService } from 'app/entities/role-list/role-list.service';

describe('Component Tests', () => {
    describe('RoleList Management Delete Component', () => {
        let comp: RoleListDeleteDialogComponent;
        let fixture: ComponentFixture<RoleListDeleteDialogComponent>;
        let service: RoleListService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [RoleListDeleteDialogComponent]
            })
                .overrideTemplate(RoleListDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoleListDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RoleListService);
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
