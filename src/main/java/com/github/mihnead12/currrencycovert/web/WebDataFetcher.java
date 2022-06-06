package com.github.mihnead12.currrencycovert.web;

import com.github.mihnead12.currrencycovert.web.exceptions.FetchError;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class WebDataFetcher {
    public static JSONObject fetchJSONObject(String url) throws FetchError {
        try {
            return new JSONObject(Objects.requireNonNull(fetchData(url)));
        } catch (Exception ex) {
            throw new FetchError(url);
        }
    }

    public static String fetchData(String url) {
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder response = new StringBuilder();

            for (String inputLine = in.readLine(); inputLine != null; inputLine = in.readLine()) {
                response.append(inputLine);
            }

            in.close();

            return response.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}