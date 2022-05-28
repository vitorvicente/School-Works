import {Component} from '@angular/core';

import {Request} from './request';
import {CurrencyService} from "../services/currency.service";
import {AppConfig} from "../../app.config";

@Component({
  selector: 'app-request-form', templateUrl: './request-form.component.html'
})

export class RequestFormComponent {

  constructor(private currencyService: CurrencyService) {
  }

  currencies = AppConfig.settings.requestFormBase.currencyList;
  baseCurrencyTitle = AppConfig.settings.requestFormComponent.baseCurrencyTitle;
  requestedCurrencyTitle = AppConfig.settings.requestFormComponent.requestedCurrencyTitle;
  buttonText = AppConfig.settings.requestFormComponent.buttonText;


  model = new Request(AppConfig.settings.requestFormBase.defaultBaseCurrency,
    AppConfig.settings.requestFormBase.defaultRequestedCurrency,
    AppConfig.settings.requestFormBase.defaultResponse);

  onSubmit() {
    this.currencyService.getRequestAnswer(this.model).subscribe(response => {
      this.model.response = "1 " + this.model.baseCurrency + " = " + response.conversion_rate.toString()
        + " " + this.model.expectedCurrency;
    })
  }

}
