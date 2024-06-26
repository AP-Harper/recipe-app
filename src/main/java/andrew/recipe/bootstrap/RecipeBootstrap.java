package andrew.recipe.bootstrap;

import andrew.recipe.domain.*;
import andrew.recipe.repositories.CategoryRepository;
import andrew.recipe.repositories.RecipeRepository;
import andrew.recipe.repositories.UnitOfMeasureRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Loading bootstrap data");
        recipeRepository.saveAll(getRecipes());

    }


    private UnitOfMeasure getMeasure(String measurement) {
        return unitOfMeasureRepository.findByDescription(measurement).
                orElseThrow(() -> new RuntimeException
                        ("Unit of measure " + measurement + " not found"));
    }

    private Category getCategory(String category) {
        return categoryRepository.findByDescription(category).
                orElseThrow(() -> new RuntimeException("Category " + category + " not found"));
    }


    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);

        UnitOfMeasure teaspoon = getMeasure("Teaspoon");
        UnitOfMeasure tablespoon = getMeasure("Tablespoon");
        UnitOfMeasure cup = getMeasure("Cup");
        UnitOfMeasure pinch = getMeasure("Pinch");
        UnitOfMeasure ounce = getMeasure("Ounce");
        UnitOfMeasure dash = getMeasure("Dash");
        UnitOfMeasure pint = getMeasure("Pint");
        UnitOfMeasure each = getMeasure("Each");


        Category american =  getCategory("American");
        Category italian =  getCategory("Italian");
        Category mexican =  getCategory("Mexican");
        Category fastFood =  getCategory("Fast Food");

        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setCookTime(10);
        guacamole.setPrepTime(15);
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");

        guacamole.setNotes(guacNotes);
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setSource("Simply Recipes");
        guacamole.setServings(4);
        guacamole.getIngredients().add(new Ingredient("ripe avocados", new BigDecimal(2), each));

        guacamole.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon));
        guacamole.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tablespoon));
        guacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoon));
        guacamole.addIngredient(new Ingredient("serrano chillis, stems and seeds removed, minced", new BigDecimal(2), each));
        guacamole.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoon));
        guacamole.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dash));
        guacamole.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), each));

        guacamole.getCategories().add(american);
        guacamole.getCategories().add(italian);

        recipes.add(guacamole);


        // Tacos

        Recipe tacos = new Recipe();
        tacos.setDescription("Spicy Grilled Chicken Taco");
        tacos.setCookTime(9);
        tacos.setServings(4);
        tacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacos.setSource("Simply Recipes");
        tacos.setPrepTime(20);
        tacos.setDifficulty(Difficulty.MODERATE);

        tacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacoNotes.setRecipe(tacos);


        tacos.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoon));
        tacos.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoon));
        tacos.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoon));
        tacos.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoon));
        tacos.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoon));
        tacos.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), each));
        tacos.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tablespoon));
        tacos.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoon));
        tacos.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoon));
        tacos.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tablespoon));
        tacos.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), each));
        tacos.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cup));
        tacos.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), each));
        tacos.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), each));
        tacos.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pint));
        tacos.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), each));
        tacos.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), each));
        tacos.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cup));
        tacos.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), each));

        tacos.getCategories().add(american);
        tacos.getCategories().add(mexican);

        recipes.add(tacos);
        return recipes;








    }
}
