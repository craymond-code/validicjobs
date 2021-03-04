package com.craymond.validic.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GithubService {

    // Service level configuration
    private final String HOST_URL = "jobs.github.com";
    private final String POSITIONS_RESOURCE = "positions.json";
    private final int timeout = 10000;

    /**
     * Build a re-usable HttpClient with config specific to this service
     *
     * @return
     */
    private HttpClient getGithubClient() {

        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder.setConnectTimeout(timeout);
        requestBuilder.setConnectionRequestTimeout(timeout);

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());

        return builder.build();

    }

    /**
     *
     * @param description - Search term for positions
     * @return - list of found positions
     */
    public List<PositionModel> searchPositions(String description) throws HttpException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("description", description));

        return getPositions(params);
    }

    /**
     *
     * @param city - City to find all positions for
     * @return
     */
    public List<PositionModel> getAllPositionsInCity(String city) throws HttpException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("location", city));

        return getPositions(params);
    }

    /**
     *
     * @param description - Search term to look for
     * @param city - City to filter by
     * @return
     */
    public List<PositionModel> getPositionsByCityAndLanguage(String description, String city) throws HttpException {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("location", city));

        return getPositions(params);
    }


    /**
     *
     * Execute a search request to the github API with the given
     * search parameters
     *
     * @param params
     * @return
     */
    private List<PositionModel> getPositions(List<NameValuePair> params) throws HttpException {

        // Grab the pre-configured http client
        HttpClient ghClient = getGithubClient();
        HttpResponse response;
        List<PositionModel> responseModel = null;

        // Execute the request
        try {

            // Build the information for this request
            URI jobsRequest = new URIBuilder()
                    .setScheme("https")
                    .setHost(HOST_URL)
                    .setPath(POSITIONS_RESOURCE)
                    .setParameters(params)
                    .build();
            HttpGet fetchPositionResults = new HttpGet(jobsRequest);

            // Execute the request
            response = ghClient.execute(fetchPositionResults);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new HttpException();
            }

            // Map the response ensuring githubs preferred date format can be parsed
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            objectMapper.setDateFormat(df);

            // Return the results
            PositionModel[] rawList = objectMapper.readValue(response.getEntity().getContent(), PositionModel[].class);
            responseModel = new ArrayList<>(Arrays.asList(rawList));

        } catch (IOException | URISyntaxException e) {

            System.out.println("Request failed in GithubService with params");
            e.printStackTrace();
        }


        return responseModel;
    }

}
