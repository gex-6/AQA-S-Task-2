public class CurrencyConverter {

    private final double commission;
    private final double exchangeRate;
    private double amountUsdToHrn;

    public CurrencyConverter(double commision, double exchangeRate, double amountUsdToHrn) {
        this.commission = commision;
        this.exchangeRate = exchangeRate;

        if (amountUsdToHrn > 0 && amountUsdToHrn < 1000000)
            this.amountUsdToHrn = amountUsdToHrn;
        else
            System.out.println("The amount of currency is invalid");
    }

    private double convertUsdToUah() {
        return amountUsdToHrn * exchangeRate;
    }

    private double calculateCommission(double sumBeforeCommision) {
        return sumBeforeCommision - sumBeforeCommision * commission;
    }

    public double calculateTotalAmount() {
        return calculateCommission(convertUsdToUah());
    }
}
