import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoleList } from 'app/shared/model/role-list.model';
import { RoleListService } from './role-list.service';

@Component({
    selector: 'jhi-role-list-delete-dialog',
    templateUrl: './role-list-delete-dialog.component.html'
})
export class RoleListDeleteDialogComponent {
    roleList: IRoleList;

    constructor(protected roleListService: RoleListService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roleListService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roleListListModification',
                content: 'Deleted an roleList'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-role-list-delete-popup',
    template: ''
})
export class RoleListDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ roleList }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoleListDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.roleList = roleList;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/role-list', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/role-list', { outlets: { popup: null } }]);
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
