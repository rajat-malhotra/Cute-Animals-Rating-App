package com.example.fotag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    Vector<Bitmap> map_array = new Vector<Bitmap>(10);

    boolean counter = false;

    Vector<ImageView> array_images =  new Vector<>(10);

    private class image_retrival extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public image_retrival(ImageView image) {
            Toast.makeText(getApplicationContext(), "Image Loading ... ", Toast.LENGTH_SHORT).show();
            this.imageView = image;
            array_images.add(image);
        }

        protected void onPostExecute(Bitmap result) {
            adp_grid new_Adapter = new adp_grid(getApplicationContext());
            adp_grid adaptor = new_Adapter;
            GridView new_grid = findViewById(R.id.gridindiv_grid_animal);
            GridView grid = new_grid;
            grid.setAdapter(adaptor);
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap image_stream = null;
            try {
                String url_1 = "";
                url_1 = urls[0];
                InputStream new_in_stream = new URL(url_1).openStream();
                InputStream in_stream = new_in_stream;
                image_stream = BitmapFactory.decodeStream(in_stream);

            } catch (Exception e) {
                 Log.e("Invalid url", e.getMessage());
                 e.printStackTrace();
            }
            map_array.add(image_stream);
            return image_stream;
        }
    }

    Vector<RatingBar> rating_array = new Vector<RatingBar>(10);

    public void image_load(View v) {
        counter = false;

        rating_array.clear();
        map_array.clear();
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/bunny.jpg");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/chinchilla.jpg");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/doggo.jpg");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/fox.jpg");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/hamster.jpg");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/husky.jpg");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/kitten.png");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/loris.jpg");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/puppy.jpg");
        new image_retrival((ImageView) findViewById(R.id.indiv_grid_animal)).execute("https://www.student.cs.uwaterloo.ca/~cs349/w20/assignments/images/sleepy.png");

        for(int i = 0; i < 10; ++i) {
            rating_array.add(new RatingBar(getApplicationContext()));
        }
        System.out.println(rating_array.size());
    }

    public void clear_image(View v){
        for(int i = 0; i < 10; ++i){

        }
        counter = true;
    }




    public class adp_grid extends BaseAdapter {

        Context content;
        LayoutInflater inflater_1;

        public adp_grid(Context content){
            this.content = content;
            //getting the inflater
            this.inflater_1 = LayoutInflater.from(content);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return rating_array.get(position);
        }

        @Override
        public int getCount() {
            return map_array.size();
        }

        @Override
        public View getView(int pos, View view_mine, ViewGroup parent) {
            final View new_view = this.inflater_1.inflate(R.layout.forming_grid, parent, false);
            view_mine = new_view;
            RatingBar new_rating_of_imageB = (RatingBar) view_mine.findViewById(R.id.rating_of_image);
            RatingBar rating_of_imageB = new_rating_of_imageB;



            final int position_1 = pos;
            rating_of_imageB.setOnRatingBarChangeListener(new OnRatingBarChangeListener() { // rating listener
                @Override
                public void onRatingChanged(RatingBar ratebar, float rating_over, boolean user) {
                   RatingBar rating_curr = rating_array.get(position_1);
                   rating_curr.setRating(rating_over);
                   rating_array.set(position_1,rating_curr);
                }
            });


            //get the image
            ImageView new_indiv_grid_animalImg = view_mine.findViewById(R.id.indiv_grid_animal);
            ImageView indiv_grid_animalImg = new_indiv_grid_animalImg;
            rating_of_imageB.setRating(rating_array.get(pos).getRating());

            //main image rating
            RatingBar new_mainRatingB = (RatingBar) findViewById(R.id.mainRating);
            RatingBar mainRatingB = new_mainRatingB;
            if(mainRatingB.getRating() <= rating_array.get(pos).getRating()) {
                if (counter == true) {
                    indiv_grid_animalImg.setVisibility(View.INVISIBLE);
                    rating_of_imageB.setVisibility(View.INVISIBLE);
                }
                if(counter == false){
                    rating_of_imageB.setVisibility(View.VISIBLE);
                    rating_of_imageB.setRating(rating_array.get(pos).getRating());
                    indiv_grid_animalImg.setVisibility(View.VISIBLE);
                    indiv_grid_animalImg.setImageBitmap(map_array.get(pos));
                }
            }
            return view_mine;
        }
    }
}
