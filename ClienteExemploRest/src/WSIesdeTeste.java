package br.org.unifenacon.iesde.main;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.org.unifenacon.iesde.MatriculaIesde;

public class WSIesdeTeste {

	public static void main(String[] args) throws Exception {
		HttpHost target = new HttpHost("ead.portalava.com.br", 80, "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();

		credsProvider.setCredentials(
				new AuthScope(target.getHostName(), target.getPort()),
				new UsernamePasswordCredentials("1590e99c63d124e374345de71205ddb7c63a0b8d", "afb94979f63f3038b84344d7ac37febe39748167"));

		HttpClient httpClient = HttpClientBuilder.create()
				/* HTTP/1.1 401 Unauthorized : Not authorized */
				.setDefaultCredentialsProvider(credsProvider)
				.build();
		
		HttpPost httpPost = new HttpPost("http://ead.portalava.com.br/web_service/getCursos/format/json");
		/* HTTP/1.1 403 Forbidden : Invalid API Key */
		httpPost.setHeader("EAD-API-KEY", "3bb4df5a6f308bc61760fdc05bd013e7");
		
		/*httpPost.setHeader("User-Agent", "Mozilla/5.0");
		httpPost.setHeader("Content-Type", "application/json; Charset=UTF-8");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Connection", "keep-alive");*/

		HttpResponse response = httpClient.execute(target, httpPost);

		String resposta = EntityUtils.toString(response.getEntity());

		Gson gson = new GsonBuilder().serializeNulls().create();
		MatriculaIesde matricula = new MatriculaIesde();
		matricula = gson.fromJson(resposta, MatriculaIesde.class);

		System.out.println("Executando solicitação " + httpPost.getRequestLine() + " para o alvo " + target);
		System.out.println("\nStatus WS IESDE: " + response.getStatusLine());

		System.out.println("\n--------------- RESPOSTA ---------------");
		System.out.println("Status : " + matricula.isStatus());
		System.out.println("Erro : " + matricula.getError());
		System.out.println("Mensagem : " + matricula.getMensagem());
	}

}
