package main;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class App {

    private String currWeaterURL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final String key = "4e46f87c528cdc9447f329001b9d171a";

    public App() {
        checkCurrentWeather();
    }

    private void checkCurrentWeather() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter City: ");
        String city = scanner.nextLine();

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
                System.out.println("\n JSON data in string format");
                System.out.println(inline);
                sc.close();
            }

            JSONParser parser = new JSONParser();

            JSONObject jobj = (JSONObject)parser.parse(inline);
            //JSONArray jsonArray = (JSONArray) jobj.get("weather");
            JSONObject mainObj = (JSONObject) jobj.get("main");
            System.out.print("In " + city + " ist es " + mainObj.get("temp") + " grad.");
            //System.out.println(mainObj.get("temp"));

            /*for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject sepJobj = (JSONObject) jsonArray.get(i);
            }*/
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
    }

}
