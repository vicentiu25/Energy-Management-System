import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {environment} from "../../environment/environment";

@Injectable({
  providedIn: 'root',
})
export class User_apiService {
  constructor(private $http: HttpClient) { }

  get(url: string, options?: any): Observable<any> {
    return this.$http.get(`${environment.user_api.base}${url}`, options);
  }

  getRaw(url: string, options?: any): Observable<any> {
    return this.$http.get(`${url}`, options);
  }

  post(url: string, body: any | null, options?: any): Observable<any> {
    return this.$http.post(`${environment.user_api.base}${url}`, body, options);
  }

  put(url: string, body: any | null, options?: any): Subscription {
    return this.$http.put(`${environment.user_api.base}${url}`, body, options).subscribe();
  }

  patch(url: string, body: any | null, options?: any): Observable<any> {
    return this.$http.patch(`${environment.user_api.base}${url}`, body, options);
  }

  delete(url: string, options?: any): Subscription {
    console.log(`${environment.user_api.base}${url}`)
    return this.$http.delete(`${environment.user_api.base}${url}`, options).subscribe();
  }
}
