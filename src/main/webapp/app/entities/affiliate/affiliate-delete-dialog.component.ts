import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from './affiliate.service';

@Component({
    selector: 'jhi-affiliate-delete-dialog',
    templateUrl: './affiliate-delete-dialog.component.html'
})
export class AffiliateDeleteDialogComponent {
    affiliate: IAffiliate;

    constructor(
        protected affiliateService: AffiliateService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.affiliateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'affiliateListModification',
                content: 'Deleted an affiliate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-affiliate-delete-popup',
    template: ''
})
export class AffiliateDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ affiliate }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AffiliateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.affiliate = affiliate;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/affiliate', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/affiliate', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
