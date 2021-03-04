import com.craymond.validic.services.GithubService;
import com.craymond.validic.services.PositionModel;
import org.apache.http.HttpException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SimpleTestSuite {

    /**
     *
     * Assert that we can get a 200 response from a search
     * for any results from a large city. Confirms we have
     * basic connectivity and the API is live.
     *
     */
    @Test
    public void AssertStatus200() {

        GithubService service = new GithubService();
        List<PositionModel> results =  null;

        try {

            results = service.getAllPositionsInCity("New Yok");

        } catch (HttpException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(results);
    }

    /**
     *
     * Kotlin should always be found somewhere...right?
     *
     */
    @Test
    public void AssertNonZeroKotlin(){

        GithubService service = new GithubService();
        List<PositionModel> results =  null;

        try {

            results = service.searchPositions("Kotlin");

        } catch (HttpException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(results);
        Assert.assertTrue(results.size() > 0);

    }


    /**
     *
     * Check for a fake language and make sure we get
     * no results back, and no divide by 0 exception
     * throwing an error
     *
     */
    @Test
    public void AssertFakeLanguageZero(){

        // It's a free-text term search, but this would be...unlikely.
        String fakeLanguage = "JabbaTheHuttScript";

        GithubService service = new GithubService();
        List<PositionModel> results =  null;

        try {

            results = service.getPositionsByCityAndLanguage("New Yok", fakeLanguage);

        } catch (HttpException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(results);
        Assert.assertEquals(0,results.size());

    }




}
