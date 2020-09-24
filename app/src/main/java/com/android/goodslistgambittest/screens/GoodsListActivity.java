package com.android.goodslistgambittest.screens;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.goodslistgambittest.R;
import com.android.goodslistgambittest.helper.SimpleItemTouchHelperCallback;
import com.android.goodslistgambittest.adapters.GoodsAdapter;
import com.android.goodslistgambittest.database.App;
import com.android.goodslistgambittest.pojo.Goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodsListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewGoods;
    private ArrayList<Goods> data;
    private GoodsAdapter mAdapter;
    private SimpleItemTouchHelperCallback mSimpTouchHelp = new SimpleItemTouchHelperCallback();
    private ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mSimpTouchHelp);
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        initViews();
    }

    private void initViews() {
        mRecyclerViewGoods = (RecyclerView) findViewById(R.id.recyclerViewGoods);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerViewGoods.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getApplicationContext());
        mRecyclerViewGoods.setLayoutManager(layoutManager);
        data = new ArrayList<>();
        mItemTouchHelper.attachToRecyclerView(mRecyclerViewGoods);
        loadJSON();
    }

    private void loadJSON() {
        Call<List<Goods>> call = App.getApiService().getGoods();
        call.enqueue(new Callback<List<Goods>>() {
            @Override
            public void onResponse(Call<List<Goods>> call, Response<List<Goods>> response) {
                Log.d("GoodsListActivity", String.valueOf(response.body()));
                List<Goods> goodsResponse = response.body();
                assert goodsResponse != null;
                data.addAll(goodsResponse);
                mAdapter = new GoodsAdapter(data);
                mRecyclerViewGoods.setAdapter(mAdapter);

                ItemTouchHelper.Callback callback =
                        new SimpleItemTouchHelperCallback(mAdapter);
                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                touchHelper.attachToRecyclerView(mRecyclerViewGoods);
            }

            @Override
            public void onFailure(Call<List<Goods>> call, Throwable t) {
                Log.d("Error", Objects.requireNonNull(t.getMessage()));
            }
        });
    }
}
