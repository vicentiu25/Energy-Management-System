import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {environment} from "../../environment/environment";

@Injectable({
  providedIn: 'root',
})
export class Device_apiService {
  constructor(private $http: HttpClient) { }

  get(url: string, options?: any): Observable<any> {
    return this.$http.get(`${environment.device_api.base}${url}`, options);
  }

  getRaw(url: string, options?: any): Observable<any> {
    return this.$http.get(`${url}`, options);
  }

  post(url: string, body: any | null, options?: any): Subscription {
    return this.$http.post(`${environment.device_api.base}${url}`, body, options).subscribe();
  }

  put(url: string, body: any | null, options?: any): Subscription {
    return this.$http.put(`${environment.device_api.base}${url}`, body, options).subscribe();
  }

  patch(url: string, body: any | null, options?: any): Subscription {
    return this.$http.patch(`${environment.device_api.base}${url}`, body, options).subscribe();
  }

  delete(url: string, options?: any): Subscription {
    return this.$http.delete(`${environment.device_api.base}${url}`, options).subscribe();
  }
}
