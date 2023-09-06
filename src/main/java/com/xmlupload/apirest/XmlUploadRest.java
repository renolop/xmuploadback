package com.xmlupload.apirest;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RestController
@RequestMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
public class XmlUploadRest{
	
	@PostMapping
	public ResponseEntity<Object> uploadArquivoXml(@RequestParam("file") MultipartFile file) {
		try { 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file.getInputStream());
			
			//agentes
			NodeList listaAgentes = doc.getElementsByTagName("agente");
			for (int i = 0; i < listaAgentes.getLength(); i++) {
	              Node agente = listaAgentes.item(i);
	              if(agente.getNodeType() == Node.ELEMENT_NODE) {
	            	  Element agenteEl = (Element) agente;
	            	  System.out.println( agenteEl.getElementsByTagName("codigo").item(0).getTextContent() );
	              }
	        }
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		Map<String, String> resposta = new HashMap<>();
		resposta.put("message", "Arquivo Carregado com Sucesso");
		return new ResponseEntity<Object>(resposta, HttpStatus.OK);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
	      .permitAll()).csrf().disable().cors();
	    return http.build();
	}
}