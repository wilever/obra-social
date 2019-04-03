import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IModuleType } from 'app/shared/model/module-type.model';

type EntityResponseType = HttpResponse<IModuleType>;
type EntityArrayResponseType = HttpResponse<IModuleType[]>;

@Injectable({ providedIn: 'root' })
export class ModuleTypeService {
    public resourceUrl = SERVER_API_URL + 'api/module-types';

    constructor(protected http: HttpClient) {}

    create(moduleType: IModuleType): Observable<EntityResponseType> {
        return this.http.post<IModuleType>(this.resourceUrl, moduleType, { observe: 'response' });
    }

    update(moduleType: IModuleType): Observable<EntityResponseType> {
        return this.http.put<IModuleType>(this.resourceUrl, moduleType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IModuleType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IModuleType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
