package no.realitylab.arface.internetService;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import no.realitylab.arface.internetService.Category;
import no.realitylab.arface.internetService.CategoryDetails;
import no.realitylab.arface.internetService.JsonPlaceHolderApi;
import no.realitylab.arface.internetService.TopLevel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import no.realitylab.arface.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

     ArrayList<Bitmap> arrayimage = new ArrayList<Bitmap>();
     ArrayList<Bitmap> arrayimagehead = new ArrayList<Bitmap>();
     ArrayList<String> nameCat = new ArrayList<String>();

    ImageView image,imageViewHead;
    Button start;
    ProgressBar progressBar;
    private int counter=0;
    Timer t=new Timer();
    TimerTask tt;
    TopLevel topLevel ;
    List<Category> categories;

    Bitmap bitmap;

    RelativeLayout progress;
    TextView textViewResult;
    TextView textCat;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     setContentView(R.layout.activity_main);

     //   image=findViewById(R.id.image);
      //  imageViewHead = findViewById(R.id.imageViewHead);
    //    textViewResult = findViewById(R.id.text_view_result);
    //    textCat = findViewById(R.id.nameCat);

       // bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.selfie1);
        bitmap = BitmapFactory.decodeResource(getResources(), Color.TRANSPARENT);


        progressBarkismi();
        internetService();

    }

    public void internetService(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IlExb3NkaFZuYnBCaWJDYjJzd1JCMElSZW5Jd2lUaHBXdXJQS3hLY3ZNZEE9IiwibmJmIjoxNjAyNzkwODY0LCJleHAiOjE2MDI4NzcyNjQsImlhdCI6MTYwMjc5MDg2NH0.2ZLtegSbKPNI4McAVSW9JSDbQKE0VoxB57sYHK5sPPc")
                        .build();
                System.out.println(newRequest + "OkHttpClient out data");
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://moodytest-env.eba-mmzgp9iv.eu-central-1.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<TopLevel> call = jsonPlaceHolderApi.getTopLevel();


        call.enqueue(new Callback<TopLevel>() {
            @Override
            public void onResponse(Call<TopLevel> call, Response<TopLevel> response) {

                topLevel = response.body();
                categories = topLevel.getCategories();

                String content = "" + "\n";

                //space for recyclerview
                nameCat.add("");
                arrayimage.add(bitmap);
                nameCat.add("");
                arrayimage.add(bitmap);
                //

                List<Category> categories = topLevel.getCategories();
                for (Category category:categories)
                {
                    byte[] decodedString = Base64.decode(category.getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    nameCat.add(category.getName());
                    arrayimage.add(decodedByte);

                    List<CategoryDetails> categoryDetailsList = category.getCategoryDetails();
                    for (CategoryDetails categoryDetails:categoryDetailsList)
                    {
                        byte[] decodedString2 = Base64.decode(category.getImage(), Base64.DEFAULT);
                        Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                        arrayimagehead.add(decodedByte2);

                        //content += "categoryDetailsId: " + categoryDetails.getId() + "\n";
                        //content += "categoryDetailsOrder: " + categoryDetails.getOrder() + "\n";
                        content += "categoryDetailsImage: " + categoryDetails.getImage() + "\n";
                    }

               }

                //space for recyclerview
                nameCat.add("");
                arrayimage.add(bitmap);

                nameCat.add("");
                arrayimage.add(bitmap);
                //

             //   initRecyclerView();
            }

            @Override
            public void onFailure(Call<TopLevel> call, Throwable t) {
                textViewResult.setText(t.getMessage());
                System.out.println(t.getMessage());
            }
        });

    }

    public void initRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

       // RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayimage,nameCat);
//        recyclerView.setAdapter(adapter);
        adapter.setLayoutManager(layoutManager);


        SnapHelper startSnapHelper = new StartSnapHelper();
//        startSnapHelper.attachToRecyclerView(recyclerView);

    }

    public ArrayList<Bitmap> sallayalım(){

        internetService();
        return arrayimagehead;
    }

    public ArrayList<Bitmap> sallayalım1(){
        internetService();
        return arrayimage;
    }

    public ArrayList<String> sallayalım2(){

        internetService();
        return nameCat;
    }



    @SuppressLint("ClickableViewAccessibility")
    public void progressBarkismi(){
        start=findViewById(R.id.button);
        progressBar=findViewById(R.id.progress_bar);

        progress=findViewById(R.id.progressLayout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager(). getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        progress.setHorizontalGravity(width/2);
        progress.setVerticalGravity(height/2);

        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, final MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        t=new Timer();
                        tt=new TimerTask() {
                            @Override
                            public void run() {
                                counter++;
                                progressBar.setProgress(counter);
                                if(counter ==100)
                                    t.cancel();
                            }
                        };
                        t.schedule(tt,0,100);
                        progressBar.setVisibility(View.VISIBLE);
                        return true;
                    case MotionEvent.ACTION_UP:

                        t.cancel();
                        progressBar.setProgress(counter=0);

                        return true;
                }
                return false;
            }
        });
    }
}