import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { XmlUploadComponent } from './xml-upload/xml-upload/xml-upload.component';

const routes: Routes = [
  {
    path: '',
    component: XmlUploadComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
