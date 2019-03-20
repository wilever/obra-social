/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { RoleTypeDetailComponent } from 'app/entities/role-type/role-type-detail.component';
import { RoleType } from 'app/shared/model/role-type.model';

describe('Component Tests', () => {
    describe('RoleType Management Detail Component', () => {
        let comp: RoleTypeDetailComponent;
        let fixture: ComponentFixture<RoleTypeDetailComponent>;
        const route = ({ data: of({ roleType: new RoleType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [RoleTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RoleTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoleTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.roleType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
