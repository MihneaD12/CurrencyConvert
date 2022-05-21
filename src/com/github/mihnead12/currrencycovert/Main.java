package com.github.mihnead12.currrencycovert;

public class Main {
    public static boolean isDouble(String val) {
        try {
            Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("No api key");
            System.exit(-1);
        }

        String curr1, curr2;
        double amount;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the currency you want to convert from: ");
        curr1 = reader.readLine();

        System.out.println("Enter the currency you want to convert to: ");
        curr2 = reader.readLine();

        System.out.println("Enter the amount: ");
        String query = reader.readLine();
        amount = (isDouble(query) ? Double.parseDouble(query) : 1);
        System.out.println(CurrencyConverter.getInstance(args[0]).convert(amount, curr1, curr2));
    }
}
