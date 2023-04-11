package hw56;

public class CreditCalculator {

  public static void main(String[] args) {
    double annualPercent = 8.9; // ставка по кредиту в % годовых
    double totalCreditTime = 60; // срок кредитования в месяцах
    double creditSum = 57000; // сумма кредита в евро

    double monthlyPercentRate = monthlyPercentRateCalc(annualPercent);
    double annuityCoefficient = annuityCoefficientCalc(monthlyPercentRate, totalCreditTime);
    double monthlyPayment = monthlyPaymentCalc(creditSum, monthlyPercentRate, totalCreditTime);
    double monthlyAnnuityPayment = annuityPaymentCalc(monthlyPercentRate, annuityCoefficient,
        creditSum);

    System.out.println("Месячная процентная ставка: " + monthlyPercentRate);
    System.out.println("Аннуитетный коэффициент: " + annuityCoefficient);
    System.out.println("Аннуитетный платёж: " + monthlyAnnuityPayment);
    System.out.println("Ежемесячный платёж: " + monthlyPayment);
  }

  public static double monthlyPercentRateCalc(double annualPercent) {
    double monthlyPercentRate = Math.pow(1 + annualPercent / 100, 1.0 / 12) - 1;
    System.out.println("Месячная процентная ставка: " + monthlyPercentRate);
    return monthlyPercentRate;
  }

  public static double annuityCoefficientCalc(double monthlyPercentRate, double totalCreditTime) {
    double decimalPercent = monthlyPercentRate;
    double annuityCoefficient = (decimalPercent * Math.pow(1 + decimalPercent, totalCreditTime))
        / (Math.pow(1 + decimalPercent, totalCreditTime) - 1);
    System.out.println("Аннуитетный коэффициент: " + annuityCoefficient);
    return annuityCoefficient;
  }

  public static double annuityPaymentCalc(double monthlyPercentRate, double annuityCoefficient,
      double creditSum) {
    double monthlyAnnuityPayment = creditSum * monthlyPercentRate * annuityCoefficient;
    System.out.println("Аннуитетный платёж: " + monthlyAnnuityPayment);
    return monthlyAnnuityPayment;
  }

  public static double monthlyPaymentCalc(double creditSum, double monthlyPercentRate,
      double totalCreditTime) {
    double left = 0.0;
    double right = 100.0;
    double middle = (left + right) / 2;
    double monthlyPayment = annuityPaymentCalc(monthlyPercentRate,
        annuityCoefficientCalc(middle, totalCreditTime), creditSum);

    while (left <= right) {
      if (Math.abs(monthlyPayment * totalCreditTime - creditSum) < 0.01) {
        return monthlyPayment;
      } else if (monthlyPayment * totalCreditTime > creditSum) {
        right = middle;
      } else {
        left = middle;
      }
      middle = (left + right) / 2;
      monthlyPayment = annuityPaymentCalc(monthlyPercentRate,
          annuityCoefficientCalc(middle, totalCreditTime), creditSum);
      System.out.println("Monthly payment: " + monthlyPayment);
    }
    return monthlyPayment;
  }
}