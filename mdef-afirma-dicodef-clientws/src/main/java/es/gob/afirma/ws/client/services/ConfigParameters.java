package es.gob.afirma.ws.client.services;

public class ConfigParameters {

	private static String URL;
	
	private static String USER;
	
	private static String PASSWORD;

	public static void inicializeValues(String url, String user, String password){
		URL=url;
		USER=user;
		PASSWORD=password;
	}
	
	public static String  getURL(){
		return URL;
	}
	
	public static String  getUSER(){
		return USER;
	}

	public static String  getPASSWORD(){
		return PASSWORD;
	}
	
}
