package dk.brokso.vaadintut.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Opskrift {


    private final List<Food> valgtmad;
    private String navn;
    private double opskriftTotalKcal = 0d;
    private double opskriftTotalProtein = 0d;
    private double opskriftTotalFat = 0d;
    private double opskriftTotalCarbonhydrates = 0d;
    private double opskriftTotalDietaryfibre = 0d;
    private double opskriftMakroNaeringlWeigt = 0d;
    private double opskriftTotalGramValgt = 0d;

    //  Disse procenter bliver regnet fra total mængde makronæringsstoffer og
    private double opskriftPercentageProtein = 0d;
    private double opskriftPercentageCarbonhydrates = 0d;
    private double opskriftPercentageFat = 0d;
    private double opskriftPercentageDietaryfibre = 0d;

    //  Bliver regnet fra opskriftens totalvægt
    private double opskriftKiloKalorierpr100Gr = 0d;
    private double opskriftProteinPr100Gr = 0d;
    private double opskriftCarbonhydratesPr100Gr = 0d;
    private double opskriftFatPr100Gr = 0d;
    private double opskriftDietaryfibrePr100Gr = 0d;

    public Opskrift(List<Food> valgtmad) {
        this.valgtmad = valgtmad;
    }


    public MealTotals calculateTotals() {

        opskriftTotalKcal = 0;
        opskriftTotalProtein = 0;
        opskriftTotalFat = 0;
        opskriftTotalCarbonhydrates = 0;
        opskriftTotalDietaryfibre = 0;
        opskriftTotalGramValgt = 0;

        for (Food foodItem : valgtmad) {
            opskriftTotalKcal = opskriftTotalKcal + foodItem.getTotalCalories();
            opskriftTotalProtein = opskriftTotalProtein + foodItem.getGramProtein();
            opskriftTotalFat = opskriftTotalFat + foodItem.getGramFat();
            opskriftTotalCarbonhydrates = opskriftTotalCarbonhydrates + foodItem.getGramCarbonhydrates();
            opskriftTotalDietaryfibre = opskriftTotalDietaryfibre + foodItem.getGramDietaryfibre();
            opskriftTotalGramValgt = opskriftTotalGramValgt + foodItem.getGram();
        }

//      kiloKalorier pr 100
        opskriftKiloKalorierpr100Gr = opskriftTotalKcal / opskriftTotalGramValgt * 100;

//      protein pr 100 gram
        opskriftProteinPr100Gr = opskriftTotalProtein / opskriftTotalGramValgt * 100;

//      kulhydrat 100 gram
        opskriftCarbonhydratesPr100Gr = opskriftTotalCarbonhydrates / opskriftTotalGramValgt * 100;

//      fedt 100 gram
        opskriftFatPr100Gr = opskriftTotalFat / opskriftTotalGramValgt * 100;

//      fibre 100 gram
        opskriftDietaryfibrePr100Gr = opskriftTotalDietaryfibre / opskriftTotalGramValgt * 100;


        opskriftMakroNaeringlWeigt = opskriftTotalProtein + opskriftTotalFat + opskriftTotalCarbonhydrates;


        opskriftPercentageCarbonhydrates = opskriftTotalCarbonhydrates / opskriftMakroNaeringlWeigt * 100;
        opskriftPercentageProtein = opskriftTotalProtein / opskriftMakroNaeringlWeigt * 100;
        opskriftPercentageFat = opskriftTotalFat / opskriftMakroNaeringlWeigt * 100;

        MealTotals mealTotals = new MealTotals(this.opskriftTotalGramValgt, this.opskriftTotalKcal, this.opskriftTotalProtein, this.opskriftTotalFat, this.opskriftTotalCarbonhydrates, this.opskriftTotalDietaryfibre);
        mealTotals.setOpskriftPercentageCarbonhydrates(opskriftPercentageCarbonhydrates);
        mealTotals.setOpskriftPercentageFat(opskriftPercentageFat);
        mealTotals.setOpskriftPercentageProtein(opskriftPercentageProtein);
        return mealTotals;

    }

    @Override
    public String toString() {
        return "Opskrift{" +
                "valgtmad=" + valgtmad +
                ", opskriftTotalKcal=" + opskriftTotalKcal +
                ", opskriftTotalProtein=" + opskriftTotalProtein +
                ", opskriftTotalFat=" + opskriftTotalFat +
                ", opskriftTotalCarbonhydrates=" + opskriftTotalCarbonhydrates +
                ", opskriftTotalDietaryfibre=" + opskriftTotalDietaryfibre +
                '}';
    }


}
