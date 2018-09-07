package main;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
            String output;

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            con.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
