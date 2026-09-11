package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public class SmartWatch implements Item {
    private static final double AVERAGE_STEP_LENGTH_IN_METERS = 0.75;

    private String brand;
    private int batteryPercentage;
    private int steps;
    private int dailyStepGoal;
    private double weightInKg;
    private double priceInEur;

    public SmartWatch() {
    }

    public SmartWatch(
            String brand,
            int batteryPercentage,
            int steps,
            int dailyStepGoal,
            double weightInKg,
            double priceInEur) {
        setBrand(brand);
        setBatteryPercentage(batteryPercentage);
        setSteps(steps);
        setDailyStepGoal(dailyStepGoal);
        setWeightInKg(weightInKg);
        setPriceInEur(priceInEur);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Die Marke darf nicht leer sein.");
        }
        this.brand = brand;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        if (batteryPercentage < 0 || batteryPercentage > 100) {
            throw new IllegalArgumentException(
                    "Der Akkustand muss zwischen 0 und 100 liegen.");
        }
        this.batteryPercentage = batteryPercentage;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        if (steps < 0) {
            throw new IllegalArgumentException(
                    "Die Schrittzahl darf nicht negativ sein.");
        }
        this.steps = steps;
    }

    public int getDailyStepGoal() {
        return dailyStepGoal;
    }

    public void setDailyStepGoal(int dailyStepGoal) {
        if (dailyStepGoal <= 0) {
            throw new IllegalArgumentException(
                    "Das Schrittziel muss positiv sein.");
        }
        this.dailyStepGoal = dailyStepGoal;
    }

    public double getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(double weightInKg) {
        if (!Double.isFinite(weightInKg) || weightInKg <= 0) {
            throw new IllegalArgumentException("Das Gewicht muss positiv sein.");
        }
        this.weightInKg = weightInKg;
    }

    public double getPriceInEur() {
        return priceInEur;
    }

    public void setPriceInEur(double priceInEur) {
        if (!Double.isFinite(priceInEur) || priceInEur < 0) {
            throw new IllegalArgumentException(
                    "Der Preis darf nicht negativ sein.");
        }
        this.priceInEur = priceInEur;
    }

    @Override
    public double weightInKg() {
        return weightInKg;
    }

    @Override
    public double priceInEur() {
        return priceInEur;
    }

    public void recordSteps(int additionalSteps) {
        if (additionalSteps < 0) {
            throw new IllegalArgumentException(
                    "Zusaetzliche Schritte duerfen nicht negativ sein.");
        }
        steps += additionalSteps;
    }

    public void charge() {
        batteryPercentage = 100;
    }

    public boolean dailyGoalReached() {
        return steps >= dailyStepGoal;
    }

    public int remainingSteps() {
        return Math.max(0, dailyStepGoal - steps);
    }

    public double progressPercentage() {
        return Math.min(100.0, steps * 100.0 / dailyStepGoal);
    }

    public double distanceInKilometers() {
        return steps * AVERAGE_STEP_LENGTH_IN_METERS / 1_000.0;
    }

    public boolean batteryLow() {
        return batteryPercentage < 20;
    }

    @Override
    public String getDescription() {
        return ("Smartwatch von %s: %d Schritte, Tagesziel zu %.1f %% "
                + "erreicht, Akku bei %d %%").formatted(
                brand, steps, progressPercentage(), batteryPercentage);
    }
}
