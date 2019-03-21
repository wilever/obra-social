import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProvider } from 'app/shared/model/provider.model';

type EntityResponseType = HttpResponse<IProvider>;
type EntityArrayResponseType = HttpResponse<IProvider[]>;

@Injectable({ providedIn: 'root' })
export class ProviderService {
    public resourceUrl = SERVER_API_URL + 'api/providers';

    constructor(protected http: HttpClient) {}

    create(provider: IProvider): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(provider);
        return this.http
            .post<IProvider>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(provider: IProvider): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(provider);
        return this.http
            .put<IProvider>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProvider>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProvider[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(provider: IProvider): IProvider {
        const copy: IProvider = Object.assign({}, provider, {
            startDate: provider.startDate != null && provider.startDate.isValid() ? provider.startDate.format(DATE_FORMAT) : null,
            endDate: provider.endDate != null && provider.endDate.isValid() ? provider.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((provider: IProvider) => {
                provider.startDate = provider.startDate != null ? moment(provider.startDate) : null;
                provider.endDate = provider.endDate != null ? moment(provider.endDate) : null;
            });
        }
        return res;
    }
}
