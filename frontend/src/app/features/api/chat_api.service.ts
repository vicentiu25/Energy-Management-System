import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, Subscription} from "rxjs";
import {environment} from "../../environment/environment";

@Injectable({
  providedIn: 'root',
})
export class Chat_apiService {
  constructor(private $http: HttpClient) { }

  post(url: string, body: any | null, options?: any): Subscription {
    return this.$http.post(`${environment.chat_api.base}${url}`, body, options).subscribe();;
  }
}
