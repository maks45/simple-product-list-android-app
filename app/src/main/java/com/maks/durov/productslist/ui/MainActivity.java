package com.maks.durov.productslist.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maks.durov.productslist.R;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListProductFragment doneListFragment;
    private ListProductFragment notDoneListFragment;
    private MenuItem multiSelectSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation_menu);
        FloatingActionButton floatingActionButton = findViewById(R.id.main_fab);
        doneListFragment = ListProductFragment.newInstance(true);
        notDoneListFragment = ListProductFragment.newInstance(false);
        setFragment(notDoneListFragment);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_show_done:
                            setFragment(doneListFragment);
                            break;
                        case R.id.menu_show_not_done:
                            setFragment(notDoneListFragment);
                            break;
                        default:
                            return false;
                    }
                    disableMultiSelecting();
                    return true;
                });
        floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreateProductActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_item_menu, menu);
        multiSelectSubmit = menu.getItem(0);
        multiSelectSubmit.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu) {
        if (menu.getItemId() == R.id.action_done) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fragment : fragments) {
                if (fragment.isVisible()) {
                    ((ListProductFragment) fragment).checkSelectedItems();
                    break;
                }
            }
            disableMultiSelecting();
        }
        return super.onOptionsItemSelected(menu);
    }

    public void enableMultiSelecting() {
        multiSelectSubmit.setVisible(true);
        if (doneListFragment.isVisible()) {
            multiSelectSubmit.setIcon(R.drawable.ic_delete_white_24dp);
        } else {
            multiSelectSubmit.setIcon(R.drawable.ic_check_white_24dp);
        }
    }

    public void disableMultiSelecting() {
        multiSelectSubmit.setVisible(false);
    }

    private void setFragment(ListProductFragment listProductFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, listProductFragment);
        fragmentTransaction.commit();
    }
}
