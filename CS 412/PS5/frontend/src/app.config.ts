import {Injectable} from "@angular/core";
import {HttpClient} from '@angular/common/http';
import {environment} from './environments/environment';
import {IConfig} from './assets/config/config.mode';

@Injectable()
export class AppConfig {
  static settings: IConfig;

  constructor(private http: HttpClient) {
  }

  load() {
    const jsonFile = `assets/config/config.${environment.name}.json`;
    return new Promise<void>((resolve, reject) => {
      this.http.get(jsonFile).toPromise().then((response) => {
        AppConfig.settings = <IConfig>response;
        resolve();
      }).catch((response: any) => {
        reject(`Could not load file '${jsonFile}': ${JSON.stringify(response)}`);
      });
    });
  }
}
