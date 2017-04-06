
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.json.JSONObject;

import com.bruno.DTO.Pessoa;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.com.acfenacon.faturamento.vo.Pedido;


public class Teste {
	
	private static final String URL_CREDENTIALS = Base64.getEncoder().encodeToString(("usertest:user123456").getBytes());
	
	public static void main(String[] args) {
//		byte[] a = com.sun.jersey.core.util.Base64.encode("bruno:123".getBytes());
		try {
//			System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
//	        System.setProperty("javax.net.ssl.keyStore", "C:\\certificado\\" + "wscert.p12");
//	        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
			
	        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
	        System.setProperty("javax.net.ssl.trustStore", "C:\\certificado\\" + "wskeystore.jks");
	        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
	        
	        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	        	    new javax.net.ssl.HostnameVerifier(){

        	        public boolean verify(String hostname,
        	                javax.net.ssl.SSLSession sslSession) {
        	            if (hostname.equals("localhost")) {
        	                return true;
        	            }
        	            return false;
        	        }
	       });
	        
//		    consultaObjeto();
//		    consultaObjetoId();
//		    consultaString();
//		    consultaLista();
			incluiObjeto();
//			incluiArray();
//			delete();
//	        consultaListaBanco();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static WebResource conection(String service){
		
		Client c = Client.create();
	    WebResource wr = c.resource("https://localhost:8443/ExemploRest/teste" + service);
		return wr;
	}
	
	public static void consultaString(){
		String json = conection("/hello").header("Authorization", "Basic " + URL_CREDENTIALS).get(String.class);
		System.out.println(json);
	}
	
	public static void consultaObjeto(){
		String json = conection("/getObjetc").header("Authorization", "Basic " + URL_CREDENTIALS).get(String.class);
		Pessoa pessoa = new Gson().fromJson(json, Pessoa.class);
		//
		JSONObject obj = new JSONObject(json);
		System.out.println(obj.getString("nome"));
		System.out.println(obj.get("idade"));
		//
		System.out.println(json);
	}
	
	public static void consultaLista() throws IOException{
		System.out.println("início " + new Date());
		String json = conection("/getList").header("Authorization", "Basic " + URL_CREDENTIALS)
				.type("application/json")
				.accept("application/json")
				.get(String.class);
		ArrayList<Pessoa> lista = new ArrayList<>(Arrays.asList(new Gson().fromJson(json, Pessoa[].class)));
		
		for (Pessoa pessoa : lista) {
			System.out.println(pessoa.getNome());
		}
		System.out.println("Fim " + new Date());
	}
	
	public static void consultaObjetoId(){
		String json = conection("/buscar/20").header("Authorization", "Basic " + URL_CREDENTIALS).get(String.class);
		System.out.println(json);
	}
	
	public static void incluiObjeto(){
		Pessoa pessoa = new Pessoa();
		pessoa.setIdade(1);
		pessoa.setNome("Incluir");
		String json = new Gson().toJson(pessoa);
		ClientResponse response = conection("/incluir").type("application/json").header("Authorization", "Basic " + URL_CREDENTIALS)
			.post(ClientResponse.class,json);
		System.out.println(response.toString());
		
	}
	
	public static void incluiArray(){
		Pessoa pessoa = new Pessoa();
		pessoa.setIdade(1);
		pessoa.setNome("Incluir");
		
		Pessoa pessoa2 = new Pessoa();
		pessoa2.setIdade(2);
		pessoa2.setNome("Segunda");
		Pessoa [] lista = {pessoa,pessoa2};
		
		ArrayList<Pessoa> list2 = new ArrayList<Pessoa>();
		list2.add(pessoa);
		list2.add(pessoa2);
		
		Gson gson = new Gson();
		String jString = gson.toJson(list2);
		System.out.println(jString);
		
//		JsonObject jo = new JsonObject();
//		Set<Pessoa> set = new HashSet<>();
//		set.add(pessoa2);
//		set.add(pessoa);
//		JSONArray jsonArray = new JSONArray(list2);
//		System.out.println(jsonArray);
		
//		System.out.println(new Gson().toJson(Arrays.asList(lista)));
//		String jsonList = new Gson().toJson(lista, Pessoa[].class);
//		System.out.println(jsonList);
////		String json = new Gson().toJson(pessoa);
		ClientResponse response = conection("/incluir").type("application/json").header("Authorization", "Basic " + URL_CREDENTIALS)
			.post(ClientResponse.class,jString);
		System.out.println(response.toString());
		
	}
	
	public static void delete(){
		int id = 255;
		String json = conection("/delete/"+id).header("Authorization", "Basic " + URL_CREDENTIALS).delete(String.class);
		System.out.println(json);
	}
	
	public static void consultaListaBanco() throws IOException{
		System.out.println("início " + new Date());
		String json = conection("/getListBanco").header("Authorization", "Basic " + URL_CREDENTIALS)
				.type("application/json")
				.accept("application/json")
				.get(String.class);
		ArrayList<Pedido> lista = new ArrayList<>(Arrays.asList(new Gson().fromJson(json, Pedido[].class)));
		
		for (Pedido pedido : lista) {
			System.out.println(pedido.getNumero());
		}
		System.out.println("Fim " + new Date());
	}
	
	
}