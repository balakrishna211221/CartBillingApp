package com.aloneliontech.cartnew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("NewApi")
public class CustomAdopter extends BaseAdapter implements Filterable{
    FragmentActivity activity;
    ArrayList<HashMap<String, String>> list;
    ArrayList<HashMap<String, String>> filteredlist;
    private DatabaseHandler dbcart;

    CustomFilter customFilter;
    int list_item_row;
    String[] strings; int[] ints;
    public CustomAdopter(FragmentActivity activity, ArrayList<HashMap<String, String>> list, int list_item_row, String[] strings, int[] ints) {
        this.activity=activity;
        this.list=list;
        this.filteredlist=list;
        this.dbcart = new DatabaseHandler(activity);

        this.list_item_row=list_item_row;
        this.strings=strings;
        this.ints=ints;

    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void updateMultiply(int pos, TextView qtycount, String price) {
        HashMap<String, String> map = new HashMap<>();
        map.put("varient_id", list.get(pos).get("id"));
        map.put("product_name", list.get(pos).get("itemName"));
        map.put("title", list.get(pos).get("itemName"));
        map.put("price", /*list.get(pos).get("price")*/price);
//            Log.d("dsfa", CategoryGridList.get(position).getPrice());
        map.put("mrp", list.get(pos).get("price"));
//            Log.d("fd", CategoryGridList.get(position).getImage());
        map.put("product_image", list.get(pos).get("image"));
//            map.put("status",CategoryGridList.get(position).get());
//            map.put("in_stock",CategoryGridList.get(position).getIn_stock());qty
        map.put("unit_value", list.get(pos).get("qty"));
        map.put("unit", "");
        map.put("increament", "0");
        map.put("rewards", "0");
        map.put("stock", "0");
        map.put("product_description", "");

        if (!qtycount.getText().toString().equalsIgnoreCase("0")) {
            if (dbcart.isInCart(map.get("varient_id"))) {
                dbcart.setCart(map, Integer.valueOf(qtycount.getText().toString()));
                  Toast.makeText(activity, "Product quantity is updated in your cart", Toast.LENGTH_SHORT).show();

            } else {
                dbcart.setCart(map, Integer.valueOf(qtycount.getText().toString()));
                  Toast.makeText(activity, "Product quantity is added in your cart", Toast.LENGTH_SHORT).show();

            }
        } else {
            dbcart.removeItemFromCart(map.get("varient_id"));
        }


    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=layoutInflater.inflate(R.layout.list_item_row,null);

        TextView name=view.findViewById(R.id.itemname);
        final TextView price=view.findViewById(R.id.itemprice);
        TextView unit=view.findViewById(R.id.itemqty);
        final TextView minus=view.findViewById(R.id.minus);
        TextView plus=view.findViewById(R.id.plus);
        final TextView qtycount=view.findViewById(R.id.txtQuan);

        final TextView addtext=view.findViewById(R.id.addtext);

        ImageView itemimage=view.findViewById(R.id.itemimages);


        Glide.with(activity)
                .load(list.get(position).get("image"))
                .fitCenter()
                .placeholder(R.drawable.gradient)
                .into(itemimage);
        name.setText(list.get(position).get("itemName"));
        unit.setText("Qty : "+list.get(position).get("qty"));


      //  price.setText("Price : ₹"+list.get(position).get("price")+".00/-");

        final HashMap<String, String> map = new HashMap<>();
        map.put("varient_id", list.get(position).get("id"));

        minus.setVisibility(View.GONE);
        if (dbcart.isInCart(map.get("varient_id"))) {
            int pricee = Integer.parseInt(list.get(position).get("price")) * Integer.parseInt(dbcart.getInCartItemQty(list.get(position).get("id")));

            price.setText("Price : ₹"+/*list.get(position).get("price")+".00/-"*//*dbcart.getInCartItemprice(list.get(position).get("id"))*/String.valueOf(pricee)+".00/-");
            qtycount.setText("" + dbcart.getInCartItemQty(list.get(position).get("id")));
            addtext.setVisibility(View.GONE);

            minus.setVisibility(View.VISIBLE);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addtext.setVisibility(View.GONE);

                    qtycount.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.VISIBLE);

                    int qnty = (int) Double.parseDouble(dbcart.getInCartItemQty(list.get(position).get("id")));

                    qtycount.setText("" + (qnty + 1));

                    int pricee = Integer.parseInt(list.get(position).get("price")) * (qnty + 1);
                    price.setText("Price : ₹" + /*(Integer.parseInt(price.getText().toString()) * (qnty + 1))*/String.valueOf(pricee)+".00/-");
                    //      mrp.setText("" + (mrp * (qnty + 1)));
                     CustomAdopter.this.updateMultiply(position,qtycount, String.valueOf(pricee));

                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qnty = (int) Double.parseDouble(dbcart.getInCartItemQty(list.get(position).get("id")));
                    int pricee = Integer.parseInt(list.get(position).get("price")) * (qnty - 1);

                    if ((qnty - 1) < 0 || (qnty - 1) == 0) {
                        qtycount.setVisibility(View.INVISIBLE);

                        qtycount.setText("" + 0);
                        addtext.setVisibility(View.VISIBLE);
                        qtycount.setVisibility(View.GONE);
                        minus.setVisibility(View.GONE);

                        price.setText("Price : ₹" + list.get(position).get("price") + ".00/-");
                        //      mrp.setText("" + mrp);
                    } else {
                        qtycount.setVisibility(View.VISIBLE);

                        addtext.setVisibility(View.GONE);
                        minus.setVisibility(View.VISIBLE);

                        qtycount.setText("" + (qnty - 1));
                        //   int pricee=Integer.parseInt(list.get(position).get("price"))* (qnty + 1);
                        qtycount.setText("" + (qnty - 1));

                        price.setText("Price : ₹" + /*Integer.parseInt(list.get(position).get("price")) * (qnty - 1)*/String.valueOf(pricee)+".00/-");

/*
                    price.setText("" + (p * (qnty - 1)));
*/
                        //holder.mrp.setText("" + (mrp * (qnty - 1)));
                    }
                     CustomAdopter.this.updateMultiply(position,qtycount, String.valueOf(pricee));

                }
            });
        }
        else {
            price.setText("Price : ₹"+list.get(position).get("price")+".00/-");
            qtycount.setText("" + /*dbcart.getInCartItemQty(list.get(position).get("id"))*/"");

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addtext.setVisibility(View.GONE);


                    int qnty = (int) Double.parseDouble(dbcart.getInCartItemQty(list.get(position).get("id")));

                    qtycount.setText("" + (qnty + 1));
                    int pricee = Integer.parseInt(list.get(position).get("price")) * (qnty + 1);
                    price.setText("Price : ₹" + /*(Integer.parseInt(price.getText().toString()) * (qnty + 1))*/String.valueOf(pricee)+".00/-");
                    //      mrp.setText("" + (mrp * (qnty + 1)));
                    qtycount.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.VISIBLE);

                    CustomAdopter.this.updateMultiply(position,qtycount, String.valueOf(pricee));

                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qnty = (int) Double.parseDouble(dbcart.getInCartItemQty(list.get(position).get("id")));
                    int pricee = Integer.parseInt(list.get(position).get("price")) * (qnty - 1);

                    if ((qnty - 1) < 0 || (qnty - 1) == 0) {
                        qtycount.setVisibility(View.GONE);

                        qtycount.setText("" + 0);
                        addtext.setVisibility(View.VISIBLE);
                        minus.setVisibility(View.GONE);

                        price.setText("Price : ₹" + list.get(position).get("price") + ".00/-");
                        //      mrp.setText("" + mrp);
                    } else {
                        qtycount.setVisibility(View.VISIBLE);
                        minus.setVisibility(View.VISIBLE);

                        qtycount.setText("" + (qnty - 1));
                        addtext.setVisibility(View.GONE);
                        Toast.makeText(activity, "5", Toast.LENGTH_SHORT).show();


                        //   int pricee=Integer.parseInt(list.get(position).get("price"))* (qnty + 1);
                        qtycount.setText("" + (qnty - 1));

                        price.setText("Price : ₹"+ /*Integer.parseInt(list.get(position).get("price")) * (qnty - 1)*/String.valueOf(pricee)+".00/-");

/*
                    price.setText("" + (p * (qnty - 1)));
*/
                        //holder.mrp.setText("" + (mrp * (qnty - 1)));
                    }
                    CustomAdopter.this.updateMultiply(position,qtycount, String.valueOf(pricee));

                }
            });
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        if (customFilter == null)
        {
            customFilter=new CustomFilter();
        }
        return customFilter;
    }

    public class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if (constraint!=null&&constraint.length()>0) {
                constraint = constraint.toString().toUpperCase();


                ArrayList<HashMap<String, String>> filters = new ArrayList<>();
                for (int i = 0; i < filteredlist.size(); i++) {
                    if (filteredlist.get(i).get("itemName").toUpperCase().contains(constraint)) {
                       // Itempojopojo singlerow = new Itempojopojo(list.get(i).get("itemName"), list.get(i).get("price"), list.get(i).get("qty"), list.get(i).get("image"), list.get(i).get("id"));
                        HashMap<String, String> item = new HashMap<>();
                        item.put("itemName", filteredlist.get(i).get("itemName"));
                        item.put("qty", filteredlist.get(i).get("qty"));
                        item.put("price",filteredlist.get(i).get("price"));
                        item.put("image",filteredlist.get(i).get("image"));
                        item.put("id",filteredlist.get(i).get("id"));
                       // list.add(item);
                        filters.add(item);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }
            else {
                results.count=filteredlist.size();
                results.values=filteredlist;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            list= (ArrayList<HashMap<String, String>>) results.values;

            notifyDataSetChanged();
        }
    }
}
