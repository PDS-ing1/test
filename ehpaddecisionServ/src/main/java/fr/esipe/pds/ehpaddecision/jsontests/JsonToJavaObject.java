package fr.esipe.pds.ehpaddecision.jsontests;

// this package should be removed one time the test has been ran
// TODO remove package jsontests
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToJavaObject {
	public static void main(String[] args) {
		JsonToJavaObject obj = new JsonToJavaObject();
		obj.run();
	}

	private void run() {
		ObjectMapper mapper = new ObjectMapper();
		try {

			// Convert JSON string from file to Object
		//	Staff staff = mapper.readValue(new File("D:/Divers/PdsIng1/staff.json"), Staff.class);
			//System.out.println(staff);

			// Convert JSON string to Object
			String jsonInString = "{\"name\":\"mkyong\",\"salary\":7500,\"skills\":[\"java\",\"python\"]}";
			Staff staff1 = mapper.readValue(jsonInString, Staff.class);
			System.out.println(staff1);

			//Pretty print
			String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(staff1);
			System.out.println(prettyStaff1);
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
