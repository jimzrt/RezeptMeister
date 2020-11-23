package com.jimzrt.RezeptMeister.web;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.Hibernate;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jimzrt.RezeptMeister.model.Ingredient;
import com.jimzrt.RezeptMeister.model.Recipe;
import com.jimzrt.RezeptMeister.model.Tag;
import com.jimzrt.RezeptMeister.model.external.SearchFilter;
import com.jimzrt.RezeptMeister.model.specification.GenericSpecificationsBuilder;
import com.jimzrt.RezeptMeister.model.specification.SearchOperation;
import com.jimzrt.RezeptMeister.repositories.IngredientRepository;
import com.jimzrt.RezeptMeister.repositories.RecipeRepository;
import com.jimzrt.RezeptMeister.repositories.TagRepository;

@RestController
public class WebController {

	private static final Logger log = LoggerFactory.getLogger(WebController.class);

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	TagRepository tagRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping("/ingredient")
	public List<Ingredient> findIngredient(@RequestParam("name") String name,
			@RequestParam("count") int count) {

//		try {
//			Random random = new Random();
//			Thread.sleep(random.ints(1000, 2000).findFirst().getAsInt());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		//if (countOpt.isPresent()) {
			//Pageable pageable = PageRequest.of(0, countOpt.get());
			
			FullTextEntityManager fullTextEntityManager 
			  = Search.getFullTextEntityManager(entityManager);
			 
			QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory() 
			  .buildQueryBuilder()
			  .forEntity(Ingredient.class)
			  .get();
//			
//			Query wildcardQuery = queryBuilder
//					  .keyword()
//					  //.wildcard()
//					  .fuzzy()
//					  .onField("name")
//					  .matching(name)
//					  .createQuery();
			
			Query simpleQueryStringQuery = queryBuilder
					  .simpleQueryString()
					  .onFields("name")
					  .withAndAsDefaultOperator()
					  .matching("(" + name + " | " + name + "*) | " + name + "~2")
					  .createQuery();
			FullTextQuery jpaQuery
			  = fullTextEntityManager.createFullTextQuery(simpleQueryStringQuery, Ingredient.class);
			
			jpaQuery.setMaxResults(count);
//			List resultList = jpaQuery.getResultList();
//			if(resultList.isEmpty()) {
//				
//			}
			
			return jpaQuery.getResultList();
			
		//	return ingredientRepository.findByNameContainingIgnoreCase(name, pageable);
//			List<Ingredient> all = ingredientRepository.findByNameIgnoreCase(name, pageable);
//			all.addAll(ingredientRepository.findByNameStartingWithIgnoreCase(name, pageable));
//			all.addAll());
//			return all.stream().distinct().limit(countOpt.get()).collect(Collectors.toList());

	//	}

		//return ingredientRepository.findByNameContainingIgnoreCase(name);
	}

	@RequestMapping("/recipe")
	public List<Recipe> findRecipe(@RequestParam("name") String name,
			@RequestParam("count") Optional<Integer> countOpt) {

//		try {
//			Random random = new Random();
//			Thread.sleep(random.ints(1000, 2000).findFirst().getAsInt());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
		if (countOpt.isPresent()) {
			Pageable pageable = PageRequest.of(0, countOpt.get());
			return recipeRepository.findByTitleContainingIgnoreCase(name, pageable);
		}

		return recipeRepository.findByTitleContainingIgnoreCase(name);

	}

	@RequestMapping("/recipe/seo")
	public Recipe findRecipe(@RequestParam("seoTitle") String seoTitle) {

//		try {
//			Random random = new Random();
//			Thread.sleep(random.ints(1000, 2000).findFirst().getAsInt());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//
		var recipe = recipeRepository.findBySeoTitle(seoTitle);

		// init lazy-loaded collections
		Hibernate.initialize(recipe.getDirections());
		Hibernate.initialize(recipe.getIngredients());
		Hibernate.initialize(recipe.getTags());
		Hibernate.initialize(recipe.getNutrition());
		Hibernate.initialize(recipe.getIngredientGroups());

		return recipe;

	}

	@RequestMapping(value = "/recipe/count/id/{ingredientId}", method = RequestMethod.POST)
	public long countRecipesByIngredientId(@RequestBody SearchFilter searchFilter, @PathVariable long ingredientId) {

		System.out.println("count: " + searchFilter);

		StopWatch watch = new StopWatch();
		watch.start();

		GenericSpecificationsBuilder<Recipe> builder = new GenericSpecificationsBuilder<>();
		builder.with("ingredients", SearchOperation.EQUALITY, Collections.singletonList(ingredientId));
		var specification = builder.fromFilter(searchFilter);

		long recipeCount = recipeRepository.count(specification);

		watch.stop();
		log.info(String.format("searching (count) took %s ms", watch.getTotalTimeMillis()));
		return recipeCount;
	}

	@RequestMapping(value = "/recipe/count/name/{ingredientName}", method = RequestMethod.POST)
	public long countRecipesByIngredientName(@RequestBody SearchFilter searchFilter,
			@PathVariable String ingredientName) {

		System.out.println("count: " + searchFilter);

		StopWatch watch = new StopWatch();
		watch.start();

		GenericSpecificationsBuilder<Recipe> builder = new GenericSpecificationsBuilder<>();
		builder.with("ingredients", SearchOperation.LIKE_NAME, Collections.singletonList(ingredientName));
		var specification = builder.fromFilter(searchFilter);

		long recipeCount = recipeRepository.count(specification);

		watch.stop();
		log.info(String.format("searching (count) took %s ms", watch.getTotalTimeMillis()));
		return recipeCount;
	}

	@RequestMapping(value = "/recipe/search", method = RequestMethod.POST)
	public Page<Recipe> getBySearch(@RequestBody SearchFilter searchFilter) {
		System.out.println("search: " + searchFilter);

		StopWatch watch = new StopWatch();
		watch.start();

		Pageable pageable = PageRequest.of(searchFilter.getPage(), searchFilter.getCount());

		GenericSpecificationsBuilder<Recipe> builder = new GenericSpecificationsBuilder<>();
		var specification = builder.fromFilter(searchFilter);

		Page<Recipe> recipes = recipeRepository.findAll(specification, pageable);
		recipes.get().forEach(r -> {
			r.getIngredients().size();
			r.getTags().size();
		});
		watch.stop();
		log.info(String.format("searching (search) took %s ms", watch.getTotalTimeMillis()));
		return recipes;

	}

//	@ResponseBody
//	@Cacheable("images")
//	@RequestMapping(value = "/e_images/**", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
//	public byte[] image(HttpServletRequest request) throws IOException {
//		
//	    String requestURL = request.getRequestURL().toString();
//
//	    String pictureUrl = "https://www.edeka.de/" + requestURL.split("/e_images/")[1];
//
//		
//		URL url = new URL(pictureUrl);
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		URLConnection conn = url.openConnection();
//	//	conn.setRequestProperty("User-Agent", "Firefox");
//
//		try (InputStream inputStream = conn.getInputStream()) {
//		  int n = 0;
//		  byte[] buffer = new byte[1024*1024];
//		  while (-1 != (n = inputStream.read(buffer))) {
//		    output.write(buffer, 0, n);
//		  }
//		}
//		byte[] img = output.toByteArray();
//		return img;
////	    InputStream in = servletContext.getResourceAsStream("images/"+path);
////	    return IOUtils.toByteArray(in);
//	}

	@RequestMapping("/tag")
	public List<Tag> findTag(@RequestParam("name") String name, @RequestParam("count") Optional<Integer> countOpt) {

//		try {
//			Random random = new Random();
//			Thread.sleep(random.ints(1000, 2000).findFirst().getAsInt());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		if (countOpt.isPresent()) {
			Pageable pageable = PageRequest.of(0, countOpt.get());
			return tagRepository.findByNameContainingIgnoreCase(name, pageable);
		}

		return tagRepository.findByNameContainingIgnoreCase(name);
	}

//	@RequestMapping("/recipe")
//	public List<Recipe> findRecipe(@RequestParam("name") String name) {
//		return recipeRepository.findDistinctByIngredientsNameContainingIgnoreCase(name);
//	}

}