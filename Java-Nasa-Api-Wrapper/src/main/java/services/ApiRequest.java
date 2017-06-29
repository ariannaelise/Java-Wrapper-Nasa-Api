package services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApiRequest {
	/**
	 * Gateway to NASA open API service(s).
	 */
	public static final String apiKey = System.getenv("NASA_API_KEY");
	
	/**
	 * Returns the HTTP response in the form of JSON.
	 * 
	 * @param url
	 * @return The HTTP response for the HTTP GET request.
	 */
	private void ApiRequest(String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// Using test url.
			HttpGet httpGet = new HttpGet("http://httpbin.org/");
			System.out.println("Executing request " + httpGet.getRequestLine());
			
			// Create a custom response handler.
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			
			    public String handleResponse(
			    		final HttpResponse response) throws ClientProtocolException, IOException {
			    	// Handling HTTP status codes.
			    	int status = response.getStatusLine().getStatusCode();
			    	if(status >= 200 && status < 300) {
			    		HttpEntity entity = response.getEntity();
			    		return entity != null ? EntityUtils.toString(entity) : null;
			    	} else {
			    		throw new ClientProtocolException("Unexpected response satus: " + status);
			    	}
			    }
			};
		    String responseBody = httpClient.execute(httpGet, responseHandler);
		    System.out.println("----------------------------------------");
		    System.out.println(responseBody);
		} finally {
			httpClient.close();
		}
	}
	/**
	 * 
	 * @return The NASA API key set in the environment variables.
	 */
	private String GetApiKey() {
		return apiKey;
	}
	
}
