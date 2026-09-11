package at.bbrz.oop.uebung09_polymorphie_ohne_switch;

public record SmartWatch(
        String brand,
        int batteryPercentage,
        int steps,
        int dailyStepGoal) implements Item {

    private static final double AVERAGE_STEP_LENGTH_IN_METERS = 0.75;

    public SmartWatch {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Die Marke darf nicht leer sein.");
        }
        if (batteryPercentage < 0 || batteryPercentage > 100) {
            throw new IllegalArgumentException(
                    "Der Akkustand muss zwischen 0 und 100 liegen.");
        }
        if (steps < 0) {
            throw new IllegalArgumentException(
                    "Die Schrittzahl darf nicht negativ sein.");
        }
        if (dailyStepGoal <= 0) {
            throw new IllegalArgumentException(
                    "Das Schrittziel muss positiv sein.");
        }
    }

    public SmartWatch recordSteps(int additionalSteps) {
        if (additionalSteps < 0) {
            throw new IllegalArgumentException(
                    "Zusaetzliche Schritte duerfen nicht negativ sein.");
        }
        return new SmartWatch(
                brand, batteryPercentage, steps + additionalSteps, dailyStepGoal);
    }

    public SmartWatch charge() {
        return new SmartWatch(brand, 100, steps, dailyStepGoal);
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
