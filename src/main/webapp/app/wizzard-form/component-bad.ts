// import { Component } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
// import {Observable} from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { CollectionViewer, SelectionChange } from '@angular/cdk/collections';
import { FlatTreeControl } from '@angular/cdk/tree';
import { Injectable } from '@angular/core';
import { BehaviorSubject, merge, Observable } from 'rxjs';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from '../entities/company/company.service';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

/** Flat node with expandable and level information */
export class DynamicFlatNode {
    constructor(public item: string, public level = 1, public expandable = false, public isLoading = false) {}
}
/**
 * Database for dynamic data. When expanding a node in the tree, the data source will need to fetch
 * the descendants data from the database.
 */
export class DynamicDatabase {
    dataMap = new Map<string, string[]>([
        ['Fruits', ['Apple', 'Orange', 'Banana']],
        ['Vegetables', ['Tomato', 'Potato', 'Onion']],
        ['Apple', ['Fuji', 'Macintosh']],
        ['Onion', ['Yellow', 'White', 'Purple']]
    ]);
    rootLevelNodes: string[] = ['Fruits', 'Vegetables'];
    /** Initial data from database */
    initialData(): DynamicFlatNode[] {
        return this.rootLevelNodes.map(name => new DynamicFlatNode(name, 0, true));
    }
    getChildren(node: string): string[] | undefined {
        return this.dataMap.get(node);
    }
    isExpandable(node: string): boolean {
        return this.dataMap.has(node);
    }
}
/**
 * File database, it can build a tree structured Json object from string.
 * Each node in Json object represents a file or a directory. For a file, it has filename and type.
 * For a directory, it has filename and children (a list of files or directories).
 * The input will be a json object string, and the output is a list of `FileNode` with nested
 * structure.
 */
@Injectable()
export class DynamicDataSource {
    dataChange = new BehaviorSubject<DynamicFlatNode[]>([]);
    get data(): DynamicFlatNode[] {
        return this.dataChange.value;
    }
    set data(value: DynamicFlatNode[]) {
        this.treeControl.dataNodes = value;
        this.dataChange.next(value);
    }
    constructor(private treeControl: FlatTreeControl<DynamicFlatNode>, private database: DynamicDatabase) {}
    connect(collectionViewer: CollectionViewer): Observable<DynamicFlatNode[]> {
        this.treeControl.expansionModel.onChange.subscribe(change => {
            if ((change as SelectionChange<DynamicFlatNode>).added || (change as SelectionChange<DynamicFlatNode>).removed) {
                this.handleTreeControl(change as SelectionChange<DynamicFlatNode>);
            }
        });
        return merge(collectionViewer.viewChange, this.dataChange).pipe(map(() => this.data));
    }
    /** Handle expand/collapse behaviors */
    handleTreeControl(change: SelectionChange<DynamicFlatNode>) {
        if (change.added) {
            change.added.forEach(node => this.toggleNode(node, true));
        }
        if (change.removed) {
            change.removed
                .slice()
                .reverse()
                .forEach(node => this.toggleNode(node, false));
        }
    }
    /**
     * Toggle the node, remove from display list
     */
    toggleNode(node: DynamicFlatNode, expand: boolean) {
        const children = this.database.getChildren(node.item);
        const index = this.data.indexOf(node);
        if (!children || index < 0) {
            // If no children, or cannot find the node, no op
            return;
        }
        node.isLoading = true;
        setTimeout(() => {
            if (expand) {
                const nodes = children.map(name => new DynamicFlatNode(name, node.level + 1, this.database.isExpandable(name)));
                this.data.splice(index + 1, 0, ...nodes);
            } else {
                let count = 0;
                for (let i = index + 1; i < this.data.length && this.data[i].level > node.level; i++, count++) {}
                this.data.splice(index + 1, count);
            }
            // notify the change
            this.dataChange.next(this.data);
            node.isLoading = false;
        }, 1000);
    }
}
/*
export interface Tile {
    color: string;
    cols: number;
    rows: number;
    text: string;
}

export interface Food {
    value: string;
    viewValue: string;
}*/

@Component({
    selector: 'jhi-resume',
    templateUrl: './component.html',
    styleUrls: ['style.scss'],
    providers: [DynamicDatabase]
})
export class WizzardForm2Component implements OnInit {
    /*
    companySelected: ICompany;
    wizzardForm: FormGroup;
    companies: ICompany[];
    company: ICompany;
    */
    // company: string;
    // mode = new FormControl('over');
    // shouldRun = [/(^|\.)plnkr\.co$/, /(^|\.)stackblitz\.io$/].some(h => h.test(window.location.host));
    // myControl = new FormControl();
    // options: string[] = ['One', 'Two', 'Three'];
    // companies: string[] = ['company_One', 'company_Two', 'company_Three'];
    // filteredOptions: Observable<string[]>;
    /*
    favoriteSeason: string;
    seasons: string[] = ['Winter', 'Spring', 'Summer', 'Autumn'];
    step = 0;
    tiles: Tile[] = [
        { text: 'One', cols: 3, rows: 1, color: 'lightblue' },
        { text: 'Two', cols: 1, rows: 2, color: 'lightgreen' },
        { text: 'Three', cols: 1, rows: 1, color: 'lightpink' },
        { text: 'Four', cols: 2, rows: 1, color: '#DDBDF1' }
    ];*/

    // isLinear = false;
    // firstFormGroup: FormGroup
    // secondFormGroup: FormGroup;

    treeControl: FlatTreeControl<DynamicFlatNode>;

    dataSource: DynamicDataSource;

    getLevel = (node: DynamicFlatNode) => node.level;

    isExpandable = (node: DynamicFlatNode) => node.expandable;

    hasChild = (_: number, _nodeData: DynamicFlatNode) => _nodeData.expandable;

    constructor(
        // private _formBuilder: FormBuilder,
        database: DynamicDatabase
        // private companyService: CompanyService,
        // private jhiAlertService: JhiAlertService
    ) {
        this.treeControl = new FlatTreeControl<DynamicFlatNode>(this.getLevel, this.isExpandable);
        this.dataSource = new DynamicDataSource(this.treeControl, database);
        this.dataSource.data = database.initialData();
    }
    /*
    loadAllCompany() {
        this.companyService
            .query({
                page: 0,
                size: 20,
                sort: null
            })
            .subscribe(
                (res: HttpResponse<ICompany[]>) => this.paginateCompanies(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    protected paginateCompanies(data: ICompany[], headers: HttpHeaders) {
        this.companies = data;
        if (this.company == null && this.companies) {
            this.company = this.companies[0];
        }
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
/*
    setStep(index: number) {
        this.step = index;
    }

    nextStep() {
        this.step++;
    }

    prevStep() {
        this.step--;
    }*/
    ngOnInit() {
        /*
        this.filteredOptions = this.myControl.valueChanges.pipe(
            startWith(''),
            map(value => this._filter(value))
        );
        /*
        this.firstFormGroup = this._formBuilder.group({
            firstCtrl: ['', Validators.required]
        });
        this.secondFormGroup = this._formBuilder.group({
            secondCtrl: ['', Validators.required]
        });*/
        // this.loadAllCompany();
    }
    /*
    private _filter(value: string): string[] {
        const filterValue = value.toLowerCase();

        return this.options.filter(option => option.toLowerCase().includes(filterValue));
    }*/
}
