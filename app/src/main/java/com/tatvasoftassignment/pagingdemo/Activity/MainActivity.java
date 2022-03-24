package com.tatvasoftassignment.pagingdemo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.tatvasoftassignment.pagingdemo.Adapter.MainAdapter;
import com.tatvasoftassignment.pagingdemo.Model.MainData;
import com.tatvasoftassignment.pagingdemo.Model.MainInterface;
import com.tatvasoftassignment.pagingdemo.R;
import com.tatvasoftassignment.pagingdemo.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

   ActivityMainBinding binding;
   ArrayList<MainData> dataArrayList = new ArrayList<>();
   MainAdapter adapter;
   int page = 1 , limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        adapter = new MainAdapter(this,dataArrayList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        getData(page,limit);
        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            page++;
            getData(page,limit);
        });

    }

    private void getData(int page, int limit) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MainInterface MainInterface = retrofit.create(MainInterface.class);
        Call<String> call = MainInterface.STRING_CALL(page,limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful() && response.body() != null){
                    try {
                        JSONArray jsonArray = new JSONArray(response.body());
                        parseRequest(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
    }

    private void parseRequest(JSONArray jsonArray) throws JSONException {
       for(int i=0;i<jsonArray.length();i++) {
           JSONObject object = jsonArray.getJSONObject(i);
           MainData data = new MainData();
           data.setImage(object.getString("download_url"));
           data.setName(object.getString("author"));
           dataArrayList.add(data);

           adapter = new MainAdapter(this,dataArrayList);
           binding.recyclerView.setAdapter(adapter);
       }

    }
}