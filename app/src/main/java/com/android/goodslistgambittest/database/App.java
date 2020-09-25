package com.android.goodslistgambittest.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.goodslistgambittest.api.ApiService;
import com.android.goodslistgambittest.pojo.Goods;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App {
    private static SharedPreferences mSharedPreferences;


    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.gambit-app.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static ApiService apiService = retrofit.create(ApiService.class);

    public static ApiService getApiService() {
        return apiService;
    }

    public static void saveData(Goods goods, Context context, String str) {
        mSharedPreferences = context.getSharedPreferences("GoodsDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(String.valueOf(goods.getId()), str).apply();
    }
    public static void saveData(Goods goods, Context context, Boolean  backgroundOfmBtn_add_to_selectedIsFill) {
        mSharedPreferences = context.getSharedPreferences("GoodsDb", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(goods.getDescription(), String.valueOf(backgroundOfmBtn_add_to_selectedIsFill)).apply();
    }

    public static String loadData(Goods goods, Context context) {
        mSharedPreferences = context.getSharedPreferences("GoodsDb", Context.MODE_PRIVATE);
        return mSharedPreferences.getString(String.valueOf(goods.getId()), "0");
    }

    public static String  loadDataBoolean(Goods goods, Context context) {
        mSharedPreferences = context.getSharedPreferences("GoodsDb", Context.MODE_PRIVATE);
        return mSharedPreferences.getString(goods.getDescription(), "false");
    }
}
