import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IColumn } from 'app/shared/model/column.model';
import { ColumnService } from './column.service';

@Component({
    selector: 'jhi-column-delete-dialog',
    templateUrl: './column-delete-dialog.component.html'
})
export class ColumnDeleteDialogComponent {
    column: IColumn;

    constructor(protected columnService: ColumnService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.columnService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'columnListModification',
                content: 'Deleted an column'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-column-delete-popup',
    template: ''
})
export class ColumnDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ column }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ColumnDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.column = column;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/column', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/column', { outlets: { popup: null } }]);
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
