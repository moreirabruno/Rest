package br.org.unifenacon.iesde.main;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.unifenacon.iesde.MatriculaIesde;

public class MatriculaMain implements Serializable {
	
	private static final long serialVersionUID = 1907556145444460581L;

	public static void main(String[] args) {
		String user = "1590e99c63d124e374345de71205ddb7c63a0b8d";
		String pass = "afb94979f63f3038b84344d7ac37febe39748167";
		String apiKeyHeader = "EAD-API-KEY";
		String apiKey = "3bb4df5a6f308bc61760fdc05bd013e7";
		String format = "json";
		String URL_WS = "http://ead.portalava.com.br/web_service/getMatriculas";
		
		HttpClient client = new HttpClient();
		client.getParams().setAuthenticationPreemptive(true);
		
		Credentials credentials = new UsernamePasswordCredentials(user, pass);
		client.getState().setCredentials(AuthScope.ANY, credentials);
		
		HttpMethod method = new PostMethod(URL_WS + "/format/" + format);
		method.addRequestHeader("Content-Type", "application/json");
		method.addRequestHeader("Accept", "application/json");
		// Erro pode está aqui
//		method.addRequestHeader("User-Agent", "Jakarta Commons-HttpClient/3.1");
		method.addRequestHeader(apiKeyHeader, apiKey);
		
		try {
			int status = client.executeMethod(method);
			System.out.println("\nStatus: " + status);
			
			String resposta = method.getResponseBodyAsString();
			Gson gson = new GsonBuilder().serializeNulls().create();
			MatriculaIesde matricula = new MatriculaIesde();
			matricula = gson.fromJson(resposta, MatriculaIesde.class);
			
			System.out.println("Detalhe do Status : " + method.getStatusLine());
			System.out.println("\n--------------- RESPOSTA ---------------");
			System.out.println("Status : " + matricula.isStatus());
			System.out.println("Erro : " + matricula.getError());
			System.out.println("Mensagem : " + matricula.getMensagem());
		} catch (HttpException e) {
			System.err.println("\nFatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("\nFatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		
	}

}
