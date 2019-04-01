/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { AffiliateUpdateComponent } from 'app/entities/affiliate/affiliate-update.component';
import { AffiliateService } from 'app/entities/affiliate/affiliate.service';
import { Affiliate } from 'app/shared/model/affiliate.model';

describe('Component Tests', () => {
    describe('Affiliate Management Update Component', () => {
        let comp: AffiliateUpdateComponent;
        let fixture: ComponentFixture<AffiliateUpdateComponent>;
        let service: AffiliateService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [AffiliateUpdateComponent]
            })
                .overrideTemplate(AffiliateUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AffiliateUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffiliateService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Affiliate(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.affiliate = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Affiliate();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.affiliate = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
