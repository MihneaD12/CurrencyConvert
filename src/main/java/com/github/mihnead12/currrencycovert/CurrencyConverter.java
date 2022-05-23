package com.github.mihnead12.currrencycovert;

import com.github.mihnead12.currrencycovert.web.WebDataFetcher;
import com.github.mihnead12.currrencycovert.exceptions.CurrencyNotValid;
import com.github.mihnead12.currrencycovert.web.exceptions.FetchError;
import org.json.JSONObject;

import java.util.HashMap;

public class CurrencyConverter {
    private static CurrencyConverter instance;
    private static final String api = "https://free.currconv.com/api/v7/convert?q=";
    private final String apiKey;
    private final HashMap<String, Long> rateLimit;
    private final HashMap<String, Double> cacheCurrency;

    private CurrencyConverter(String apiKey) {
        this.apiKey = "&apiKey=" + apiKey;
        this.rateLimit = new HashMap<>();
        this.cacheCurrency = new HashMap<>();
    }

    public static CurrencyConverter getInstance(String apiKey) {
        if (instance == null) instance = new CurrencyConverter(apiKey);
        return instance;
    }

    public double convert(String currency1, String currency2) {
        String id = currency1.toUpperCase() + "_" + currency2.toUpperCase();
        JSONObject response;
        try {
            response = WebDataFetcher.fetchJSONObject(api + id + apiKey);
        } catch (FetchError e) {
            return -1;
        }

        if (response.getJSONObject("query").getInt("count") == 0) throw new CurrencyNotValid();

        // To respect 100 request / hour rate limit, limit the api access of each currency conversion to 1 per hour
        if (!rateLimit.containsKey(id) || rateLimit.get(id) + 3600000L <= System.currentTimeMillis()) {
            rateLimit.put(id, System.currentTimeMillis());
            cacheCurrency.put(id, response.getJSONObject("results").getJSONObject(id).getDouble("val"));
        }
        return cacheCurrency.get(id);
    }

    public double convert(double amount, String currency1, String currency2) {
        return amount * convert(currency1, currency2);
    }
}
