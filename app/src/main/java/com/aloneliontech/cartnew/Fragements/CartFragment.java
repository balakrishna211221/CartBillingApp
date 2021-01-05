package com.aloneliontech.cartnew.Fragements;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aloneliontech.cartnew.Adopter.Cart_adapter;
import com.aloneliontech.cartnew.CartModel;
import com.aloneliontech.cartnew.DatabaseHandler;
import com.aloneliontech.cartnew.MainActivity;
import com.aloneliontech.cartnew.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartFragment extends androidx.fragment.app.Fragment {
    Button btn_ShopNOw;
    RecyclerView recyclerView;
    LinearLayout ll_Checkout;
    RelativeLayout noData,viewCart;
    TextView totalItems,btn_Checkout;
    public static   TextView tv_total ;
    private List<CartModel> cartList = new ArrayList<>();
    private DatabaseHandler db;

    public CartFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cartlayout, container, false);

        recyclerView=view.findViewById(R.id.recyclerCart);
        btn_ShopNOw=view.findViewById(R.id.btn_ShopNOw);
        viewCart=view.findViewById(R.id.viewCartItems);
        tv_total=view.findViewById(R.id.txt_totalamount);
        totalItems=view.findViewById(R.id.txt_totalQuan);
        btn_Checkout=view.findViewById(R.id.btn_Checkout);
        noData=view.findViewById(R.id.noData);
        db = new DatabaseHandler(getActivity());
        btn_Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.clearCart();
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        db = new DatabaseHandler(getActivity());

        ArrayList<HashMap<String, String>> map = db.getCartAll();

       /* Cart_adapter adapter = new Cart_adapter(getActivity(), map, () -> {
            if (*//*db.getCartCount()*//*db.getCartCountofstore(sessionManagement.getAreaid())== 0) {
                noData.setVisibility(View.VISIBLE);
                viewCart.setVisibility(View.GONE);
            }
        });*/
        if (db.getCartCount()==0){
            noData.setVisibility(View.VISIBLE);
            viewCart.setVisibility(View.GONE);

        }
        tv_total.setText("Total Rs."+db.getTotalAmount()+".00/-");

        Cart_adapter cart_adapter=new Cart_adapter(getActivity(),map,noData,viewCart,tv_total);
        recyclerView.setAdapter(cart_adapter);
        cart_adapter.notifyDataSetChanged();

        updateData();
        return view;
    }

    public void updateData() {
        totalItems.setText(/* db.getCartCount()*/"Total Items:"+db.getCartCount());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        // ((MainActivity) getActivity()).setCartCounter("" + db.getCartCount());
//        }

    }


    private void showBloackDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setCancelable(true);
        alertDialog.setMessage("You are blocked from backend.\n Please Contact with customer care!");
//        alertDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }
}
