package dk.brokso.vaadintut.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;
import dk.brokso.vaadintut.data.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Route("")
public class MainView extends VerticalLayout {

    private final ComboBox<FoodItem> foodChoiceComboBox;
    private final Grid<Food> chosenFoodGrid;
    private final HorizontalLayout bagdes;
    private Span proteinBadge;
    private Span kulhydratBadge;
    private Span fedtBadge;

    // New components for recipe management
    private TextField recipeNameField;
    private TextArea recipeDescriptionField;
    private ComboBox<String> savedRecipesComboBox;

    private final List<Food> chosenfoodList = new ArrayList<>();
    private final Opskrift opskrift = new Opskrift(chosenfoodList);
    private final static String KOLONNE_NAVN = "Navn";
    private final static String KOLONNE_GRAM = "Gram";
    private final static String KOLONNE_KALORIER = "Kalorier";
    private final static String KOLONNE_PROTEIN = "Protein";
    private final static String KOLONNE_KULHYDRAT = "Kulhydrat";
    private final static String KOLONNE_FEDT = "Fedt";
    private final static String KOLONNE_FIBRE = "Fibre";
    private final static String KOLONNE_MAETHED = "Mæthed";

    // Directory to store recipes
    private final static String RECIPES_DIR = "recipes";


    MainView(Dataloader dataloader) {
        // Create recipes directory if it doesn't exist
        createRecipesDirectory();

        UI.getCurrent().getElement().getThemeList().add(Lumo.DARK);

        List<FoodItem> foodListToChooseFrom = dataloader.getFood();

        // Recipe management section
        VerticalLayout recipeManagementLayout = new VerticalLayout();
        recipeManagementLayout.setWidthFull();

        // Recipe name field
        recipeNameField = new TextField("Opskrift navn");
        recipeNameField.setWidthFull();

        // Recipe description field
        recipeDescriptionField = new TextArea("Beskrivelse");
        recipeDescriptionField.setWidthFull();
        recipeDescriptionField.setHeight("100px");

        // Saved recipes dropdown
        savedRecipesComboBox = new ComboBox<>("Gemte opskrifter");
        savedRecipesComboBox.setWidthFull();
        updateSavedRecipesList();
        savedRecipesComboBox.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                loadRecipe(event.getValue());
            }
        });

        // Save and load buttons
        Button saveButton = new Button("Gem opskrift", VaadinIcon.DOWNLOAD.create());
        saveButton.addClickListener(e -> saveRecipe());

        Button deleteRecipeButton = new Button("Slet opskrift", VaadinIcon.TRASH.create());
        deleteRecipeButton.getStyle().set("color", "#ffbd66");
        deleteRecipeButton.addClickListener(e -> deleteRecipe());

        // Add recipe management components
        HorizontalLayout recipeFieldsLayout = new HorizontalLayout(recipeNameField, savedRecipesComboBox);
        recipeFieldsLayout.setWidthFull();

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, deleteRecipeButton);
        buttonLayout.setWidthFull();

        recipeManagementLayout.add(recipeFieldsLayout, recipeDescriptionField, buttonLayout);
        add(recipeManagementLayout);

//      Food choice
        VerticalLayout topOfPage = new VerticalLayout();
        topOfPage.setWidthFull();
        foodChoiceComboBox = new ComboBox<>("Vælg Mad");
        foodChoiceComboBox.setItems(foodListToChooseFrom);
        foodChoiceComboBox.setItemLabelGenerator(FoodItem::getName);
        foodChoiceComboBox.setWidthFull();
        foodChoiceComboBox.addValueChangeListener(changeInValue -> receiveChosenValue(changeInValue.getValue()));
        topOfPage.add(foodChoiceComboBox);
        add(topOfPage);

//      Chosen foods
        this.chosenFoodGrid = new Grid<>(Food.class, false);
        Grid.Column<Food> navnKolonne = chosenFoodGrid.addColumn(Food::getName).setHeader(KOLONNE_NAVN).setAutoWidth(true);

        // Replace the gram column with an editable component column
        Grid.Column<Food> gramKolonne = chosenFoodGrid.addComponentColumn(food -> {
            NumberField gramField = new NumberField();
            gramField.setValue(food.getGram());
            gramField.setWidthFull();
            gramField.setMin(0);
            gramField.setStepButtonsVisible(false);
            gramField.setAutoselect(true);

            // Update the food item when the value changes
            gramField.addValueChangeListener(e -> {
                if (e.getValue() != null) {
                    food.setGram(e.getValue());
                    refresh();
                }
            });

            return gramField;
        }).setHeader(KOLONNE_GRAM).setAutoWidth(true);

        Grid.Column<Food> kalorieKolonne = chosenFoodGrid.addColumn(Food::getTotalCalories).setHeader(KOLONNE_KALORIER).setAutoWidth(true);
        Grid.Column<Food> protein = chosenFoodGrid.addColumn(Food::getGramProtein).setHeader(KOLONNE_PROTEIN).setAutoWidth(true);
        Grid.Column<Food> kulhydrat = chosenFoodGrid.addColumn(Food::getGramCarbonhydrates).setHeader(KOLONNE_KULHYDRAT).setAutoWidth(true);
        Grid.Column<Food> fedt = chosenFoodGrid.addColumn(Food::getGramFat).setHeader(KOLONNE_FEDT).setAutoWidth(true);
        Grid.Column<Food> fibre = chosenFoodGrid.addColumn(Food::getGramDietaryfibre).setHeader(KOLONNE_FIBRE).setAutoWidth(true);
        Grid.Column<Food> maethed = chosenFoodGrid.addColumn(Food::getMaethed).setHeader(createHeaderWithTooltip(KOLONNE_MAETHED, "Hvor mæt du bliver")).setAutoWidth(true).setKey(KOLONNE_MAETHED);


        chosenFoodGrid.addComponentColumn(food -> {
            Button deleteButton = new Button(VaadinIcon.TRASH.create());
            deleteButton.getStyle()
                    .set("background-color", "transparent")
                    .set("border", "none")
                    .set("box-shadow", "none")
                    .set("color", "#ffbd66")
                    .set("cursor", "pointer");
            deleteButton.addClickListener(event -> deleteRow(food));
            return deleteButton;
        });

        chosenFoodGrid.setItems(chosenfoodList);

        proteinBadge = initializeBadge();
        kulhydratBadge = initializeBadge();
        fedtBadge = initializeBadge();
        bagdes = new HorizontalLayout(proteinBadge, kulhydratBadge, fedtBadge);
        VerticalLayout badgeContainer = new VerticalLayout();
        badgeContainer.add(chosenFoodGrid);
        badgeContainer.add(bagdes);
        add(badgeContainer);


        Button Slanketips = new Button("Slanketips", event ->
                getUI().ifPresent(ui -> ui.navigate("tips"))
        );
        VerticalLayout linksContainer = new VerticalLayout();
        linksContainer.add(Slanketips);

        add(linksContainer);
    }

    private Component createHeaderWithTooltip(String headerText, String tooltipText) {
        Span headerLabel = new Span(headerText);

        Icon infoIcon = VaadinIcon.INFO_CIRCLE.create();
        infoIcon.getStyle()
                .set("cursor", "pointer")
                .set("color", "#ffbd66");

        infoIcon.getStyle().set("cursor", "pointer");

        // Vaadin's built-in tooltip component
        Tooltip tooltip = Tooltip.forComponent(infoIcon)
                .withText("Mæthedstallet er et beregnet mål, som udtrykker opnået mæthed per spist kalorie. Målet holder godt vand i de fleste tilfælde, men skal kombineres med ens egen mæthedsoplevelse.")
                .withPosition(Tooltip.TooltipPosition.BOTTOM);


        return new HorizontalLayout(headerLabel, infoIcon);
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
                    column.setFooter("");
                    break;
            }
        }

        chosenFoodGrid.getColumnByKey(KOLONNE_MAETHED).setFooter(String.format("%.2f", mealTotals.getMaethed()));

        bagdes.removeAll();
        bagdes.add(
                createColoredValue("Protein", mealTotals.getOpskriftPercentageProtein(), "#90bdf9"),
                createColoredValue("Kulhydrat", mealTotals.getOpskriftPercentageCarbonhydrates(), "#8aff66"),
                createColoredValue("Fedt", mealTotals.getOpskriftPercentageFat(), "#ffbd66")
        );

        // Force a refresh of the grid data
        ((ListDataProvider<Food>) chosenFoodGrid.getDataProvider()).refreshAll();
        foodChoiceComboBox.clear();
        chosenFoodGrid.setItems(new ArrayList<>(chosenfoodList));
    }

    // Create the recipes directory if it doesn't exist
    private void createRecipesDirectory() {
        try {
            Path path = Paths.get(RECIPES_DIR);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            Notification.show("Fejl ved oprettelse af opskrifts-mappe: " + e.getMessage(),
                    3000, Notification.Position.MIDDLE);
        }
    }

    // Save the current recipe
    private void saveRecipe() {
        String recipeName = recipeNameField.getValue();

        if (recipeName == null || recipeName.trim().isEmpty()) {
            Notification notification = Notification.show("Angiv venligst et navn til opskriften");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        if (chosenfoodList.isEmpty()) {
            Notification notification = Notification.show("Tilføj venligst nogle ingredienser til opskriften");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Prepare the recipe for saving
        SavedRecipe savedRecipe = new SavedRecipe();
        savedRecipe.setName(recipeName);
        savedRecipe.setDescription(recipeDescriptionField.getValue());
        savedRecipe.setIngredients(new ArrayList<>(chosenfoodList));

        // Save the recipe to a file
        try {
            FileOutputStream fileOut = new FileOutputStream(RECIPES_DIR + "/" + recipeName + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(savedRecipe);
            out.close();
            fileOut.close();

            Notification notification = Notification.show("Opskrift gemt: " + recipeName);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            // Update the saved recipes list
            updateSavedRecipesList();
        } catch (IOException e) {
            Notification notification = Notification.show("Fejl ved gemning af opskrift: " + e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    // Load a recipe by name
    private void loadRecipe(String recipeName) {
        try {
            FileInputStream fileIn = new FileInputStream(RECIPES_DIR + "/" + recipeName + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SavedRecipe savedRecipe = (SavedRecipe) in.readObject();
            in.close();
            fileIn.close();

            // Update UI with loaded recipe
            recipeNameField.setValue(savedRecipe.getName());
            recipeDescriptionField.setValue(savedRecipe.getDescription() != null ? savedRecipe.getDescription() : "");

            // Clear current ingredients and add the loaded ones
            chosenfoodList.clear();
            chosenfoodList.addAll(savedRecipe.getIngredients());

            // Refresh the UI
            refresh();

            Notification notification = Notification.show("Opskrift indlæst: " + recipeName);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } catch (IOException | ClassNotFoundException e) {
            Notification notification = Notification.show("Fejl ved indlæsning af opskrift: " + e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    // Delete the currently selected recipe
    private void deleteRecipe() {
        String recipeName = savedRecipesComboBox.getValue();

        if (recipeName == null || recipeName.trim().isEmpty()) {
            Notification notification = Notification.show("Vælg venligst en opskrift at slette");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        // Confirm deletion
        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle("Bekræft sletning");

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(new Span("Er du sikker på, at du vil slette opskriften \"" + recipeName + "\"?"));
        dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button confirmButton = new Button("Slet", e -> {
            try {
                Files.deleteIfExists(Paths.get(RECIPES_DIR + "/" + recipeName + ".ser"));
                updateSavedRecipesList();

                if (recipeNameField.getValue().equals(recipeName)) {
                    recipeNameField.setValue("");
                    recipeDescriptionField.setValue("");
                }

                Notification notification = Notification.show("Opskrift slettet: " + recipeName);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                savedRecipesComboBox.clear();
            } catch (IOException ex) {
                Notification notification = Notification.show("Fejl ved sletning af opskrift: " + ex.getMessage());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            confirmDialog.close();
        });
        confirmButton.getStyle().set("color", "#ffbd66");

        Button cancelButton = new Button("Annuller", e -> confirmDialog.close());
        buttonLayout.add(confirmButton, cancelButton);

        dialogLayout.add(buttonLayout);
        confirmDialog.add(dialogLayout);
        confirmDialog.open();
    }

    // Update the saved recipes list in the combobox
    private void updateSavedRecipesList() {
        try {
            List<String> recipeNames = Files.list(Paths.get(RECIPES_DIR))
                    .filter(path -> path.toString().endsWith(".ser"))
                    .map(path -> path.getFileName().toString().replace(".ser", ""))
                    .collect(Collectors.toList());

            savedRecipesComboBox.setItems(recipeNames);
        } catch (IOException e) {
            Notification notification = Notification.show("Fejl ved indlæsning af gemte opskrifter: " + e.getMessage());
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
}

// Serializable class to save recipe data
class SavedRecipe implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private List<Food> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Food> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Food> ingredients) {
        this.ingredients = ingredients;
    }
}