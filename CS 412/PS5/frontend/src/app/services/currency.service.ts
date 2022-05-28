import {Injectable} from '@angular/core';
import {Request} from '../components/request';
import {HttpClient} from '@angular/common/http';
import {Response} from "../components/response";
import {AppConfig} from "../../app.config";

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {

  private requestEndpoint = AppConfig.settings.services.requestEndpoint;

  constructor(private httpClient: HttpClient) {
  }

  getRequestAnswer(request: Request) {
    return this.httpClient.get<Response>(this.requestEndpoint + `/${request.baseCurrency}` + `/${request.expectedCurrency}`);
  }

}
