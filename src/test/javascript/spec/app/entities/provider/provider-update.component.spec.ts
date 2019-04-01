/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { ProviderUpdateComponent } from 'app/entities/provider/provider-update.component';
import { ProviderService } from 'app/entities/provider/provider.service';
import { Provider } from 'app/shared/model/provider.model';

describe('Component Tests', () => {
    describe('Provider Management Update Component', () => {
        let comp: ProviderUpdateComponent;
        let fixture: ComponentFixture<ProviderUpdateComponent>;
        let service: ProviderService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [ProviderUpdateComponent]
            })
                .overrideTemplate(ProviderUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProviderUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProviderService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Provider(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.provider = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Provider();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.provider = entity;
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
