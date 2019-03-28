import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IAffiliate } from 'app/shared/model/affiliate.model';
import { AffiliateService } from './affiliate.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
    selector: 'jhi-affiliate-update',
    templateUrl: './affiliate-update.component.html'
})
export class AffiliateUpdateComponent implements OnInit {
    affiliate: IAffiliate;
    isSaving: boolean;

    companies: ICompany[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected affiliateService: AffiliateService,
        protected companyService: CompanyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ affiliate }) => {
            this.affiliate = affiliate;
        });
        this.companyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompany[]>) => response.body)
            )
            .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.affiliate.id !== undefined) {
            this.subscribeToSaveResponse(this.affiliateService.update(this.affiliate));
        } else {
            this.subscribeToSaveResponse(this.affiliateService.create(this.affiliate));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAffiliate>>) {
        result.subscribe((res: HttpResponse<IAffiliate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCompanyById(index: number, item: ICompany) {
        return item.id;
    }
}
