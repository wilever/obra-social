/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ObraSocialTestModule } from '../../../test.module';
import { AffiliateDeleteDialogComponent } from 'app/entities/affiliate/affiliate-delete-dialog.component';
import { AffiliateService } from 'app/entities/affiliate/affiliate.service';

describe('Component Tests', () => {
    describe('Affiliate Management Delete Component', () => {
        let comp: AffiliateDeleteDialogComponent;
        let fixture: ComponentFixture<AffiliateDeleteDialogComponent>;
        let service: AffiliateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [AffiliateDeleteDialogComponent]
            })
                .overrideTemplate(AffiliateDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AffiliateDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffiliateService);
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
