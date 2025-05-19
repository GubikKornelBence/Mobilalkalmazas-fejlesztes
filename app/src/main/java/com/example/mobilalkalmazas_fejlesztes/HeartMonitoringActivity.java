package com.example.mobilalkalmazas_fejlesztes;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.search.SearchView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




import java.util.ArrayList;

public class HeartMonitoringActivity extends AppCompatActivity {
    private FirebaseUser user;
    private RecyclerView mRecycleView;
    private ArrayList<HeartRateItem> mItemlist;
    private HeartRateItemAdapter mAdapter;
    private int gridNumber= 1;
    private static final String LOG_TAG = HeartMonitoringActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_heart_monitoring);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(LOG_TAG,"user!");
        }else{
            finish();
        }

        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView. setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemlist = new ArrayList<>();
        mAdapter = new HeartRateItemAdapter(this,mItemlist);
        mRecycleView.setAdapter(mAdapter);
        initializeData();
    }
    private void initializeData(){
        String[] date = getResources().getStringArray(R.array.dates);
        int[] pulse = getResources().getIntArray(R.array.pulses);
        String[] bloodPressure= getResources().getStringArray(R.array.bloodpressure);
        int[] oxygen = getResources().getIntArray(R.array.oxygen);

        mItemlist.clear();

        for(int i = 0; i < date.length; i++){
            mItemlist.add(new HeartRateItem(date[i],pulse[i],bloodPressure[i],oxygen[i]));
        }
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.heart_rate_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        return true;
    }


}