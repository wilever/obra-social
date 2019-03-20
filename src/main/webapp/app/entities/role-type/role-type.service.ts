import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoleType } from 'app/shared/model/role-type.model';

type EntityResponseType = HttpResponse<IRoleType>;
type EntityArrayResponseType = HttpResponse<IRoleType[]>;

@Injectable({ providedIn: 'root' })
export class RoleTypeService {
    public resourceUrl = SERVER_API_URL + 'api/role-types';

    constructor(protected http: HttpClient) {}

    create(roleType: IRoleType): Observable<EntityResponseType> {
        return this.http.post<IRoleType>(this.resourceUrl, roleType, { observe: 'response' });
    }

    update(roleType: IRoleType): Observable<EntityResponseType> {
        return this.http.put<IRoleType>(this.resourceUrl, roleType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRoleType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRoleType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
