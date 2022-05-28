export interface IConfig {
  general: {
    title: string;
  };
  app: {
    headerText: string;
  };
  services: {
    requestEndpoint: string;
  };
  requestFormBase: {
    defaultBaseCurrency: string;
    defaultRequestedCurrency: string;
    defaultResponse: string;
    currencyList: Array<string>;
  };
  requestFormComponent: {
    baseCurrencyTitle: string;
    requestedCurrencyTitle: string;
    buttonText: string;
  };
}
