import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IColumn } from 'app/shared/model/column.model';

type EntityResponseType = HttpResponse<IColumn>;
type EntityArrayResponseType = HttpResponse<IColumn[]>;

@Injectable({ providedIn: 'root' })
export class ColumnService {
    public resourceUrl = SERVER_API_URL + 'api/columns';

    constructor(protected http: HttpClient) {}

    create(column: IColumn): Observable<EntityResponseType> {
        return this.http.post<IColumn>(this.resourceUrl, column, { observe: 'response' });
    }

    update(column: IColumn): Observable<EntityResponseType> {
        return this.http.put<IColumn>(this.resourceUrl, column, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IColumn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IColumn[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
