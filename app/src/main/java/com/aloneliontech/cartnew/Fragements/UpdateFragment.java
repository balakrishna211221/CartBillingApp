package com.aloneliontech.cartnew.Fragements;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aloneliontech.cartnew.Adopter.updateCustomAdopter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateFragment extends Fragment {
    ListView listView;
    updateCustomAdopter adapter;
    ProgressDialog loading;
    EditText search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.updatelayout, container, false);

        listView = view.findViewById(R.id.lv_item);
        search=view.findViewById(R.id.search);
        getItems();
        return view;
    }

    private void getItems() {

        loading =  ProgressDialog.show(getActivity(),"Loading","please wait",false,true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyiocV9hLKIug2hkJKm_5FKnbXmp4z_suxQCrzlejqsjlxIni4/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        parseItems(response);
                        //      Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                   /*     Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);*/

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();

                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action","getItems");


                return parmas;
            }
        };


        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        queue.add(stringRequest);

    }


    private void parseItems(String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String itemName = jo.getString("itemName");
                String brand = jo.getString("qty");
                String price = jo.getString("price");
                String image = jo.getString("image");
                String id = jo.getString("id");


                HashMap<String, String> item = new HashMap<>();
                item.put("itemName", itemName);
                item.put("qty", brand);
                item.put("price",price);
                item.put("image",image);
                item.put("id",id);
                list.add(item);

                //   Toast.makeText(getActivity(), ""+list, Toast.LENGTH_SHORT).show();




                /*    for (int i = 0; i < filteredlist.size(); i++) {*/



                /*  }*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();

        }
        adapter = new updateCustomAdopter(getActivity(),list,R.layout.list_item_row,
                new String[]{"itemName","price","qty"},new int[]{R.id.itemname,R.id.itemprice,R.id.itemqty});




        listView.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loading.dismiss();
    }
}
