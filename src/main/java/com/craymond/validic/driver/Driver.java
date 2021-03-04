package com.craymond.validic.driver;

import com.craymond.validic.model.Constants;
import com.craymond.validic.workflow.AggregateData;

import java.util.Scanner;

/**
 *
 * Classic driver class to dump some stuff to stdout
 *
 */
public class Driver {

    public static void main(String[] args) {

        // So we can pause for input
        Scanner scan = new Scanner(System.in);

        // Get our helpful data aggregator
        AggregateData dataScience = new AggregateData();

        // First prompt
        System.out.println("Let's see which US cities have the most jobs with which language.");
        System.out.println("Press enter to continue...");
        scan.nextLine();

        // Walk through the largest 5 US cities
        for(String city : Constants.CITIES.subList(0,4)){
            dataScience.printCitySummary(city);
        }

        // Display header and prompt the user
        System.out.println("\n");
        System.out.println("Everybody knows Kotlin is the coolest language...but which city is coolest?");
        System.out.println("Press enter to continue... ");
        scan.nextLine();

        dataScience.sortCities();

    }

}
