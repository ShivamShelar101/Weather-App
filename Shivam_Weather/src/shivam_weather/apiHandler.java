package weatherplantwater;

import java.util.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author shivam
 */
public class apiHandler extends WeatherPlantWater{
    //range output for API
    
    
public int apiHandle(String urlString){   
    
    try{
       //Read from URL, extract data from source
              StringBuilder result = new StringBuilder();
              URL url = new URL(urlString);
              URLConnection conn = url.openConnection();
              try (BufferedReader rd = new BufferedReader(new InputStreamReader (conn.getInputStream()))) {
                  String line;
                  while ((line = rd.readLine()) != null){
                      result.append(line);//result is json file
                  }
              }
          
          //parse data, and extract certain values from overall data extraction
          Map<String, Object > respMap = WeatherPlantWater.jsonToMap(result.toString());
          Map<String, Object > mainMap = (Map<String, Object >)respMap.get("main");
          Map<String, Object > sysMap = (Map<String, Object >)respMap.get("sys");
          String  nameMap = (String) respMap.get("name");
          List<Map<String, Object >> weather = (List<Map<String, Object>>) (respMap.get("weather"));
          Map<String, Object> weatherMap = weather.get(0);//grab from whole result
         
         //Assign data such as city, country, weatherdescription, and temperature to labels
         WeatherPlantWater.CityLabel.setText("City: " + nameMap); 
         WeatherPlantWater.CountryLabel.setText("Country: " + sysMap.get("country"));         
         WeatherPlantWater.WeatherDescriptionLabel.setText("Weather Description: "+ weatherMap.get("main"));          
         WeatherPlantWater.TemperatureLabel.setText("Current Temperature: " + mainMap.get("temp") +" F" );
         
         //use weather input to convert to k to a range of index from 0-1, depending on weather conditions
         Object description =  weatherMap.get("main");
        if (description.equals("Thunderstorm") || description.equals("Snow")||description.equals("Rain") || description.equals("Drizzle") ){
            k=1;
        }            
        else{
            k=0;
        }
 
          }catch (IOException e){
           
              System.out.println(e.getMessage());
          }
    return k;//return k to use to drive user interface
    }

}


