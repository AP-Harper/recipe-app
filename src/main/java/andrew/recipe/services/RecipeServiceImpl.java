package andrew.recipe.services;

import andrew.recipe.domain.Recipe;
import andrew.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findByID(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        if(recipeOptional.isEmpty()) {
            throw new RuntimeException("Recipe not found");
        }

        return recipeOptional.get();

    }


}
