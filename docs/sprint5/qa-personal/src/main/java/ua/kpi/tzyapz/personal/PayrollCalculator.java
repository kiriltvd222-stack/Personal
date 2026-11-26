package ua.kpi.tzyapz.personal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PayrollCalculator {

    public static final BigDecimal PIT_RATE = new BigDecimal("0.18");
    public static final BigDecimal MILITARY_RATE = new BigDecimal("0.05");
    public static final BigDecimal HOURS_PER_MONTH = new BigDecimal("168");
    public static final BigDecimal OVERTIME_COEF = new BigDecimal("1.5");

    private static final int SCALE = 2;

    public BigDecimal net(BigDecimal gross) {
        requireNonNegative(gross, "gross");
        BigDecimal pit = gross.multiply(PIT_RATE);
        BigDecimal military = gross.multiply(MILITARY_RATE);
        return gross.subtract(pit).subtract(military).setScale(SCALE, RoundingMode.HALF_UP);
    }

    public BigDecimal hourlyRate(BigDecimal monthly) {
        requireNonNegative(monthly, "monthly");
        return monthly.divide(HOURS_PER_MONTH, SCALE, RoundingMode.DOWN);
    }

    public BigDecimal overtimePay(BigDecimal monthly, BigDecimal hours) {
        requireNonNegative(hours, "hours");
        return hourlyRate(monthly).multiply(OVERTIME_COEF).multiply(hours)
                .setScale(SCALE, RoundingMode.HALF_UP);
    }

    private static void requireNonNegative(BigDecimal value, String name) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(name + " має бути >= 0");
        }
    }
}