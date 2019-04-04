/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ObraSocialTestModule } from '../../../test.module';
import { ColumnModuleDeleteDialogComponent } from 'app/entities/column-module/column-module-delete-dialog.component';
import { ColumnModuleService } from 'app/entities/column-module/column-module.service';

describe('Component Tests', () => {
    describe('ColumnModule Management Delete Component', () => {
        let comp: ColumnModuleDeleteDialogComponent;
        let fixture: ComponentFixture<ColumnModuleDeleteDialogComponent>;
        let service: ColumnModuleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [ColumnModuleDeleteDialogComponent]
            })
                .overrideTemplate(ColumnModuleDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ColumnModuleDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ColumnModuleService);
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
