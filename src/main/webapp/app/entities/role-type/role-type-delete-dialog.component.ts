import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoleType } from 'app/shared/model/role-type.model';
import { RoleTypeService } from './role-type.service';

@Component({
    selector: 'jhi-role-type-delete-dialog',
    templateUrl: './role-type-delete-dialog.component.html'
})
export class RoleTypeDeleteDialogComponent {
    roleType: IRoleType;

    constructor(protected roleTypeService: RoleTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roleTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roleTypeListModification',
                content: 'Deleted an roleType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-role-type-delete-popup',
    template: ''
})
export class RoleTypeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roleType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoleTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.roleType = roleType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/role-type', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/role-type', { outlets: { popup: null } }]);
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
