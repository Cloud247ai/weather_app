import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.net.URI;
import java.net.http.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {

    private static final String API_KEY = "46bc6f134cmsh621a58e2b7374e3p1861acjsn0a1cb1d3dd6a";
    private static final String API_HOST = "weather-api167.p.rapidapi.com";
    private static final String API_URL = "https://weather-api167.p.rapidapi.com/api/weather/forecast";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String place = request.getParameter("place");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (place == null || place.trim().isEmpty()) {
            out.println("<h3>Please enter a valid location</h3>");
            return;
        }

        try {
            String json = fetchWeatherData(place);
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();

            JsonObject city = root.getAsJsonObject("city");
            JsonObject firstData = root.getAsJsonArray("list").get(0).getAsJsonObject();
            JsonObject main = firstData.getAsJsonObject("main");
            JsonObject weather = firstData.getAsJsonArray("weather").get(0).getAsJsonObject();

            String cityName = city.get("name").getAsString();
            String country = city.get("country").getAsString();
            String description = weather.get("description").getAsString();
            double temperature = main.get("temprature").getAsDouble(); 
            int humidity = main.get("humidity").getAsInt();
            
            String time = firstData.get("dt_txt").getAsString();
            String bgImage = getBackgroundByWeather(description);

            // Display output
            out.println("<html><head><title>Weather Report</title>");
            out.println("<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css'>");
            out.println("<style>");
            out.println("body { margin: 0; padding: 0; font-family: 'Segoe UI', sans-serif; height: 100vh; display: flex; align-items: center; justify-content: center;");
            out.println("background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('" + bgImage + "') no-repeat center center fixed; background-size: cover; }");
            out.println(".weather-card { background: rgba(255, 255, 255, 0.1); border-radius: 15px; padding: 30px; color: #fff; width: 350px; backdrop-filter: blur(10px); box-shadow: 0 8px 32px 0 rgba( 31, 38, 135, 0.37 ); text-align: center; }");
            out.println(".weather-card h2 { font-weight: bold; }");
            out.println(".weather-icon { width: 100px; }");
            out.println(".btn-custom { margin-top: 20px; background-color: #0dcaf0; border: none; }");
            out.println("</style>");
            out.println("</head><body>");

            out.println("<div class='weather-card'>");
            
            out.println("<h2>" + cityName + ", " + country + "</h2>");
            out.println("<p><strong>" + description.substring(0, 1).toUpperCase() + description.substring(1) + "</strong></p>");
            out.println("<p>🌡 Temperature: " + temperature + " K</p>");
            out.println("<p>💧 Humidity: " + humidity + "%</p>");
            out.println("<p>🕒 Time: " + time + "</p>");
            out.println("<a href='index.jsp' class='btn btn-custom'>Search Again</a>");
            out.println("</div>");

            out.println("</body></html>");


        } catch (Exception e) {
            out.println("<p>Error fetching weather: " + e.getMessage() + "</p>");
            e.printStackTrace();
        }
    }

    private String fetchWeatherData(String place) throws IOException, InterruptedException {
        String url = API_URL + "?place=" + place + "&cnt=1&units=standard&type=three_hour&mode=json&lang=en";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("x-rapidapi-key", API_KEY)
            .header("x-rapidapi-host", API_HOST)
            .header("Accept", "application/json")
            .GET().build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    private String getBackgroundByWeather(String description) {
        String desc = description.toLowerCase();
        if (desc.contains("cloud")) return "https://i.pinimg.com/originals/6a/62/20/6a6220cf08d104335ab53dd59c7dce62.gif";
        else if (desc.contains("rain")) return "https://images.squarespace-cdn.com/content/v1/5fe4caeadae61a2f19719512/1721115271529-K4JVTGV28BE2PHNN4RRC/18.gif";
        else if (desc.contains("clear")) return "https://i.pinimg.com/originals/cd/0c/3f/cd0c3f12008404cae0a8cbc20e880d21.gif";
        else return "https://wallpapercave.com/wp/wp2757829.jpg"; // default background
    }
}
