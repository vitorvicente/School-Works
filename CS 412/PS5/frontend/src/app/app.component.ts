import { Component } from '@angular/core';
import {AppConfig} from "../app.config";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = AppConfig.settings.general.title;
  headerText = AppConfig.settings.app.headerText;
}
