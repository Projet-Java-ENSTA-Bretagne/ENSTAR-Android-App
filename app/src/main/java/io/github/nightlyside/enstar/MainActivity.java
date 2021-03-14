package io.github.nightlyside.enstar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import io.github.nightlyside.enstar.entities.Group;
import io.github.nightlyside.enstar.group_card.GroupCardDecoration;
import io.github.nightlyside.enstar.group_card.GroupCardRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // if the user is not logged in it's the first step
        if (!ApplicationState.getInstance(this).isUserLoggedIn) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        // then we set the view content
        setContentView(R.layout.activity_main);

        // set toolbar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // set groups
        Group test = new Group("0", "Un groupe", false, null, "");
        List<Group> groups = new ArrayList<Group>();
        for (int k = 0; k < 9; k++) {
            groups.add(test);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        GroupCardRecyclerAdapter adapter = new GroupCardRecyclerAdapter(this, groups);
        recyclerView.setAdapter(adapter);
        int largePadding = getResources().getDimensionPixelSize(R.dimen.group_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.group_grid_spacing_small);
        recyclerView.addItemDecoration(new GroupCardDecoration(largePadding, smallPadding));

    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}