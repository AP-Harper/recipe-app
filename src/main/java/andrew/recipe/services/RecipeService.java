package andrew.recipe.services;

import andrew.recipe.domain.Recipe;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;


public interface RecipeService {

    public Set<Recipe> getRecipes();

    Recipe findByID(Long l);

    Recipe findByName(String name);


}
