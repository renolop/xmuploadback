import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, catchError, concatAll, map, of } from 'rxjs';

@Component({
  selector: 'app-xml-upload',
  templateUrl: './xml-upload.component.html',
  styleUrls: ['./xml-upload.component.scss']
})
export class XmlUploadComponent {

  myFiles:File [] = [];
  
  myForm = new FormGroup({
   name: new FormControl('', [Validators.required, Validators.minLength(3)]),
   file: new FormControl('', [Validators.required])
 });
   
 mostrarLoader = false;
 desabilitaBotao = false;

 constructor(private http: HttpClient) { }
         
 onFileChange(event:any) {
       for (var i = 0; i < event.target.files.length; i++) { 
           this.myFiles.push(event.target.files[i]);
       }
 }
     
 submit(){
   this.mostrarLoader = true;
   this.desabilitaBotao = true;

   // para mostrar o spinner
   setTimeout(() => {this.submeter()}, 2000);
    
 }

 submeter(){
  
  let formData = new FormData();

  let listRequest: Observable<Object>[] = [];

  for (var i = 0; i < this.myFiles.length; i++) { 
    this.removePrecoMedio(this.myFiles[i]);
    formData = new FormData();
    formData.append("file", this.myFiles[i]);
    
    let headers= new HttpHeaders();
    headers = headers.append('Access-Control-Allow-Origin', '*');

    //list de observables para serem executados sequencialmente fora do laço
     listRequest.push( this.http.post('http://localhost:8080/upload', formData, {'headers':headers} ) );
  }

  //executando requisições
  const listObservables = of(...listRequest);
  let teveErro: boolean = false;
  listObservables.pipe(concatAll()).subscribe( resp => { this.mostrarLoader = false;    this.desabilitaBotao = false; this.myForm.reset(); this.myFiles=[]; } );
   
 }

 removePrecoMedio( file: any ) {
  let reader = new FileReader();
  reader.readAsText( file );
  reader.onload = this.lerArquivo.bind(this);
 }

  lerArquivo( evento: any ){
    let parser = new DOMParser();
    let arquivoXml = parser.parseFromString(evento.target.result, "text/xml") as XMLDocument;
    let root = arquivoXml.documentElement;

    var childrenAgentes = root.children
    for( var i = 0; i < childrenAgentes.length; i++ ) {
        var childrenSubmercado = childrenAgentes[i].children;
        for( var j = 0; j < childrenSubmercado.length; j++ ) {
          var children = childrenSubmercado[j].children;
          for( var k = 0; k < children.length; k++ ) {
             if( children[k].nodeName == "preco-medio" ) {
              children[k].textContent = '';
             }
          }
        }
    }

  }

}
