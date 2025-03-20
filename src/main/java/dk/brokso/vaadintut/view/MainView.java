package dk.brokso.vaadintut.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import dk.brokso.vaadintut.data.*;

import java.util.ArrayList;
import java.util.List;


@Route("")
public class MainView extends VerticalLayout {

    private final ComboBox<FoodItem> foodChoiceComboBox;
    private final List<FoodItem> foodList;
    private final List<Food> chosenfoodList = new ArrayList<>();
    ;
    private final Opskrift opskrift = new Opskrift(chosenfoodList);
    private final Grid<Food> chosenFoodGrid;
    private Food totals = new FoodItem();
    private final HorizontalLayout bagdes;
    private Span proteinBadge;
    private Span kulhydratBadge;
    private Span fedtBadge;


    MainView(Dataloader dataloader) {

        this.foodList = dataloader.getFood();

//      Food choice
        VerticalLayout topOfPage = new VerticalLayout();
        topOfPage.setWidthFull();
        foodChoiceComboBox = new ComboBox<>("Mad");
        foodChoiceComboBox.setItems(foodList);
        foodChoiceComboBox.setItemLabelGenerator(FoodItem::getName);
        foodChoiceComboBox.setWidthFull();
        foodChoiceComboBox.addValueChangeListener(changeInValue -> receiveChosenValue(changeInValue.getValue()));
        topOfPage.add(foodChoiceComboBox);
        add(topOfPage);

//      Chosen foods
        this.chosenFoodGrid = new Grid<>(Food.class, false);
        Grid.Column<Food> navnKolonne = chosenFoodGrid.addColumn(Food::getName).setHeader("Navn").setAutoWidth(true);
        Grid.Column<Food> gramKolonne = chosenFoodGrid.addColumn(Food::getGram).setHeader("Gram").setAutoWidth(true);
        Grid.Column<Food> kalorieKolonne = chosenFoodGrid.addColumn(Food::getTotalCalories).setHeader("Kalorier").setAutoWidth(true);
        Grid.Column<Food> protein = chosenFoodGrid.addColumn(Food::getGramProtein).setHeader("Protein").setAutoWidth(true);
        Grid.Column<Food> kulhydrat = chosenFoodGrid.addColumn(Food::getGramCarbonhydrates).setHeader("Kulhydrat").setAutoWidth(true);
        Grid.Column<Food> fedt = chosenFoodGrid.addColumn(Food::getGramFat).setHeader("Fedt").setAutoWidth(true);
        Grid.Column<Food> fibre = chosenFoodGrid.addColumn(Food::getGramDietaryfibre).setHeader("Fibre").setAutoWidth(true);
        gramKolonne.setFooter("hej med dig");
//        Grid.Column<Food> maethed = chosenFoodGrid.addColumn(0.0).setHeader("Mæthedsindex").setAutoWidth(true);
        chosenFoodGrid.addComponentColumn(food -> {
            Button deleteButton = new Button("X");
            deleteButton.addClickListener(event -> deleteRow(food));
            return deleteButton;
        }).setHeader("Actions");

        chosenFoodGrid.setItems(chosenfoodList);


        Binder<Food> binder = new Binder<>(Food.class);
        Editor<Food> editor = chosenFoodGrid.getEditor();
        editor.setBinder(binder);

        NumberField gramField = new NumberField();
        gramField.addBlurListener(blur -> refresh());
        gramField.setWidthFull();
//        addCloseHandler(firstNameField, editor);
        binder.forField(gramField)
                .asRequired("Gram name must not be empty or 0")
                .bind(Food::getGram, Food::setGram);
        gramKolonne.setEditorComponent(gramField);

        chosenFoodGrid.addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable<?>) {
                ((Focusable) editorComponent).focus();
            }
        });


        VerticalLayout bottomOfPage = new VerticalLayout();
        bottomOfPage.add(chosenFoodGrid);
        proteinBadge = initializeBadge();
        kulhydratBadge = initializeBadge();
        fedtBadge = initializeBadge();


        bagdes = new HorizontalLayout(proteinBadge, kulhydratBadge, fedtBadge);
        bottomOfPage.add(bagdes);
        add(bottomOfPage);


    }

    private Span initializeBadge() {
        Span badge = new Span("");
        badge.setVisible(false);
        return badge;
    }

    private void updateBadge(Span badge, String text, String theme) {
        badge.setText(text);
        badge.getElement().getThemeList().add(theme);
        badge.setVisible(true);
    }


    private void deleteRow(Food chosenFoodItem) {
        if (chosenFoodItem == null) {
            return;
        }
        chosenfoodList.remove(chosenFoodItem);
        refresh();

    }

    private void receiveChosenValue(FoodItem chosenFoodItem) {
        if (chosenFoodItem == null) {
            return;
        }
        chosenfoodList.add(chosenFoodItem);
        refresh();

    }

    private void refresh() {

        this.chosenfoodList.remove(totals);
        foodChoiceComboBox.setClearButtonVisible(true);
        MealTotals mealTotals = opskrift.calculateTotals();
        this.totals = mealTotals;
        this.chosenfoodList.add(totals);
        chosenFoodGrid.getDataProvider().refreshAll();
        foodChoiceComboBox.clear();

        updateBadge(proteinBadge, String.format("Protein %.2f procent", mealTotals.getOpskriftPercentageProtein()), "badge" );
        updateBadge(kulhydratBadge, String.format("Kulhydrat %.2f procent", mealTotals.getOpskriftPercentageCarbonhydrates()), "badge" );
        updateBadge(fedtBadge, String.format("Fedt %.2f procent", mealTotals.getOpskriftPercentageFat()), "badge" );

    }

}
