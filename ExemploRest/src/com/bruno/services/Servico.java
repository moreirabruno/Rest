package com.bruno.services;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.bruno.DTO.Pessoa;
import com.google.gson.Gson;

/**
 * asdfasdfasdfasdf
 * @author bruno.moreira
 *
 */
@Path("teste")
public class Servico {
	
	private HashMap<Integer, Pessoa> registros;
	
	/**
	 * Construtor
	 * @author bruno.moreira
	 */
	public Servico() {
		Pessoa pessoa = new Pessoa();
		pessoa.setIdade(10);
		pessoa.setNome("Teste Bruno");
		
		Pessoa pessoa2 = new Pessoa();
		pessoa2.setIdade(20);
		pessoa2.setNome("Teste Bruno 2");
		
		registros = new HashMap<>();
		
		registros.put(pessoa.getIdade(), pessoa);
		registros.put(pessoa2.getIdade(), pessoa2);
		
	}

	/**
	 * Teste de comentário
	 * @author bruno.moreira
	 * @return String
	 */
	@GET
	@Path("/hello")
	@Produces("application/json")
	public String Hello(){
		return "TESTANDO 123";
	}
	
	/**
	 * Teste de comentário
	 * @author bruno.moreira
	 * @return String
	 */
	@GET
	@Path("/getObjetc")
	public String getObjeto(){
		
		Pessoa pessoa = new Pessoa();
		pessoa.setIdade(10);
		pessoa.setNome("Teste Bruno");
		
		String json = new Gson().toJson(pessoa);
		
		return json;
		
	}
	
	@GET
	@Path("/getList")
	public String getList(){
		
		ArrayList<Pessoa> lista = new ArrayList<>();
		
		for(int i = 0; i < 10000; i++){
			Pessoa pessoa = new Pessoa();
			pessoa.setIdade(i);
			pessoa.setNome("Teste Bruno" +i);
			lista.add(pessoa);
		}
		
		
//		Pessoa pessoa2 = new Pessoa();
//		pessoa2.setIdade(20);
//		pessoa2.setNome("Teste Bruno 2");
//		
//		List lista = new ArrayList<Pessoa>();
//		lista.add(pessoa);
//		lista.add(pessoa2);
		
		String json = new Gson().toJson(lista);
		
		return json;
		
	}
	
	/**
	 * Testest 
	 * @param json
	 * @return Response
	 */
	@POST
	@Path("/incluir")
	@Consumes("application/json")
//	@Produces("text/plain")
	public Response incluir(String json){
		
//		Pessoa pessoa = new Gson().fromJson(json, Pessoa.class);
		System.out.println("AAAAAAAAAA");
		
		return Response.status(201).entity(json).build();
	}
	
	/**
	 * kakakakak
	 * @param idade
	 * @return String
	 */
	@GET
	@Path("/buscar/{id}")
	public String buscaId(@PathParam(value = "id") String idade){
		
		registros.remove(Integer.parseInt(idade));
		
		return "Registro excluído";
	}
	
	@DELETE
	@Path("/delete/{id}")
	public String delete(@PathParam(value = "id") String id){
		
		System.out.println("Deletado : " + id);
		
		return "Registro excluído";
	}
	
	
	
//	public static void main(String[] args) {
//		Pessoa pessoa = new Pessoa();
//		pessoa.setIdade(10);
//		pessoa.setNome("Teste Bruno");
//		
//		String gson = new Gson().toJson(pessoa);
//		System.out.println(gson);
//	}
	
	
	
}
