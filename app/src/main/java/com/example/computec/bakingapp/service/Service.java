package com.example.computec.bakingapp.service;

import com.example.computec.bakingapp.model.Recipe;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Service {

    @GET("baking.json")
    Observable<ArrayList<Recipe>> getRecipe();
}
