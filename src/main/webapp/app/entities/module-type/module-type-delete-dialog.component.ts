import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModuleType } from 'app/shared/model/module-type.model';
import { ModuleTypeService } from './module-type.service';

@Component({
    selector: 'jhi-module-type-delete-dialog',
    templateUrl: './module-type-delete-dialog.component.html'
})
export class ModuleTypeDeleteDialogComponent {
    moduleType: IModuleType;

    constructor(
        protected moduleTypeService: ModuleTypeService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moduleTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'moduleTypeListModification',
                content: 'Deleted an moduleType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-module-type-delete-popup',
    template: ''
})
export class ModuleTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moduleType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ModuleTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.moduleType = moduleType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/module-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/module-type', { outlets: { popup: null } }]);
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
