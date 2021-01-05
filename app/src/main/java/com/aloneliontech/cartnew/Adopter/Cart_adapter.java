package com.aloneliontech.cartnew.Adopter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aloneliontech.cartnew.DatabaseHandler;
import com.aloneliontech.cartnew.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart_adapter extends RecyclerView.Adapter<Cart_adapter.ProductHolder> {
    ArrayList<HashMap<String, String>> list;
    Activity activity;
    String price_tx;
    SharedPreferences preferences;
    String language;
    int lastpostion;
    DatabaseHandler dbHandler;
    private Context context;
    TextView tv_total;
    RelativeLayout noData,viewcart;
    public Cart_adapter(Activity activity, ArrayList<HashMap<String, String>> list, RelativeLayout noData, RelativeLayout viewCart, TextView tv_total) {
        this.list = list;
        this.activity = activity;
        context = activity;
        dbHandler = new DatabaseHandler(activity);
        this.noData=noData;
        this.viewcart=viewCart;
        this.tv_total=tv_total;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart, parent, false);
        context = parent.getContext();
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, final int position) {
        final HashMap<String, String> map = list.get(position);
        holder.currency_indicator.setText("Price :₹");
       /* Picasso.with(activity)
                .load(IMG_URL + map.get("product_image"))
                .into(holder.iv_logo);*/
        Glide.with(activity)
                .load(map.get("product_image"))
                .fitCenter()
                .placeholder(R.drawable.gradient)
                .into(holder.iv_logo);
        holder.tv_title.setText(map.get("product_name"));
        holder.pDescrptn.setText(map.get("product_description"));
        int items = (int) Double.parseDouble(dbHandler.getInCartItemQty(map.get("varient_id")));
        int sprice = (int) Double.parseDouble(map.get("price"));

/*
        holder.card_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        int qtyd = Integer.parseInt(dbHandler.getInCartItemQtys(map.get("varient_id")));
        holder.itemcount.setText(" X "+qtyd);

        if (qtyd > 0) {
            holder.tv_add.setVisibility(View.GONE);
            //          holder.ll_addQuan.setVisibility(View.VISIBLE);
//            holder.tv_contetiy.setText("" + qtyd);
            holder.pPrice.setText("" + (sprice * qtyd));
            holder.itemcount.setText(" X "+qtyd);

//            holder.pMrp.setText("" + (sprice * qtyd));
        } else {
            holder.tv_add.setVisibility(View.VISIBLE);
            holder.ll_addQuan.setVisibility(View.GONE);
            holder.pPrice.setText(""+sprice+".00/-");
//            holder.pMrp.setText(cc.getpMrp());
            // holder.tv_contetiy.setText("" + 0);
            holder.itemcount.setText(" X "+qtyd);

        }

//        holder.pPrice.setText("" + sprice * items);
//        holder.tv_contetiy.setText("" + items);
//        holder.minteger = items;
        holder.pQuan.setText("" + map.get("unit_value"));
//        holder.pMrp.setText("" + map.get("mrp"));
//        holder.pMrp.setPaintFlags(holder.pMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tv_add.setVisibility(View.GONE);
                // holder.ll_addQuan.setVisibility(View.VISIBLE);

                //    dbHandler.setCart(map, Integer.valueOf(holder.tv_contetiy.getText().toString()));
                Double items = Double.parseDouble(dbHandler.getInCartItemQty(map.get("varient_id")));
                Double price = Double.parseDouble(map.get("price"));
                Double reward = Double.parseDouble(map.get("rewards"));
                holder.pPrice.setText("" + price * items);
                //  holder.tv_reward.setText("" + reward * items);
                //   holder.tv_total.setText(activity.getResources().getString(R.string.tv_cart_total) + price * items + " " + activity.getResources().getString(R.string.currency));
                updateintent(dbHandler, view.getContext());
            }
        });

        holder.txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHandler.removeItemFromCart(map.get("varient_id"));
                list.remove(position);
                notifyDataSetChanged();
                tv_total.setText("R " + /*dbHandler.getTotalAmount()*/dbHandler.getTotalAmount());
                updateintent(dbHandler, activity);

            }
        });

/*
        holder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = Integer.parseInt(dbHandler.getInCartItemQtys(map.get("varient_id")));
                double price = Double.parseDouble(map.get("price"));
                if ((i - 1) < 0 || (i - 1) == 0) {
                    holder.tv_add.setVisibility(View.VISIBLE);
        //            holder.ll_addQuan.setVisibility(View.GONE);
          //          holder.tv_contetiy.setText("" + 0);
                    holder.pPrice.setText("" + price);
//                    holder.pMrp.setText("" + mrp);
                } else {
                 //   holder.tv_contetiy.setText("" + (i - 1));
                    holder.pPrice.setText("" + (price * (i - 1)));
//                    holder.pMrp.setText("" + (mrp * (i - 1)));
                }
                updateMultiply(position, (i - 1));


//                if (holder.minteger == 1) {
//                    holder.minteger = 1;
//                    display(holder.minteger, holder);
//                    holder.ll_addQuan.setVisibility(View.GONE);
//                    holder.tv_add.setVisibility(View.VISIBLE);
//                } else {
//                    holder.minteger = holder.minteger - 1;
//                    display(holder.minteger, holder);
//
//                }

//                decreaseInteger(holder);
//                updateMultiply(holder, map, position);
            }
        });
*/

/*
        holder.iv_plus.setOnClickListener(v -> {
//            increaseInteger(holder);
//            updateMultiply(holder, map, position);

            try {
                holder.tv_add.setVisibility(View.GONE);
             //   holder.ll_addQuan.setVisibility(View.VISIBLE);
                if (dbHandler == null) {
                    dbHandler = new DatabaseHandler(v.getContext());
                }
                double price = Double.parseDouble(map.get("price"));
                int i = Integer.parseInt(dbHandler.getInCartItemQtys(map.get("varient_id")));
//            cartList.get(position).setpQuan(String.valueOf(i+1));
       //         holder.tv_contetiy.setText("" + (i + 1));
                holder.pPrice.setText("" + (price * (i + 1)));
//                holder.pMrp.setText("" + (mrp * (i + 1)));
                updateMultiply(position, (i + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }


        });
*/
    }

    private void updateMultiply(int pos, int i) {
        try {

            if (i > 0) {
                dbHandler.setCart(list.get(pos), i);
            } else {
                dbHandler.removeItemFromCart(list.get(pos).get("varient_id"));
                list.remove(pos);
                notifyDataSetChanged();
            }
            tv_total.setText("R " + /*dbHandler.getTotalAmount()*/dbHandler.getTotalAmount());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                SharedPreferences preferences = context.getSharedPreferences("GOGrocer", Context.MODE_PRIVATE);
                preferences.edit().putInt("cardqnty", /*dbHandler.getCartCount()*/dbHandler.getCartCount()).apply();
            }
            updateintent(dbHandler, context);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void updateMultiply(ProductHolder holder, HashMap<String, String> map, int position) {




        //     dbHandler.setCart(map, Integer.valueOf(holder.tv_contetiy.getText().toString()));
        Log.d("asfd", holder.tv_contetiy.getText().toString());


        int items = (int) Double.parseDouble(dbHandler.getInCartItemQty(map.get("varient_id")));
        //  Double price = Double.parseDouble(map.get("price"));
//        Double reward = Double.parseDouble(dbHandler.getInCartItemQty(map.get("price")));
        if (items == 1) {
            //       holder.tv_contetiy.setText("" + Integer.valueOf(String.valueOf(items)));
//            holder.pPrice.setText("" + holder.price);
            dbHandler.removeItemFromCart(map.get("varient_id"));
            list.remove(position);
            notifyDataSetChanged();
        } else {
            //    holder.tv_contetiy.setText("" + Integer.valueOf(String.valueOf(items)));
//            holder.pPrice.setText("" + holder.price * items);
        }
        tv_total.setText("R " +/* dbHandler.getTotalAmount()*/dbHandler.getTotalAmount());

        //   holder.tv_total.setText(activity.getResources().getString(R.string.tv_cart_total) + price * items + " " + activity.getResources().getString(R.string.currency));
        updateintent(dbHandler, activity.getApplicationContext());
    }

    public void increaseInteger(ProductHolder holder) {
        holder.minteger = holder.minteger + 1;
        display(holder.minteger, holder);
    }

    public void decreaseInteger(ProductHolder holder) {
        if (holder.minteger == 1) {
            holder.minteger = 1;
            display(holder.minteger, holder);
            //  holder.ll_addQuan.setVisibility(View.GONE);
            holder.tv_add.setVisibility(View.VISIBLE);
        } else {
            holder.minteger = holder.minteger - 1;
            display(holder.minteger, holder);

        }
    }

    private void display(Integer number, ProductHolder holder) {

        //  holder.tv_contetiy.setText("" + number);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void updateintent(DatabaseHandler dbHandler, Context context) {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                SharedPreferences preferences = context.getSharedPreferences("GOGrocer", Context.MODE_PRIVATE);
                preferences.edit().putInt("cardqnty", /*dbHandler.getCartCount()*/dbHandler.getCartCount()).apply();

                if (/*dbHandler.getCartCount()*/dbHandler.getCartCount() == 0) {
                    noData.setVisibility(View.VISIBLE);
                    viewcart.setVisibility(View.GONE);
                }
            }
            Intent updates = new Intent("AkhilaCart");
            updates.putExtra("type", "update");
            activity.sendBroadcast(updates);
        }catch (Exception ep){
            ep.printStackTrace();
        }

    }

    class ProductHolder extends RecyclerView.ViewHolder {
        public TextView itemcount,tv_title, txt_close, tv_contetiy, iv_plus, iv_minus, pDescrptn, pQuan, pPrice, pdiscountOff, pMrp, tv_unit, tv_unit_value, currency_indicator;
        public ImageView iv_logo;
        CardView card_cart;
        LinearLayout tv_add, ll_addQuan;
        int minteger = 0;
        int price = 0;

        public ProductHolder(View view) {
            super(view);
            itemcount=view.findViewById(R.id.itemcount);

            tv_title = view.findViewById(R.id.txt_pName);
            currency_indicator = view.findViewById(R.id.currency_indicator);
            iv_logo = view.findViewById(R.id.prodImage);

            //      tv_contetiy = view.findViewById(R.id.txtQuan);
            tv_add = view.findViewById(R.id.btn_Add);
            //    ll_addQuan = view.findViewById(R.id.ll_addQuan);
            //      iv_plus = view.findViewById(R.id.plus);
            //    iv_minus = view.findViewById(R.id.minus);

            pDescrptn = view.findViewById(R.id.txt_pInfo);
            pQuan = view.findViewById(R.id.txt_unit);
            pPrice = view.findViewById(R.id.txt_Pprice);
            pMrp = view.findViewById(R.id.txt_Mrp);
            card_cart=view.findViewById(R.id.card_cart);
            txt_close = view.findViewById(R.id.txt_close);

            //  tv_add.setText(R.string.tv_pro_update);

        }
    }

}

