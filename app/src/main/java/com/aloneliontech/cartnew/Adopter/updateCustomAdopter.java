package com.aloneliontech.cartnew.Adopter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.aloneliontech.cartnew.CustomAdopter;
import com.aloneliontech.cartnew.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class updateCustomAdopter extends BaseAdapter implements Filterable {
    FragmentActivity activity;
    ArrayList<HashMap<String, String>> list;
    ArrayList<HashMap<String, String>> filteredlist;

    CustomFilter customFilter;
    int list_item_row;
    String[] strings; int[] ints;
    public updateCustomAdopter(FragmentActivity activity, ArrayList<HashMap<String, String>> list, int list_item_row, String[] strings, int[] ints) {
        this.activity=activity;
        this.list=list;
        this.filteredlist=list;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
TextView update,delete,qty,price,name;
        LayoutInflater layoutInflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=layoutInflater.inflate(R.layout.update_list_item_row,null);

        name=view.findViewById(R.id.itemname);
        price=view.findViewById(R.id.itemprice);
        qty=view.findViewById(R.id.itemqty);
        update=view.findViewById(R.id.update_price);
        delete=view.findViewById(R.id.delete_item);

        ImageView itemimage=view.findViewById(R.id.itemimages);


        Glide.with(activity)
                .load(list.get(position).get("image"))
                .fitCenter()
                .placeholder(R.drawable.gradient)
                .into(itemimage);
        name.setText(list.get(position).get("itemName"));
        price.setText("Price : ₹"+list.get(position).get("price")+".00/-");
        qty.setText("Qty : "+list.get(position).get("qty"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedialog(list.get(position).get("id"),list.get(position).get("itemName"));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(list.get(position).get("id"),list.get(position).get("itemName"));
            }
        });

        return view;
    }

    private void updatedialog(final String id, final String itemName) {
        LayoutInflater factory = LayoutInflater.from(activity);
        final View DialogView = factory.inflate(R.layout.updatedialog, null);
        final AlertDialog Dialog = new AlertDialog.Builder(activity).create();
        Dialog.setView(DialogView);
        final EditText editText=DialogView.findViewById(R.id.update_price_et);

        DialogView.findViewById(R.id.update_dialog_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic

                Dialog.dismiss();
                update(id,itemName,Dialog,editText.getText().toString());
                Toast.makeText(activity, ""+editText.getText().toString()+itemName, Toast.LENGTH_SHORT).show();
            }
        });


        Dialog.show();
    }

    private void delete(final String id, final String itemName) {
        final ProgressDialog loading = ProgressDialog.show(activity,"Adding Item","Please wait");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycby0F0U1W-cRiXyaFkZeuDli7sYxGiv1wyokc9ddsiQhA-VtY76ouiqn/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        try {
                            JSONObject jobj = new JSONObject(response);
                            String s=jobj.getString("result");
                            Toast.makeText(activity,s.toString(),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();

                        Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","delete");
                parmas.put("name",itemName);
                parmas.put("id",id);


                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(activity);

        queue.add(stringRequest);
        Toast.makeText(activity, id+"  "+itemName, Toast.LENGTH_SHORT).show();

    }

    private void update(final String id, final String itemName, final AlertDialog dialog, final String price) {


            final ProgressDialog loading = ProgressDialog.show(activity,"Adding Item","Please wait");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycby0F0U1W-cRiXyaFkZeuDli7sYxGiv1wyokc9ddsiQhA-VtY76ouiqn/exec",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            loading.dismiss();
                            try {
                                JSONObject jobj = new JSONObject(response);
                                String s=jobj.getString("result");
                                Toast.makeText(activity,s.toString(),Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            dialog.dismiss();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();

                            Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parmas = new HashMap<>();

                    //here we pass params
                    parmas.put("action","update");
                    parmas.put("name",itemName);
                    parmas.put("id",id);
                    parmas.put("price",price);


                    return parmas;
                }
            };

            int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

            RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);

            RequestQueue queue = Volley.newRequestQueue(activity);

            queue.add(stringRequest);
          //  Toast.makeText(activity, id+"  "+itemName, Toast.LENGTH_SHORT).show();
    }


    @Override
    public Filter getFilter() {
        if (customFilter == null)
        {
            customFilter=new CustomFilter();
        }
        return customFilter;
    }

    class CustomFilter extends Filter{

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
