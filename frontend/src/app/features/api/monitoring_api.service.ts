import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {environment} from "../../environment/environment";

@Injectable({
  providedIn: 'root',
})
export class Monitoring_apiService {
  constructor(private $http: HttpClient) { }

  get(url: string, options?: any): Observable<any> {
    return this.$http.get(`${environment.monitoring_api.base}${url}`, options);
  }
}
