import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class FlightLabsTest {
    private static final String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiMzY1MzI2MDJhMTQ4Y2JhMzZjNzBiN2FkMDVmNmUzYzMyOTgyMjFhN2YwM2I3ZmYwODczMGVkN2JhOTNiNDNhOGRkYjliMzZmYjBhYjI2OGIiLCJpYXQiOjE2NTcyOTU1OTcsIm5iZiI6MTY1NzI5NTU5NywiZXhwIjoxNjg4ODMxNTk3LCJzdWIiOiI4MTY0Iiwic2NvcGVzIjpbXX0.LyGPBxWw1poqoqxbVnlDWP8JY6G5oaEJu1-wG2E3zyj8iicgzRhiqf8huV5Fnw_uZ4GysNZg_Z6wY6KFuOSoQA";

    public static void main(String[] args) {
        try {

            URL url = new URL("https://app.goflightlabs.com/flights?access_key=" + apiKey + "&dep_iata=LHR&arr_iata=FRA&flight_status=scheduled&arr_scheduled_time_arr=2022-07-13");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder response = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }

                //Close the scanner
                scanner.close();

                String inline = response.toString();

                ObjectMapper mapper = new ObjectMapper();
                JsonNode flightJson = mapper.readTree(inline);
//                System.out.println(flightJson.toPrettyString());

                ArrayNode arrayNode = (ArrayNode) flightJson;
                for(int i = 0; i < arrayNode.size(); i++) {
                    JsonNode arrayElement = arrayNode.get(i);
                    JsonNode flightDate = arrayElement.get("flight_date");
                    JsonNode departureAirport = arrayElement.at("/departure/airport");
                    JsonNode departureScheduled = arrayElement.at("/departure/scheduled");
                    JsonNode arrivalAirport = arrayElement.at("/arrival/airport");
                    JsonNode arrivalScheduled = arrayElement.at("/arrival/scheduled");
                    JsonNode airlineName = arrayElement.at("/airline/name");
                    JsonNode flightNumber = arrayElement.at("/flight/iata");

                    System.out.println("Date: " + flightDate.toString() + ", " +
                            "Departure Airport: " + departureAirport.toString() + ", " +
                            "Departure Time: " + departureScheduled.toString().substring(12, 17) + ", " +
                            "Arrival Airport: " + arrivalAirport.toString() + ", " +
                            "Arrival Time: " + arrivalScheduled.toString().substring(12, 17) + ", " +
                            "Airline Name: " + airlineName.toString() + ", " +
                            "Flight Number: " + flightNumber.toString() );

                }

                }

    } catch (Exception e) {
        e.printStackTrace();
        }
    }

}
