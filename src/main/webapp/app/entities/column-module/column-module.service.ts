import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IColumnModule } from 'app/shared/model/column-module.model';

type EntityResponseType = HttpResponse<IColumnModule>;
type EntityArrayResponseType = HttpResponse<IColumnModule[]>;

@Injectable({ providedIn: 'root' })
export class ColumnModuleService {
    public resourceUrl = SERVER_API_URL + 'api/column-modules';

    constructor(protected http: HttpClient) {}

    create(columnModule: IColumnModule): Observable<EntityResponseType> {
        return this.http.post<IColumnModule>(this.resourceUrl, columnModule, { observe: 'response' });
    }

    update(columnModule: IColumnModule): Observable<EntityResponseType> {
        return this.http.put<IColumnModule>(this.resourceUrl, columnModule, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IColumnModule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IColumnModule[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
