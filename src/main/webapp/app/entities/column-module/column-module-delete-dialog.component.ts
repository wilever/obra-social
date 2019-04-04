import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IColumnModule } from 'app/shared/model/column-module.model';
import { ColumnModuleService } from './column-module.service';

@Component({
    selector: 'jhi-column-module-delete-dialog',
    templateUrl: './column-module-delete-dialog.component.html'
})
export class ColumnModuleDeleteDialogComponent {
    columnModule: IColumnModule;

    constructor(
        protected columnModuleService: ColumnModuleService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.columnModuleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'columnModuleListModification',
                content: 'Deleted an columnModule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-column-module-delete-popup',
    template: ''
})
export class ColumnModuleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ columnModule }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ColumnModuleDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.columnModule = columnModule;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/column-module', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/column-module', { outlets: { popup: null } }]);
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
