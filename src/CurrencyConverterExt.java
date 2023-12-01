import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * The idea of extension is to get the exchange rates dynamically.
 * Class provides the possibility to convert EUR to any other currency.
 * The latest currencies exchange rates are taken from fixer.io
 * Read more about it: <a href="https://fixer.io/documentation">documentation</a>
 * <br>
 * <br>
 * P.S.: parsing the JSON with reg-ex was done with help of Chat GPT :)
 */
public class CurrencyConverterExt {

    private static final String API_URL = "http://data.fixer.io/api/latest";
    private static final String API_KEY = "?access_key=1b96addb2d3108603bd89ca0a9290193";
    private final double commission;
    private final double exchangeRate;
    private double amountToConvert;

    /**
     * @param commission taken by the bank
     * @param currency provide the currency as a string in ISO 4217 standard (i.e. "UAH", "USD")
     * @param amountToConvert the sum to be converted
     */
    public CurrencyConverterExt(double commission, String currency, double amountToConvert) {
        this.commission = commission;
        HashMap<String, Double> latestExchangeRates = getTheLatestExchangeRates();
        this.exchangeRate = latestExchangeRates.get(currency.toUpperCase());

        if (amountToConvert > 0 && amountToConvert <= 1000000)
            this.amountToConvert = amountToConvert;
        else
            System.out.println("The amount is invalid");
    }

    private double convertEurToCurrency() {
        return amountToConvert * exchangeRate;
    }

    private double calculateCommission(double sumBeforeCommission) {
        return sumBeforeCommission - sumBeforeCommission * commission;
    }

    public double calculateTotalAmount() {
        return calculateCommission(convertEurToCurrency());
    }

    public HashMap<String, Double> getTheLatestExchangeRates() {

        HashMap<String, Double> exchangeRatesMap = null;

        try {
            // Make the API request
            URL url = new URL(API_URL + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse the JSON response manually
            Pattern pattern = Pattern.compile("\"([A-Z]{3})\":(\\d+\\.\\d+)");
            Scanner scanner = new Scanner(response.toString());

            // Extract rates using regular expressions
            exchangeRatesMap = new HashMap<>();
            while (scanner.findWithinHorizon(pattern, 0) != null) {
                MatchResult matchResult = scanner.match();
                String currency = matchResult.group(1);
                double rate = Double.parseDouble(matchResult.group(2));
                exchangeRatesMap.put(currency, rate);
            }

            // Close the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exchangeRatesMap;
    }
}
