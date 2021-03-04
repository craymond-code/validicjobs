package com.craymond.validic.workflow;

import com.craymond.validic.model.Constants;
import com.craymond.validic.model.CoolCity;
import com.craymond.validic.services.GithubService;
import com.craymond.validic.services.PositionModel;
import org.apache.http.HttpException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AggregateData {

    private GithubService service = new GithubService();

    /**
     *
     * Very complicated data science algorithm doing percentages
     *
     */
    private double getCityLanguagePercent(String city, String language) {

        double results = 0;

        // Find total job results for this city
        List<PositionModel> cityResults = null;
        try {

            cityResults = service.getAllPositionsInCity(city);
            // Find specific job results for this city
            List<PositionModel> cityLanguageResults = service.getPositionsByCityAndLanguage(language, city);

            float totalPositions = cityResults.size();
            float languagePositions = cityLanguageResults.size();

            if(totalPositions != 0) {
                results = (languagePositions / totalPositions) * 100;
            }

        } catch (HttpException e) {
            e.printStackTrace();
        }

        return results;

    }

    /**
     * Print a simple summary of one city
     * @param city
     */
    public void printCitySummary(String city){

        System.out.println("\n");
        System.out.println(city + ":");

        for(String language : Constants.LANGUAGES) {
            double percent = getCityLanguagePercent(city, language);
            System.out.println("   - "+ language + ":" + percent + "%");
        }

    }

    /**
     * Find out the "cool factor" (% of kotlin jobs) in a given city
     * @param city
     * @return
     */
    private int cityCoolFactor(String city) {
        System.out.println("Fetching cool factor for " + city + "city...");
        return (int) getCityLanguagePercent(city, "Kotlin");
    }

    public boolean findCoolerCity(String city1, String city2) {

        double percent1 = getCityLanguagePercent(city1, "Kotlin");
        double percent2 = getCityLanguagePercent(city2, "Kotlin");

        if(percent1 > percent2) {
            return true;
        }

        return false;

    }

    /**
     *
     * Print which cities are coolest in order
     *
     */
    public void sortCities() {

        List<CoolCity> coolfactors = new ArrayList<>();
        for(String city : Constants.CITIES.subList(0,10)) {
            coolfactors.add(new CoolCity(city, cityCoolFactor(city)));
        }

        coolfactors.sort(Comparator.comparingInt(a -> a.cool));
        Collections.reverse(coolfactors);

        System.out.println("\n");
        System.out.println("Coolest cities in order: ");
        for(CoolCity cityPair : coolfactors) {
            System.out.println("  - " + cityPair.city + ": "
                    + cityPair.cool.toString()
                    + "% jobs are Kotlin");
        }

    }



}
