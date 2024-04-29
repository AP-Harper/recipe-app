package andrew.recipe.controllers;

import andrew.recipe.domain.Category;
import andrew.recipe.domain.UnitOfMeasure;
import andrew.recipe.repositories.CategoryRepository;
import andrew.recipe.repositories.UnitOfMeasureRepository;
import andrew.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLOutput;
import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }


}
