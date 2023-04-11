package hw56;

public class AnnuityPayment {
  /*
  Задана процентная ставка по кредиту (percent % годовых),
  срок кредитования (months месяцев) и сумма кредита (total евро).
  Необходимо при помощи бинарного поиска рассчитать размер аннуитетного платежа:
  платежи каждый месяц должны быть равными, как при обычном кредите или ипотеке.
  Каждый платёж полностью выплачивает все накопленные проценты,
  оставшаяся сумма идёт на погашение тела кредита.
  Это означает, что в первом месяце ваш платёж состоит
  почти только из начисленных процентов, а в последнем - почти только из тела кредита.
  Подсказка 1
  Воспользуйтесь бинарным поиском для размера начисляемых ежемесячно процентов
  (с точностью до четырёх знаков после запятой) - это не percent / 12.
  Процент должен быть таким, чтобы за 12 месяцев на сумму
  (с учётом начисления процентов на проценты) общая сумма долга увеличилась на percent %.
  Подсказка 2
  Подберите размер ежемесячного платежа при помощи бинарного поиска: необходимо,
  чтобы при выбранном размере платежа и ежемесячных процентах из подсказки 1
  количество платежей для полного погашения долга (total евро)
  совпадало с заданным в условии задачи (months месяцев).

посчитать процент за месяц:
  X = процент за год
  X/100 = доля за год
  1+X/100 = годовой коэфф-т умножения вклада
  root12(1+X/100) = месячный коэфф-т умножения вклада, где root12 это корень 12-ой степени
  root12(1+X/100) - 1 = месячная доля
  (root12(1+X/100)-1)*100 = месячный процент

  (((1 + годовая ставка(x)/ 100)1/12 — 1) × 100 %) - месячный процент.
  (размер аннуитетного платежа) = искомое (если меньше ищем справа, больше - слева)

  (percent % годовых) = ставка по кредиту
  (months месяцев) = срок кредитования
  (total евро) = сумма кредита

  int mid = (first + last) >>> 1
  first + (last — first) / 2
  
  множество делится на p+1 частей
  K=lg(p)+1n Примерная высота ЯПФ

  70657 = общий; 13657 = процентная ставка; 57000 = сумма; 60 мес = время
   1011 = 1 мес;     254 = 1 мес;                757 = 1 мес;   1 мес;
   1180 = 2 мес;     417 = 2 мес;                763 = 2 мес;   2 мес;
   1180 = 60 мес;      8 = 60мес;               1172 = 60мес;  60 мес;

  - рассчитать ежемесячный платёж за кредит
  - рассчитать коэффициент аннуитета
  - рассчитать месячный аннуитет
  - рассчитать итоговый платёж
   */

  public static void main(String[] args) {
    double annualPercent = 8.9;//(percent % годовых) = ставка по кредиту
    double totalCreditTime = 60;//(months месяцев) = срок кредитования
    double creditSum = 57000;//(total евро) = сумма кредита

    double monthlyPercentRate = monthlyPercentRateCalc(annualPercent);
    double annuityCoefficient = annuityCoefficientCalc(monthlyPercentRate, totalCreditTime);
    double monthlyPayment = monthlyPaymentCalc(creditSum, monthlyPercentRate, totalCreditTime);
    double monthlyAnnuityPayment = annuityPaymentCalc(monthlyPercentRate, annuityCoefficient,
        creditSum);

    System.out.println("Месячная процентная ставка: " + monthlyPercentRate);
    System.out.println("Аннуитетный коэффициент: " + annuityCoefficient);
    System.out.println("Аннуитетный платёж: " + monthlyAnnuityPayment);
    System.out.print("Ежемесячный платёж: " + monthlyPayment);
  }

  public static double monthlyPercentRateCalc(double annualPercent) {
    double monthlyPercentRate = (Math.pow(1 + annualPercent / 100, 1.0 / 12) - 1) * 100;
    return monthlyPercentRate;
  }

  public static double annuityCoefficientCalc(double monthlyPercentRate, double totalCreditTime) {
    double decimalPercent = monthlyPercentRate /*/ 100.0*/;//!!!!!!!!!  / 100*
    double annuityCoefficient = (decimalPercent * Math.pow(1 + decimalPercent, totalCreditTime)) /
        (Math.pow(1 + decimalPercent, totalCreditTime) - 1);
    return annuityCoefficient;
  }

  public static double annuityPaymentCalc(double monthlyPercentRate, double annuityCoefficient,
      double creditSum) {
    double monthlyAnnuityPayment = creditSum * (monthlyPercentRate / 100) * annuityCoefficient;
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
      if (Math.abs(monthlyPayment * totalCreditTime - creditSum) < 0.0001) {
        return monthlyPayment;
      } else if (monthlyPayment * totalCreditTime > creditSum) {
        right = middle;
      } else {
        left = middle;
      }
      middle = (left + right) / 2;
      monthlyPayment = annuityPaymentCalc(monthlyPercentRate,
          annuityCoefficientCalc(middle, totalCreditTime), creditSum);
    }
    return monthlyPayment;
  }
}
