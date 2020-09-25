package com.android.goodslistgambittest.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.goodslistgambittest.R;
import com.android.goodslistgambittest.database.App;
import com.android.goodslistgambittest.pojo.Goods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>  {
    private ArrayList<Goods> mGoods;
    private Context mContext;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    Goods goods;


    public GoodsAdapter(ArrayList<Goods> goods) {
        this.mGoods = goods;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public GoodsAdapter.GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_activity, parent, false);
        return new GoodsViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final GoodsViewHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.mSwipeRevealLayout, String.valueOf(mGoods.get(position).getName()));
        viewBinderHelper.closeLayout(String.valueOf(mGoods.get(position).getName()));

        holder.bind(goods = mGoods.get(position));
        mContext = holder.itemView.getContext();

    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }



    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mGoodsImage;
        private TextView mGoodsTitle;
        private TextView mGoodsCount;
        private Button mBtnPlus;
        private Button mBtnMinus;
        private TextView mPrice;
        private Button mAdd_to_store;
        private SwipeRevealLayout mSwipeRevealLayout;
        private Button mBtn_add_to_selected;
        private Boolean backgroundOfmBtn_add_to_selected_Is_Fill = false;

        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            mGoodsImage = itemView.findViewById(R.id.goods_image);
            mGoodsTitle = itemView.findViewById(R.id.goods_title);
            mGoodsCount = itemView.findViewById(R.id.goods_count);
            mBtnPlus = itemView.findViewById(R.id.btn_plus);
            mBtnMinus = itemView.findViewById(R.id.btn_minus);
            mPrice = itemView.findViewById(R.id.price);
            mAdd_to_store = itemView.findViewById(R.id.btn_add_to_store);
            mSwipeRevealLayout = itemView.findViewById(R.id.swipelayout);
            mBtn_add_to_selected = itemView.findViewById(R.id.btn_add_to_selected);
        }

        @SuppressLint({"SetTextI18n", "ResourceAsColor"})
        private void bind(final Goods goods) {

            mGoodsTitle.setText(goods.getName());
            mPrice.setText(goods.getPrice() + mGoodsImage.getContext().getString(R.string.rub_sign));

            Glide.with(mGoodsImage.getContext())
                    .load(goods.getImage())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_baseline_fastfood_24)
                            .centerCrop()
                            .error(R.drawable.ic_launcher_foreground))
                    .into(mGoodsImage);

            String value = App.loadData(goods,mGoodsImage.getContext());
            backgroundOfmBtn_add_to_selected_Is_Fill = Boolean.parseBoolean(App.loadDataBoolean(goods,mGoodsImage.getContext()));
            if (!backgroundOfmBtn_add_to_selected_Is_Fill){
                mBtn_add_to_selected.setBackgroundResource(R.drawable.ic_vector);
                backgroundOfmBtn_add_to_selected_Is_Fill = true;

            }else {
                mBtn_add_to_selected.setBackgroundResource(R.drawable.ic_vectorfill);
                backgroundOfmBtn_add_to_selected_Is_Fill = false;
            }



            mGoodsCount.setText(value);
            int counter = Integer.parseInt(value);
            if (counter != 0) {
                mAdd_to_store.setVisibility(View.INVISIBLE);
                mBtnPlus.setVisibility(View.VISIBLE);
                mBtnMinus.setVisibility(View.VISIBLE);
                mGoodsCount.setVisibility(View.VISIBLE);
            }else {
                mAdd_to_store.setVisibility(View.VISIBLE);
                mBtnMinus.setVisibility(View.INVISIBLE);
                mBtnPlus.setVisibility(View.INVISIBLE);
                mGoodsCount.setVisibility(View.INVISIBLE);
            }


            mAdd_to_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdd_to_store.setVisibility(View.INVISIBLE);
                    mBtnPlus.setVisibility(View.VISIBLE);
                    mBtnMinus.setVisibility(View.VISIBLE);
                    mGoodsCount.setVisibility(View.VISIBLE);

                    String str = String.valueOf(mGoodsCount.getText());
                    int counter = Integer.parseInt(str);
                    counter++;
                    str = String.valueOf(counter);
                    mGoodsCount.setText(str);
                    App.saveData(goods,mContext,str);
                }
            });

            mBtnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = String.valueOf(mGoodsCount.getText());
                    int counter = Integer.parseInt(str);
                    if (counter == 1) {
                        mBtnMinus.setVisibility(View.INVISIBLE);
                        mBtnPlus.setVisibility(View.INVISIBLE);
                        mGoodsCount.setVisibility(View.INVISIBLE);
                        mGoodsCount.setText("0");
                        mAdd_to_store.setVisibility(View.VISIBLE);
                        App.saveData(goods,mContext,"0");
                        return;
                    }
                    counter--;
                    str = String.valueOf(counter);
                    mGoodsCount.setText(str);
                    App.saveData(goods,mContext,str);
                }
            });

            mBtnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = String.valueOf(mGoodsCount.getText());
                    int counter = Integer.parseInt(str);
                    counter++;
                    str = String.valueOf(counter);
                    mGoodsCount.setText(str);
                    App.saveData(goods,mContext,str);
                }
            });

            mBtn_add_to_selected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!backgroundOfmBtn_add_to_selected_Is_Fill){
                        mBtn_add_to_selected.setBackgroundResource(R.drawable.ic_vectorfill);
                        App.saveData(goods,mContext, true);
                        backgroundOfmBtn_add_to_selected_Is_Fill = true;
                    }else {
                        mBtn_add_to_selected.setBackgroundResource(R.drawable.ic_vector);
                        App.saveData(goods,mContext, false);
                        backgroundOfmBtn_add_to_selected_Is_Fill = false;
                    }

                }
            });
        }
    }
}
