package com.example.data

import com.example.api.models.CategoryDTO
import com.example.api.models.ProductDTO
import com.example.data.models.Category
import com.example.data.models.Product
import com.example.database.models.CategoryDBO
import com.example.database.models.ProductDBO

fun ProductDTO.toProduct() : Product {
    return Product(
        dateModified, idMeal, strArea, strCategory, strCreativeCommonsConfirmed, strDrinkAlternate, strImageSource, strIngredient1, strIngredient10, strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15, strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient2, strIngredient20, strIngredient3, strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9, strInstructions, strMeal, strMealThumb, strMeasure1, strMeasure10, strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15, strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure2, strMeasure20, strMeasure3, strMeasure4, strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9, strSource, strTags, strYoutube
    )
}

fun ProductDBO.toProduct() : Product {
    return Product(
        dateModified, idMeal, strArea, strCategory, strCreativeCommonsConfirmed, strDrinkAlternate, strImageSource, strIngredient1, strIngredient10, strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15, strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient2, strIngredient20, strIngredient3, strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9, strInstructions, strMeal, strMealThumb, strMeasure1, strMeasure10, strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15, strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure2, strMeasure20, strMeasure3, strMeasure4, strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9, strSource, strTags, strYoutube
    )
}

fun ProductDTO.toProductDBO() : ProductDBO {
    return ProductDBO(
        dateModified, idMeal, strArea, strCategory, strCreativeCommonsConfirmed, strDrinkAlternate, strImageSource, strIngredient1, strIngredient10, strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15, strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient2, strIngredient20, strIngredient3, strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9, strInstructions, strMeal, strMealThumb, strMeasure1, strMeasure10, strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15, strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure2, strMeasure20, strMeasure3, strMeasure4, strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9, strSource, strTags, strYoutube
    )
}

fun CategoryDTO.toCategories() : Category {
    return Category(idCategory, strCategory, strCategoryDescription, strCategoryThumb)
}

fun CategoryDBO.toCategories() : Category {
    return Category(idCategory, strCategory, strCategoryDescription, strCategoryThumb)
}

fun CategoryDTO.toCategoriesDBO() : CategoryDBO {
    return CategoryDBO(idCategory, strCategory, strCategoryDescription, strCategoryThumb)
}
