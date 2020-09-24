package com.android.goodslistgambittest.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NutritionFacts {
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("calories")
    @Expose
    private Object calories;
    @SerializedName("protein")
    @Expose
    private Object protein;
    @SerializedName("fat")
    @Expose
    private Object fat;
    @SerializedName("carbohydrates")
    @Expose
    private Object carbohydrates;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Object getCalories() {
        return calories;
    }

    public void setCalories(Object calories) {
        this.calories = calories;
    }

    public Object getProtein() {
        return protein;
    }

    public void setProtein(Object protein) {
        this.protein = protein;
    }

    public Object getFat() {
        return fat;
    }

    public void setFat(Object fat) {
        this.fat = fat;
    }

    public Object getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Object carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
}
