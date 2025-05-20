package dk.brokso.vaadintut.data;



import java.io.Serializable;
import java.util.List;

import dk.brokso.vaadintut.utils.Calculator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString
@EqualsAndHashCode
public class FoodItem implements Food, Serializable {

    private static int foodcounter;
    private int id;
    private String name;
    private double kcalIn100Gram;
    private double proteinIn100Gram;
    private double fatIn100Gram;
    private double carbonhydratesIn100Gram;
    private double dietaryfibreIn100gram;
    private double fullnessFactor;
    private double gram;
    private double makronaeringVaegt;

    public FoodItem(){}

    public FoodItem(String name, double kcalIn100Gram, double proteinIn100Gram, double fatIn100Gram, double carbonhydratesIn100Gram, double dietaryfibreIn100gram) {
        this.name = name;
        this.kcalIn100Gram = kcalIn100Gram;
        this.proteinIn100Gram = proteinIn100Gram;
        this.fatIn100Gram = fatIn100Gram;
        this.carbonhydratesIn100Gram = carbonhydratesIn100Gram;
        this.dietaryfibreIn100gram = dietaryfibreIn100gram;
    }


    public static FoodItem of(List<String> foodProps) {

        foodcounter++;

        FoodItem foodItem = new FoodItem();

        foodItem.setId(foodcounter);
        foodItem.setName(foodProps.get(0));
        foodItem.setKcalIn100Gram(localParsedouble(foodProps, 5));
        foodItem.setProteinIn100Gram(localParsedouble(foodProps, 7));
        foodItem.setCarbonhydratesIn100Gram(localParsedouble(foodProps, 10));
        foodItem.setDietaryfibreIn100gram(localParsedouble(foodProps, 13));
        foodItem.setFatIn100Gram(localParsedouble(foodProps, 14));

        foodItem.setFullnessFactor(Calculator.calculateFullnessFactor(foodItem.getKcalIn100Gram(),  foodItem.getProteinIn100Gram(), foodItem.getDietaryfibreIn100gram(), foodItem.getFatIn100Gram()));

        return foodItem;

    }

    public static FoodItem ofAdditional(List<String> foodProps) {

        foodcounter++;

        FoodItem foodItem = new FoodItem();

        foodItem.setId(foodcounter);
        foodItem.setName(foodProps.get(0));
        foodItem.setKcalIn100Gram(localParsedouble(foodProps, 1));
        foodItem.setProteinIn100Gram(localParsedouble(foodProps, 2));
        foodItem.setCarbonhydratesIn100Gram(localParsedouble(foodProps, 3));
        foodItem.setDietaryfibreIn100gram(localParsedouble(foodProps, 4));
        foodItem.setFatIn100Gram(localParsedouble(foodProps, 5));
        foodItem.setFullnessFactor(Calculator.calculateFullnessFactor(foodItem.getKcalIn100Gram(),  foodItem.getProteinIn100Gram(), foodItem.getDietaryfibreIn100gram(), foodItem.getFatIn100Gram()));

        return foodItem;

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

    public double getTotalCalories(){
        return (kcalIn100Gram * gram)/100 ;
    }

    public double getGramProtein(){
        return (proteinIn100Gram * gram)/100;
    }

    public double getGramCarbonhydrates(){
        return carbonhydratesIn100Gram * gram  * 1/100;
    }

    public double getGramFat(){
        return fatIn100Gram * gram  * 1/100;
    }

    public double getGramDietaryfibre(){
        return dietaryfibreIn100gram * gram  * 1/100;
    }

    public double getMaethed(){
        return this.calculateFullnessFactor(kcalIn100Gram, proteinIn100Gram, dietaryfibreIn100gram, fatIn100Gram);
    }

    @Override
    public void setGram(double gram) {
        this.gram = gram;
    }

}
