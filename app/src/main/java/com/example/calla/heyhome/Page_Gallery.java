package com.example.calla.heyhome;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



public class Page_Gallery extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    String[] brands = {"Brand", "Basset", "RH", "Crate & Barrel", "Ethan Allen", "IKEA", "Pottery Barn", "West elm"};
    String[] rooms = {"Room", "Living", "Bedroom", "Kitchen", "Dining", "Bathroom"};
    String[] styles = {"Style", "Contemporary", "Modern", "Transitional", "Traditional", "Farmhouse", "Rustic"};
    String[] selections = new String[3];

    GridView gridview;
    ImageView soloPhoto;
    Button btnBack;

    // initialize array of smallImages (100x75 thumbnails)
    Integer[] smallImages = { R.drawable.pic01_small,
            R.drawable.pic02_small, R.drawable.pic03_small,
            R.drawable.pic04_small, R.drawable.pic05_small,
            R.drawable.pic06_small, R.drawable.pic07_small,
            R.drawable.pic08_small, R.drawable.pic09_small,
            R.drawable.pic10_small, R.drawable.homedec_5,
            R.drawable.homedec_4, R.drawable.homedec_3,
            R.drawable.homedec_2, R.drawable.homedec_1 };

    //initialize array of high-resolution images (1024x768)
    /*Integer[] largeImages = { R.drawable.pic01_large,
            R.drawable.pic02_large, R.drawable.pic03_large,
            R.drawable.pic04_large, R.drawable.pic05_large,
            R.drawable.pic06_large, R.drawable.pic07_large,
            R.drawable.pic08_large, R.drawable.pic09_large,
            R.drawable.pic10_large, R.drawable.pic11_large,
            R.drawable.pic12_large, R.drawable.pic13_large,
            R.drawable.pic14_large, R.drawable.pic15_large };*/

    //in case you want to use-save state values
    Bundle myOriginalMemoryBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_page_gallery, container, false);

        // part1: three spinners
        Spinner spinner1 = (Spinner) rootView.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                brands);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        Spinner spinner2 = (Spinner) rootView.findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                rooms);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        Spinner spinner3 = (Spinner) rootView.findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_spinner_item,
                styles);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(this);


        // part2: GridView for images
        myOriginalMemoryBundle = savedInstanceState;
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this.getActivity(), smallImages));
        gridview.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s = "";
        switch (parent.getId()) {
            case R.id.spinner1:
                selections[0] = brands[position] == "brand" ? "" : brands[position];
                break;
            case R.id.spinner2:
                selections[1] = rooms[position] == "room" ? "" : rooms[position];
                break;
            case R.id.spinner3:
                selections[2] = styles[position] == "style" ? "" : styles[position];
                break;
        }
        /*Toast.makeText(getActivity().getApplicationContext(),
                selections[0] + " " + selections[1] + " " + selections[2] + " are selected!", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getActivity().getApplicationContext(), "Nothing selected!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        // TODO: 5/26/16
//        showBigScreen(position);
    }

    private void showBigScreen(int position) {
        // show the selected picture as a single frame
        getActivity().setContentView(R.layout.solo_picture);
//        soloPhoto = (ImageView) getView().findViewById(R.id.imgSoloPhoto);
//
//        soloPhoto.setImageResource( largeImages[position] );
//
//        btnBack = (Button) getView().findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // redraw the main screen showing the GridView
//                onCreate(myOriginalMemoryBundle);
//                //recreate();
//            }
//        });

    }
}
