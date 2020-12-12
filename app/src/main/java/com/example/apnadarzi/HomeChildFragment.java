package com.example.apnadarzi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apnadarzi.Adapter.GridAdapter;
import com.google.firebase.database.DatabaseReference;

public class HomeChildFragment extends Fragment  implements  View.OnClickListener{

   // private static final String ARGUMENT_POSITION = "argument_position";
    //private static final String ARGUMENT_NAME = "argument_name";
    private DatabaseReference ProductsRef;

    private RecyclerView rv;
    GridView grid;
    GridAdapter adapter;
    String[] Version;
    int[] image;
    ImageView profile_image;

    public static HomeChildFragment newInstance(int position, String name) {

      //  Bundle args = new Bundle();
      //  args.putInt(ARGUMENT_POSITION, position);
     //   args.putString(ARGUMENT_NAME, name);
        HomeChildFragment fragment = new HomeChildFragment();
      //  fragment.setArguments(args);
        return fragment;
    }
/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLifecycle().addObserver(new TimberLogger(this));
    }



   */
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
       // rootview = inflater.inflate(R.layout.fragment_home_child, container, false);
        View rootView=inflater.inflate(R.layout.fragment_home_child,null);

        Version = new String[] { "REd|| white Banarsi ", "REd|| white Banarsi ", "REd|| white Banarsi ", "REd|| white Banarsi ", "REd|| white Banarsi ", " REd|| white Banarsi ", "REd|| white Banarsi ", "REd|| white Banarsi ", "REd|| white Banarsi ", "REd|| white Banarsi " };
        image = new int[] { R.drawable.ema,R.drawable.ema,R.drawable.ema,R.drawable.ema,R.drawable.ema,R.drawable.ema,
                R.drawable.ema,R.drawable.ema,R.drawable.ema,R.drawable.ema,};

        // Locate the ListView in listview_main.xml
        grid = (GridView) rootView.findViewById(R.id.gridviewhome);

        // Pass results to ListViewAdapter Class
        adapter = new GridAdapter(getContext(), Version,image);
        // Binds the Adapter to the ListView
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              Intent intent = new Intent(getActivity(), Product_Detail.class);
                intent.putExtra("image", image);

                startActivity(intent); //start activity
               getActivity().startActivityForResult(intent, 1);

            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profile_image =(ImageView) view.findViewById(R.id.product_image) ;

    }




    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), My_Order.class);
        startActivity(intent);
    }
}
