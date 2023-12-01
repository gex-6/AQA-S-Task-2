import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /* CurrencyConverter */
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nHow many USD should be exchanged to UAH? ");
        double amountUsdToUah = Double.parseDouble(scanner.nextLine());
        CurrencyConverter convert = new CurrencyConverter(0.01, 36.55, amountUsdToUah);
        System.out.println(amountUsdToUah + " USD will be " + convert.calculateTotalAmount() + " UAH with 1% commission");

        /* Extended CurrencyConverter */
        System.out.print("\n*** extended converter is launched ***\nSelect currency in ISO to convert from EUR:  ");
        String currency = scanner.nextLine();
        System.out.println("Type in the amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        CurrencyConverterExt converterExt = new CurrencyConverterExt(0.01, currency, amount);
        System.out.println(amount + " EUR will be " + converterExt.calculateTotalAmount() + " " + currency + " with 1% commission");
    }
}