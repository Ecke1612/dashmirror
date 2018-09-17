package apis.weather;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

public class GetWeatherData {

    private String currWeaterURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final String key = "4e46f87c528cdc9447f329001b9d171a";
    private String city;
    private String temperature = "";
    private String tempMin = "";
    private String tempMax = "";
    private String wind = "";

    public GetWeatherData(String city) {
        this.city = city;
        //checkCurrentWeather();
    }

    public boolean checkCurrentWeather() {
        boolean dataArrived = false;
        URL url = null;
        try {
            url = new URL(currWeaterURL + city + "&appid=" + key + "&units=metric");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.connect();
            String inline = "";
            int responscode = con.getResponseCode();
            if(responscode !=200) {
                throw new RuntimeException("HttpResponseCode:"+responscode);
            } else {
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext()){
                    inline +=sc.nextLine();
                }
                System.out.println(inline);
                sc.close();
                dataArrived = true;
            }

            JSONParser parser = new JSONParser();

            JSONObject jobj = (JSONObject)parser.parse(inline);
            JSONObject mainObj = (JSONObject) jobj.get("main");
            JSONObject windObj = (JSONObject) jobj.get("wind");

            temperature = mainObj.get("temp").toString();
            tempMin = mainObj.get("temp_min").toString();
            tempMax = mainObj.get("temp_max").toString();
            wind = windObj.get("speed").toString();


            con.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataArrived;
    }

    public String getTemperature() {
        String[] split = temperature.split("\\.");
        return split[0] + "°C";
    }

    public String getTempMin() {
        String[] split = tempMin.split("\\.");
        return split[0] + "°C";
    }

    public String getTempMax() {
        String[] split = tempMax.split("\\.");
        return split[0] + "°C";
    }

    public String getWind() {
        double dwind = Double.parseDouble(wind);
        dwind = dwind * 3.6;
        return roundDoubleToString(dwind);
    }

    public String getWindInWords() {
        String sentence = "nicht erfasst";
        double w = Double.parseDouble(wind);
        if(w < 0.2) {
            sentence = "Stille";
        }else if(w >= 0.2 && w < 5.5) {
            sentence = "schwacher Wind";
        }else if(w >= 5.5 && w < 7.9) {
            sentence = "mäßiger Wind";
        }else if(w >= 7.9 && w < 10.7) {
            sentence = "frischer Wind";
        }else if(w >= 10.7 && w < 17.1) {
            sentence = "starker Wind";
        }else if(w >= 17.1 && w < 24.4) {
            sentence = "Sturm";
        }else if(w >= 24.4) {
            sentence = "schwerer Sturm";
        }
        return sentence;
    }

    private String roundDoubleToString(double value) {
        DecimalFormat df = new DecimalFormat("####0.00");
        return df.format(value);
    }
}
