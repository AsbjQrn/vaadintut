package dk.brokso.vaadintut.data;


import dk.brokso.vaadintut.utils.Calculator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;


@Data
@ToString
@EqualsAndHashCode
public class MealTotals implements Food {

    private static int foodcounter;
    private int id;
    private String name = "Total";
    private double gramIalt;
    private double kalorierIalt;
    private double proteinIalt;
    private double fedtIalt;
    private double kulhydratIalt;
    private double fibreIalt;
    private double fullnessFactor;
    private double opskriftPercentageCarbonhydrates;
    private double opskriftPercentageProtein;
    private double opskriftPercentageFat;
    private double maethed;

    public MealTotals() {
    }

    public MealTotals(double gramIalt, double kalorierIalt, double proteinIalt, double fedtIalt, double kulhydratIalt, double fibreIalt) {
        this.gramIalt = gramIalt;
        this.kalorierIalt = kalorierIalt;
        this.proteinIalt = proteinIalt;
        this.fedtIalt = fedtIalt;
        this.kulhydratIalt = kulhydratIalt;
        this.fibreIalt = fibreIalt;
        this.fullnessFactor = fullnessFactor;


        fullnessFactor = 0.1d;


//        foodItem.setFullnessFactor(Calculator.calculateFullnessFactor(foodItem.getKcalIn100Gram(),  foodItem.getProteinIn100Gram(), foodItem.getDietaryfibreIn100gram(), foodItem.getFatIn100Gram()));

    }

    private static double localParsedouble(List<String> foodProps, int position) {
        String foodprop = foodProps.get(position);

        if ("".equals(foodprop.trim())) {
            foodprop = "0";
        }

        double f = 0f;

        try {
            f = Double.parseDouble(foodProps.get(position));
        } catch (Exception e) {
            System.out.println("");
        }

        return f;

    }

    public double getGram() {
        return gramIalt;
    }

    @Override
    public void setGram(double gram) {
        this.gramIalt = gram;
    }

    @Override
    public double getMaethed() {
        return this.maethed;
    }

    public void setMaethed(double opskriftKiloKalorierpr100Gr, double opskriftProteinPr100Gr, double opskriftDietaryfibrePr100Gr, double opskriftFatPr100Gr) {
        this.maethed = this.calculateFullnessFactor(opskriftKiloKalorierpr100Gr, opskriftProteinPr100Gr, opskriftDietaryfibrePr100Gr, opskriftFatPr100Gr);
    }

    public double getTotalCalories() {
        return kalorierIalt;
    }

    public double getGramProtein() {
        return proteinIalt;
    }

    public double getGramCarbonhydrates() {
        return kulhydratIalt;

    }

    public double getGramFat() {
        return fedtIalt;
    }

    public double getGramDietaryfibre() {
        return fibreIalt;
    }

}
