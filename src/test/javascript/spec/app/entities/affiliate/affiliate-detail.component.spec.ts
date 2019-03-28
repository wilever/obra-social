/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { AffiliateDetailComponent } from 'app/entities/affiliate/affiliate-detail.component';
import { Affiliate } from 'app/shared/model/affiliate.model';

describe('Component Tests', () => {
    describe('Affiliate Management Detail Component', () => {
        let comp: AffiliateDetailComponent;
        let fixture: ComponentFixture<AffiliateDetailComponent>;
        const route = ({ data: of({ affiliate: new Affiliate(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [AffiliateDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AffiliateDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AffiliateDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.affiliate).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
