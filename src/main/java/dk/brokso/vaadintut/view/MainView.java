package dk.brokso.vaadintut.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import dk.brokso.vaadintut.data.*;

import java.util.ArrayList;
import java.util.List;



@Route("")
public class MainView extends VerticalLayout {

    private final ComboBox<FoodItem> foodChoiceComboBox;
    private final List<FoodItem> foodList;
    private final List<Food> chosenfoodList = new ArrayList<>();
    private final Opskrift opskrift = new Opskrift(chosenfoodList);
    private final Grid<Food> chosenFoodGrid;
    private final HorizontalLayout bagdes;
    private Span proteinBadge;
    private Span kulhydratBadge;
    private Span fedtBadge;

    private final static String KOLONNE_NAVN = "Navn";
    private final static String KOLONNE_GRAM = "Gram";
    private final static String KOLONNE_KALORIER = "Kalorier";
    private final static String KOLONNE_PROTEIN = "Protein";
    private final static String KOLONNE_KULHYDRAT = "Kulhydrat";
    private final static String KOLONNE_FEDT = "Fedt";
    private final static String KOLONNE_FIBRE = "Fibre";


    MainView(Dataloader dataloader) {



        UI.getCurrent().getElement().getThemeList().add(Lumo.DARK);

        this.foodList = dataloader.getFood();

//      Food choice
        VerticalLayout topOfPage = new VerticalLayout();
        topOfPage.setWidthFull();
        foodChoiceComboBox = new ComboBox<>("Vælg Mad");
        foodChoiceComboBox.setItems(foodList);
        foodChoiceComboBox.setItemLabelGenerator(FoodItem::getName);
        foodChoiceComboBox.setWidthFull();
        foodChoiceComboBox.addValueChangeListener(changeInValue -> receiveChosenValue(changeInValue.getValue()));
        topOfPage.add(foodChoiceComboBox);
        add(topOfPage);

//      Chosen foods
        this.chosenFoodGrid = new Grid<>(Food.class, false);
        Grid.Column<Food> navnKolonne = chosenFoodGrid.addColumn(Food::getName).setHeader(KOLONNE_NAVN).setAutoWidth(true);
        Grid.Column<Food> gramKolonne = chosenFoodGrid.addColumn(Food::getGram).setHeader(KOLONNE_GRAM).setAutoWidth(true);
        Grid.Column<Food> kalorieKolonne = chosenFoodGrid.addColumn(Food::getTotalCalories).setHeader(KOLONNE_KALORIER).setAutoWidth(true);
        Grid.Column<Food> protein = chosenFoodGrid.addColumn(Food::getGramProtein).setHeader(KOLONNE_PROTEIN).setAutoWidth(true);
        Grid.Column<Food> kulhydrat = chosenFoodGrid.addColumn(Food::getGramCarbonhydrates).setHeader(KOLONNE_KULHYDRAT).setAutoWidth(true);
        Grid.Column<Food> fedt = chosenFoodGrid.addColumn(Food::getGramFat).setHeader(KOLONNE_FEDT).setAutoWidth(true);
        Grid.Column<Food> fibre = chosenFoodGrid.addColumn(Food::getGramDietaryfibre).setHeader(KOLONNE_FIBRE).setAutoWidth(true);

//        Grid.Column<Food> maethed = chosenFoodGrid.addColumn(0.0).setHeader("Mæthedsindex").setAutoWidth(true);
        chosenFoodGrid.addComponentColumn(food -> {
            Button deleteButton = new Button(VaadinIcon.TRASH.create());
            deleteButton.getStyle()
                    .set("background-color", "transparent")
                    .set("border", "none")
                    .set("box-shadow", "none")
                    .set("color", "#ffbd66") // Optional: make the icon red
//                    .set("color", "#ff9e99") // Optional: make the icon red
//                    .set("color", "#90bdf9") // Optional: make the icon red
                    .set("cursor", "pointer");
            deleteButton.addClickListener(event -> deleteRow(food));
            return deleteButton;
        });

        chosenFoodGrid.setItems(chosenfoodList);


        Binder<Food> binder = new Binder<>(Food.class);
        Editor<Food> editor = chosenFoodGrid.getEditor();
        editor.setBinder(binder);

        NumberField gramField = new NumberField();
        gramField.addBlurListener(blur -> refresh());
        gramField.setWidthFull();
//        gramField.getStyle().set("color", "red");
//        gramField.getStyle().set("font-weight", "bold");
//        gramField.getStyle().set("border", "1px solid #ccc");



//        addCloseHandler(firstNameField, editor);
        binder.forField(gramField)
                .asRequired("Gram name must not be empty or 0")
                .bind(Food::getGram, Food::setGram);
        gramKolonne.setEditorComponent(gramField);


        chosenFoodGrid.addItemClickListener(e -> {
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
        badge.getElement().getThemeList().add("badge");
        badge.setVisible(false);
        return badge;
    }


    private Component createColoredValue(String label, double value, String color) {
        Div container = new Div();
        container.getStyle()
                .set("display", "inline-block")
                .set("margin-right", "15px")
                .set("padding", "5px 10px")
                .set("border-left", "4px solid " + color)
                .set("background-color", "rgba(0,0,0,0.05)");

        Span labelSpan = new Span(label + ": ");
        labelSpan.getStyle().set("font-weight", "bold");

        Span valueSpan = new Span(String.format("%.2f procent", value));
        valueSpan.getStyle().set("color", color);

        container.add(labelSpan, valueSpan);
        return container;
    }


    private Component createColorBox(String label, double value, String bgColor) {
        Div box = new Div();
        box.getStyle()
                .set("display", "inline-block")
                .set("margin-right", "12px")
                .set("padding", "6px 12px")
                .set("background-color", bgColor)
                .set("color", "white")
                .set("border-radius", "3px")
                .set("font-weight", "500");

        box.setText(label + ": " + String.format("%.2f%%", value));
        return box;
    }

    private void updateBadge(Span badge, String text, String theme) {


//        badge.setText(text);
//        badge.getElement().getThemeList().clear(); // Clear existing themes
//        badge.getElement().getThemeList().add("badge");
//        badge.getElement().getThemeList().add(theme); // Add additional theme variant like "success", "error", etc.
//        badge.setVisible(true);



//        badge.setText(text);
//        // Clear any existing theme classes
//        badge.getElement().getThemeList().clear();
//
//        // Apply direct styling
//        badge.getStyle()
//                .set("background-color", "blue")
//                .set("color", "white")
//                .set("border-radius", "4px")
//                .set("padding", "4px 8px")
//                .set("font-size", "14px")
//                .set("font-weight", "500")
//                .set("margin-right", "8px")
//                .set("display", "inline-block");
//
//        badge.setVisible(true);



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

        foodChoiceComboBox.setClearButtonVisible(true);
        MealTotals mealTotals = opskrift.calculateTotals();

        List<Grid.Column<Food>> alleKollonner = chosenFoodGrid.getColumns();
        for (Grid.Column<Food> column : alleKollonner) {
            String headerText = column.getHeaderText();
            if (headerText == null) {
                continue;
            }

            switch (headerText) {
                case KOLONNE_NAVN:
                    column.setFooter("Ialt:");
                    break;
                case KOLONNE_KALORIER:
                    column.setFooter(String.format("%.2f", mealTotals.getKalorierIalt()));
                    break;
                case KOLONNE_PROTEIN:
                    column.setFooter(String.format("%.2f", mealTotals.getProteinIalt()));
                    break;
                case KOLONNE_FEDT:
                    column.setFooter(String.format("%.2f", mealTotals.getFedtIalt()));
                    break;
                case KOLONNE_FIBRE:
                    column.setFooter(String.format("%.2f", mealTotals.getFibreIalt()));
                    break;
                case KOLONNE_KULHYDRAT:
                    column.setFooter(String.format("%.2f", mealTotals.getKulhydratIalt()));
                    break;
                case KOLONNE_GRAM:
                    column.setFooter(String.format("%.2f", mealTotals.getGramIalt()));
                    break;
                default:
                    column.setFooter(""); // Default action for unhandled columns
                    break;
            }

            // Clear previous indicators
            bagdes.removeAll();

// Add new colored values
            bagdes.add(
                    createColoredValue("Protein", mealTotals.getOpskriftPercentageProtein(), "#90bdf9"),
//                    createColoredValue("Protein", mealTotals.getOpskriftPercentageProtein(), "#0066CC"),
                    createColoredValue("Kulhydrat", mealTotals.getOpskriftPercentageCarbonhydrates(), "#8aff66"),
//                    createColoredValue("Kulhydrat", mealTotals.getOpskriftPercentageCarbonhydrates(), "#33CC33"),
//                    createColoredValue("Fedt", mealTotals.getOpskriftPercentageFat(), "#eb9934")
                    createColoredValue("Fedt", mealTotals.getOpskriftPercentageFat(), "#ffbd66")
//                    createColoredValue("Fedt", mealTotals.getOpskriftPercentageFat(), "#ff9e99")
//                    createColoredValue("Fedt", mealTotals.getOpskriftPercentageFat(), "#eb7734")
            );

        }


        updateBadge(proteinBadge, String.format("Protein %.2f procent", mealTotals.getOpskriftPercentageProtein()), "badge");
        updateBadge(kulhydratBadge, String.format("Kulhydrat %.2f procent", mealTotals.getOpskriftPercentageCarbonhydrates()), "badge");
        updateBadge(fedtBadge, String.format("Fedt %.2f procent", mealTotals.getOpskriftPercentageFat()), "badge");

        chosenFoodGrid.getDataProvider().refreshAll();
        foodChoiceComboBox.clear();


    }

}
