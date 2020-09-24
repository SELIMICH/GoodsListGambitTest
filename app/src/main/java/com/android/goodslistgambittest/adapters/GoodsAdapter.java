package com.android.goodslistgambittest.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
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
import com.android.goodslistgambittest.helper.ItemTouchHelperAdapter;
import com.android.goodslistgambittest.pojo.Goods;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder> implements ItemTouchHelperAdapter {
    private ArrayList<Goods> mGoods;
    private Context mContext;
    Goods goods;



    public GoodsAdapter(ArrayList<Goods> goods) {
        this.mGoods = goods;
    }


    @NonNull
    @Override
    public GoodsAdapter.GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_activity, parent, false);
        return new GoodsViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final GoodsViewHolder holder, int position) {

        holder.bind(goods = mGoods.get(position));
        mContext = holder.itemView.getContext();
    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mGoods, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mGoods, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mGoods.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

    }


    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mGoodsImage;
        private TextView mGoodsTitle;
        private TextView mGoodsCount;
        private Button mBtnPlus;
        private Button mBtnMinus;
        private TextView mPrice;
        private Button mAdd_to_store;

        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            mGoodsImage = itemView.findViewById(R.id.goods_image);
            mGoodsTitle = itemView.findViewById(R.id.goods_title);
            mGoodsCount = itemView.findViewById(R.id.goods_count);
            mBtnPlus = itemView.findViewById(R.id.btn_plus);
            mBtnMinus = itemView.findViewById(R.id.btn_minus);
            mPrice = itemView.findViewById(R.id.price);
            mAdd_to_store = itemView.findViewById(R.id.btn_add_to_store);
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
//                    notifyItemChanged(getAdapterPosition());
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
        }
    }
}
