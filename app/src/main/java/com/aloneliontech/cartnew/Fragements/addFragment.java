package com.aloneliontech.cartnew.Fragements;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aloneliontech.cartnew.MainActivity;
import com.aloneliontech.cartnew.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;

public class addFragment extends Fragment {


    EditText editTextItemName,editTextprice,editTextimage,qty;
    Button buttonAddItem;
    WebView browser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.addproduct, container, false);
        editTextItemName = (EditText)view.findViewById(R.id.et_item_name);
        editTextprice = (EditText)view.findViewById(R.id.price);
        editTextimage = (EditText)view.findViewById(R.id.imagelink);
        qty = (EditText)view.findViewById(R.id.qty);
        buttonAddItem = (Button)view.findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editTextItemName.getText().toString())){
                    Toast.makeText(getActivity(), "Enter name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(editTextprice.getText().toString())){
                    Toast.makeText(getActivity(), "Enter price", Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(qty.getText().toString())){
                    Toast.makeText(getActivity(), "Enter Qty", Toast.LENGTH_SHORT).show();

                }else if(TextUtils.isEmpty(editTextimage.getText().toString())){
                    Toast.makeText(getActivity(), "Enter image link", Toast.LENGTH_SHORT).show();

                }
                else {

                    addItemToSheet();
                }
            }
        });
        browser = (WebView) view.findViewById(R.id.webview);
        browser.setWebViewClient(new MyBrowser());
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        browser.loadUrl("https://www.google.com/search?q=grocery+images&tbm=isch&ved=2ahUKEwiC5qSRkMjtAhX1QXwKHUliBYEQ2-cCegQIABAA&oq=grocery+&gs_lcp=CgNpbWcQARgAMgQIABBDMgQIABBDMgcIABCxAxBDMgcIABCxAxBDMgQIABBDMgQIABBDMgQIABBDMgcIABCxAxBDMgQIABBDMgQIABBDOgcIIxDqAhAnOgQIIxAnOggIABCxAxCDAToFCAAQsQM6AggAUJQ2WMFXYPtdaAFwAHgAgAG6AYgBhQeSAQMzLjWYAQCgAQGqAQtnd3Mtd2l6LWltZ7ABCrgBA8ABAQ&sclient=img&ei=2IzUX4KaOfWD8QPJxJWICA&bih=650&biw=1324");

        registerForContextMenu(browser);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);

        final WebView.HitTestResult webViewHitTestResult = browser.getHitTestResult();

        if (webViewHitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                webViewHitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

            contextMenu.setHeaderTitle("Add Image Link Below");

            contextMenu.add(0, 1, 0, "Ok")
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {

                            String DownloadImageURL = webViewHitTestResult.getExtra();
                            editTextimage.setText(DownloadImageURL);

                          //  Toast.makeText(getActivity(), ""+DownloadImageURL, Toast.LENGTH_SHORT).show();
                            if(URLUtil.isValidUrl(DownloadImageURL)){

                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadImageURL));
                                request.allowScanningByMediaScanner();


                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                              /*  DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                downloadManager.enqueue(request);
*/
                                Toast.makeText(getActivity(),"Image Downloaded Successfully.",Toast.LENGTH_LONG).show();
                            }
                            else {
                             //   editTextimage.setText(DownloadImageURL);
                              //  Toast.makeText(getActivity(),"Sorry.. Something Went Wrong."+DownloadImageURL,Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
        }
    }


    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Adding Item","Please wait");
        final String name = editTextItemName.getText().toString().trim();
        final String price = editTextprice.getText().toString().trim();
        final String qtys = qty.getText().toString().trim();
        final String editTextimages = editTextimage.getText().toString().trim();




        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbyiocV9hLKIug2hkJKm_5FKnbXmp4z_suxQCrzlejqsjlxIni4/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
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
                parmas.put("action","addItem");
                parmas.put("name",name);
                parmas.put("qty",qtys);
                parmas.put("price",price);
                parmas.put("image",editTextimages);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        queue.add(stringRequest);


    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


    }
}
