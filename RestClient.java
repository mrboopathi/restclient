
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.security.cert.Certificate;

public class RestClient {

	public static void main(String[] args) throws IOException, JSONException, ParseException{
		
			Scanner scanner = new Scanner(System.in);
 	  		String name ="";// scanner.nextLine();
	 		String jsonString = getPersonData(name);
	 		JSONParser parser = new JSONParser();
			JSONArray array= (JSONArray) parser.parse(jsonString);
	 		for(int i=0;i<array.size();i++)
			{
				JSONObject jsonobj_1 = (JSONObject)array.get(i);
				System.out.println("\n title : " +jsonobj_1.get("title"));
			}
	 		scanner.close();
	 		
	}
	
	public static String getPersonData(String id) throws IOException{
        
	 	 String https_url = "https://jsonplaceholder.typicode.com/todos";
	 	 URL url = null;
		 url = new URL(https_url);
		 HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		 System.out.println("Response Code : " + con.getResponseCode());
		 System.out.println("Cipher Suite : " + con.getCipherSuite());
		 System.out.println("\n");
		
		Certificate[] certs = con.getServerCertificates();
		
	    for(Certificate cert : certs){
	       System.out.println("Cert Type : " + cert.getType());
	       System.out.println("Cert Hash Code : " + cert.hashCode());
	       System.out.println("Cert Public Key Algorithm : " 
	                                    + cert.getPublicKey().getAlgorithm());
	       System.out.println("Cert Public Key Format : " 
	                                    + cert.getPublicKey().getFormat());
	       System.out.println("\n");
	    }
	    
	    int responseCode = con.getResponseCode();
		if(responseCode == 200){
			String response = "";
			Scanner scanner = new Scanner(con.getInputStream());
			while(scanner.hasNextLine()){
				response += scanner.nextLine();
				response += "\n";
			}
			scanner.close();

			return response;
		}
		
		// an error happened
		return null;
	}

	public static void setPersonData(String name, String birthYear, String about,int id) throws IOException{
		HttpURLConnection connection = (HttpURLConnection) new URL("https://reqres.in/api/products/" + id).openConnection();

		connection.setRequestMethod("POST");
		
		String postData = "name=" + URLEncoder.encode(name);
		postData += "&about=" + URLEncoder.encode(about);
		postData += "&birthYear=" + birthYear;
		
		connection.setDoOutput(true);
	    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
	    wr.write(postData);
	    wr.flush();
		
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			System.out.println("POST was successful.");
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
}