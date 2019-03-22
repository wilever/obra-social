import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { JhiLanguageHelper, AccountService, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';

// import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
// import { JhiAlertService } from 'ng-jhipster';
// import { ICompany } from 'app/shared/model/company.model';
// import { CompanyService } from '../../entities/company/company.service';
@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    companies: any[];
    company: any;

    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private sessionStorage: SessionStorageService,
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router
    ) // private jhiAlertService: JhiAlertService,
    // private companyService: CompanyService
    {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        this.companies = [{ name: 'up' }, { name: 'swiss' }];
        this.company = this.companies[0];
    }
    ngOnInit() {
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        // this.loadAllCompany();
    }
    changeCompany(company: any) {
        this.sessionStorage.store('company', company);
        this.company = company;
    }
    /*
    protected paginateCompanies(data: ICompany[], headers: HttpHeaders) {
        this.companies = data;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    loadAllCompany() {
        this.companyService
            .query({
                page: null,
                size: null,
                sort: null
            })
            .subscribe(
                (res: HttpResponse<ICompany[]>) => this.paginateCompanies(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }*/
    changeLanguage(languageKey: string) {
        this.sessionStorage.store('locale', languageKey);
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.accountService.getImageUrl() : null;
    }
}
