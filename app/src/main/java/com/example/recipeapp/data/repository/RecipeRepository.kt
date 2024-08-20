package com.example.recipeapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.recipeapp.data.database.RecipeDao
import com.example.recipeapp.data.model.Meal
import com.example.recipeapp.network.api.RemoteDataSource
import com.example.recipeapp.network.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val remoteDataSource: RemoteDataSource
): IRecipeRepository {
    /**
     * Handles data operations, including fetching data from the network and the local database.
     */

    suspend fun addFavoriteMeal(meal: Meal) {
        recipeDao.addFavoriteMeal(meal)
    }

    suspend fun getAllFavoriteMeals(): List<Meal> = withContext(Dispatchers.IO) {
        recipeDao.getAllFavoriteMeals()
    }

    suspend fun getAllFavoriteMealsId(): List<String> = withContext(Dispatchers.IO) {
        recipeDao.getAllFavoriteMealsId()
    }

    suspend fun deleteFavoriteMeal(meal: Meal) {
        recipeDao.deleteFavoriteMeal(meal)
    }

    // online related
    suspend fun getMealById(id: Int): ApiResponse {
        return remoteDataSource.getMealById(id)
    }
    fun isMealFavorite(mealId: String): LiveData<Boolean> {
        val result = MediatorLiveData<Boolean>()
        result.addSource(recipeDao.isMealFavorite(mealId)) { meal ->
            result.value = meal != null
        }
        return result
    }

}