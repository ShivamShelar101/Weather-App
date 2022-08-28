package weatherplantwater;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Shivam
 */
public class WeatherPlantWaterTest{
    
    //apiHandlerTests
    
    @Test
    //testing the apiHandler class to confirm that it returns what we expect it to return by calling it and 
    //making sure the variable of the city is assigned with Fresno during the first run of the code. This verifies
    //that the code is parsing the Json file and assigning variables with values
    public void testapihandler() {
        try{
        
              StringBuilder result = new StringBuilder();
              URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=fresno,us&appid=14bc3c13c84f557708d9ecc439e10171&units=imperial");
              URLConnection conn = url.openConnection();
              try (BufferedReader rd = new BufferedReader(new InputStreamReader (conn.getInputStream()))) {
                  String line;
                  while ((line = rd.readLine()) != null){
                      result.append(line);//result is json file, system.out.print returned stuff
                  }
              }
              
          Map<String, Object > respMap = jsonToMap(result.toString());
          Map<String, Object > mainMap = (Map<String, Object >)respMap.get("main");
          Map<String, Object > sysMap = (Map<String, Object >)respMap.get("sys");
          String  nameMap = (String) respMap.get("name");
          List<Map<String, Object >> weather = (List<Map<String, Object>>) (respMap.get("weather"));
          Map<String, Object> weatherMap = weather.get(0);//grab from whole result
          
          assertEquals(nameMap, "San Jose");
        }
          catch (Exception e){
              System.out.println(e.getMessage());
              
          }
    }
    public static Map<String,Object> jsonToMap(String str){

        Map<String,Object> map = new Gson().fromJson(str,new TypeToken<HashMap<String,Object>> () {}.getType());
        return map;
    }
    
    
    
    //bad url/null test, inputting a bad url statement into the code, send something that the 
    //apiHandler is not expecting. Checking that the try/catch statements are working as they should
    //and handling the invalid URL
    @Test
    public void testbadURLAPIreturn() {
        String urlString = "badURL";
        int k;
        int numErrors = 0;
        
        try{
        
              StringBuilder result = new StringBuilder();
              URL url = new URL(urlString);
              URLConnection conn = url.openConnection();
              try (BufferedReader rd = new BufferedReader(new InputStreamReader (conn.getInputStream()))) {
                  String line;
                  while ((line = rd.readLine()) != null){
                      result.append(line);
                  }
              }
              
        numErrors++;      
              
          Map<String, Object > respMap = WeatherPlantWater.jsonToMap(result.toString());
          Map<String, Object > mainMap = (Map<String, Object >)respMap.get("main");
          Map<String, Object > sysMap = (Map<String, Object >)respMap.get("sys");
          String  nameMap = (String) respMap.get("name");
          List<Map<String, Object >> weather = (List<Map<String, Object>>) (respMap.get("weather"));
          Map<String, Object> weatherMap = weather.get(0);
          
         WeatherPlantWater.CityLabel.setText("City: " + nameMap); 
         WeatherPlantWater.CountryLabel.setText("Country: " + sysMap.get("country"));         
         WeatherPlantWater.WeatherDescriptionLabel.setText("Weather Description: "+ weatherMap.get("main"));          
         WeatherPlantWater.TemperatureLabel.setText("Current Temperature: " + mainMap.get("temp") +" F" );
         
         //use weather input to convert to  range of index from 0-1, depending on weather conditions
         Object description =  weatherMap.get("main");
        if (description.equals("Thunderstorm") || description.equals("Snow")||description.equals("Rain") || description.equals("Drizzle") ){
            k=1;
        }            
        else{
            k=0;
        }
 
          }catch (IOException e){
              assertEquals(numErrors, 0);
              System.out.println(e.getMessage());
          }
      
    }
    
    @Test
    //test to verify that k is indeed 0 or 1. As that is the range of output from the apiHandler that drives our user interface.
    public void testrangek() {
        int k;
        try{
        
              StringBuilder result = new StringBuilder();
              URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=fresno,us&appid=14bc3c13c84f557708d9ecc439e10171&units=imperial");
              URLConnection conn = url.openConnection();
              try (BufferedReader rd = new BufferedReader(new InputStreamReader (conn.getInputStream()))) {
                  String line;
                  while ((line = rd.readLine()) != null){
                      result.append(line);
                  }
              }
              
          Map<String, Object > respMap = jsonToMap(result.toString());
          Map<String, Object > mainMap = (Map<String, Object >)respMap.get("main");
          Map<String, Object > sysMap = (Map<String, Object >)respMap.get("sys");
          String  nameMap = (String) respMap.get("name");
          List<Map<String, Object >> weather = (List<Map<String, Object>>) (respMap.get("weather"));
          Map<String, Object> weatherMap = weather.get(0);//grab from whole result
          
         Object description =  weatherMap.get("main");
        if (description.equals("Thunderstorm") || description.equals("Snow")||description.equals("Rain") || description.equals("Drizzle") ){
            k=1;
            
        } 
        
        else{
            k=0;
        }
            assertTrue(k==0 || k==1);
        }
          catch (Exception e){
              System.out.println(e.getMessage());
              
          }
        
    }
    
    
}
