package com.example.computec.bakingapp.ui.recipe;


import com.example.computec.bakingapp.App;
import com.example.computec.bakingapp.R;
import com.example.computec.bakingapp.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipePresenter<V extends RecipeContract.View> extends BasePresenter<V>
        implements RecipeContract.Presenter<V> {

    @Override
    public void getRecipe() {
        if (getMvpView().isNetworkConnected())
            App.service
                    .getRecipe()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(recipes -> {
                        if (isViewAttached()) {
                            getMvpView().showRecipes(recipes);
                        }
                    });
        else if (isViewAttached())
            getMvpView().onError(R.string.no_internet_connection_error);

    }
}
