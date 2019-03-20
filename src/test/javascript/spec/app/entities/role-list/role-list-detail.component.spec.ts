/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ObraSocialTestModule } from '../../../test.module';
import { RoleListDetailComponent } from 'app/entities/role-list/role-list-detail.component';
import { RoleList } from 'app/shared/model/role-list.model';

describe('Component Tests', () => {
    describe('RoleList Management Detail Component', () => {
        let comp: RoleListDetailComponent;
        let fixture: ComponentFixture<RoleListDetailComponent>;
        const route = ({ data: of({ roleList: new RoleList(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ObraSocialTestModule],
                declarations: [RoleListDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RoleListDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RoleListDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.roleList).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
