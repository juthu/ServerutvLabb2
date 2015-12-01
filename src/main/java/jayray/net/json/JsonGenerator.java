package jayray.net.json;

import com.google.gson.Gson;

public final class JsonGenerator {
/*
 * this class is for generating JSON	
 */
	
	/*
	 * initialization of Gson class 
	 */
	private static Gson gson = new Gson();
	
	/*
	 * This class is for generating JSON from MODEL class
	 * @param Object
	 * @return String 
	 */
	public static String generateJson(Object to) {
		return null == to ? "" : gson.toJson(to);
	}

	/*
	 * Generates the transfer object from the given JSON using Google Gson.
	 * 
	 * @param String
	 * @return transfer object
	 */
	public static Object generateTOfromJson(String json, Class<?> class1) {
		return gson.fromJson(json, class1);
	}
	
	/*
	 * this method is for generating error json
	 * @param exception - Exception
	 * @return String
	 */
	public static String generateErrorJson(Exception exception) {
		
		ErrorDTO errorTO = new ErrorDTO();
		errorTO.setErrorCode(-1);
		errorTO.setErrorMessage(exception.getMessage());
        return generateJson(errorTO);
	}

	/*
	 * Sets the error details to the ResponseDTO from the given Exception using Google Gson.
	 * @param String
	 * @return String
	 */
	public static String generateSuccessJson(String message) {
		SuccessDTO successTO = new SuccessDTO();
		successTO.setCode(0);
		successTO.setMessage(message);
        return generateJson(successTO);
	}
}
