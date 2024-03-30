package com.reactive.api.flux.reactive.strams.tractor_api_flux_reactive_strams;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reactive.api.flux.reactive.strams.tractor_api_flux_reactive_strams.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class TractorApiFluxReactiveStramsApplication implements CommandLineRunner{ //Se implementa el CommandLineRunner para que se pueda ejecutar por consola
	private static final Logger log = LoggerFactory.getLogger(TractorApiFluxReactiveStramsApplication.class); 
	public static void main(String[] args) {
		SpringApplication.run(TractorApiFluxReactiveStramsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<String> usuariosList = new ArrayList<>();
		usuariosList.add("Andres Guzman");
		usuariosList.add("Pedro Fulano");
		usuariosList.add("Diego Sultano");
		usuariosList.add("Juan Mengano");
		usuariosList.add("Maria Fulana");
		usuariosList.add("Bruce Lee");
		usuariosList.add("Bruce Willis");

		Flux<String> nombresUsuariosList = Flux.fromIterable(usuariosList); //Crear un flux a partir de una lista
		
		//Recordar que para trabajar con flux se debe agregar en el pom las dependencias de Reactor
		Flux<Usuario> nombres = Flux.just("Andres Guzman", "Pedro Fulano", "Diego Sultano","Juan Mengano","Maria Fulana", "Bruce Lee","Bruce Willis")//Creamos obserbables con el flux y su metodo just, donde se le pasa la data
		.map(nombre -> new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase()))
		.filter(usuario -> usuario.getName().equalsIgnoreCase("Bruce")) //Filtramos por el nombre bruce
		.doOnNext(element ->{
			if(element == null){
				throw new RuntimeException("Nombres no se pueden vacios"); //manejo de errores dentro del doOnNext, esto termina el flujo
			}
				System.out.println(element.getName().concat(" ").concat(element.getLastname()));
			})//Esto es lo que va a hacer cuando nos subscribamos, notifica ccuanod haya llegado un elemento
			.map(usuario ->{
				String nombre = usuario.getName().toLowerCase();
				usuario.setName(nombre);
				return usuario;}); //Retornamos otra instancia del flux con los datos cambiados, el map sirve para generar otra instancia modificada, la posición del map afecta a la ejecución si se pone antes se mostraran en mayusculas , si se pone despues no, afectando solo a los métodos del subscribe
			

		nombres.subscribe(e-> log.info(e.getName()), error -> log.error(error.getMessage()), new Runnable() {

			@Override
			public void run() {
			
				log.info("Ha finalizado la ejecución del observable con éxito!");
			}
			
		}); //El subscribe no es solo para aplicar sino que puede hacer modificaciones en el método
		                 //Con el log hacemos que se imprima en el log cuando se hace el doOnNext
		//En los subcribe se pueden pasar 3 parametros, el codigo que queremos que se ejecute, el codigo del error y el codigo de cuando termina de ejecutarse, con el Runnable que es una interface
	}
		

}
