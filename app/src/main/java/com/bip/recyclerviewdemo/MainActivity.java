package com.bip.recyclerviewdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bip.recyclerviewdemo.model.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Cac buoc tao 1 Recyclerview
 * 1. Them Thu vien
 * 2. Tao giao dien Activity co RecyclerView
 * 3. Anh xa RecyclerView vao code Activity
 * 4. Tao ViewHolder va giao dien cho ViewHolder
 * 5. Tao Adapter
 * 6. Overrite 3 ham trong Adapter: OncreateViewHolder,
 * OnBindViewHolder, getitemCount
 * 7. Set LayoutManager cho Recyclerview de chi dinh
 * kieu hien thi cho recyclerview(list/grid/ngang/doc)
 * 8. Tao du lieu cho Adapter va khoi tao Adapter
 * 9. Set Adapter cho RecyclerView
 * 10. Chay chuong trinh
 */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter myAdapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        RequestHttpAsyntask requestHttpAsyntask = new RequestHttpAsyntask("https://api.github.com/search/users?q=tom");
        requestHttpAsyntask.execute();
    }


    class RequestHttpAsyntask extends AsyncTask<Void, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        private String url;
        public RequestHttpAsyntask(String url){
            this.url = url;
        }
        //Chay vao truoc khi goi tac vu thuoc uithread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "Đang tải dữ liệu", true);
        }
        //Chay ngam khi thuc hien tac vui nam o thread khac tach biet
        @Override
        protected String doInBackground(Void... voids) {
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(url);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                result = null;
            }
            return result;

        }
        //Khi thuc hien xong tac vu goi den ham nay de tra ket qua ve cho UI
        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            progressDialog.dismiss();
            //txtContent.setText(s);
            disPlayData(json);

        }

    }
    private void disPlayData(String json){
        List<Student> studentList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            //Lay ra mang Json
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i =0; i<jsonArray.length(); i++){
                //Lay ra Jsonobject tai vi tri i
                JSONObject item = jsonArray.getJSONObject(i);
                Student studenti = new Student();
                studenti.setId(item.getInt("id"));
                studenti.setAvatar(item.getString("avatar_url"));
                studenti.setName(item.getString("login"));
                studentList.add(studenti);
            }
            myAdapter = new StudentAdapter(studentList);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(myAdapter);
            //txtContent.setText(content);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
