package protzek.sebastian.mastermindlogicgame.MainMenu.HowToPlay;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import protzek.sebastian.mastermindlogicgame.R;

public class HowToPlayActivity extends AppCompatActivity {
    ViewPager2 pager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        createList();
        pager = findViewById(R.id.pager);
        HowToPlayPagerAdapter adapter = new HowToPlayPagerAdapter(getSupportFragmentManager(), getLifecycle(), createList());
        pager.setAdapter(adapter);
        tabLayout = findViewById(R.id.tab_layout);
        setTabLayout();
    }

    private ArrayList<Fragment> createList() {
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new HowToPlayFragment(R.layout.how_to_play_page_one));
        list.add(new HowToPlayFragment(R.layout.how_to_play_page_two));
        list.add(new HowToPlayFragment(R.layout.how_to_play_page_three));
        return list;
    }

    private void setTabLayout() {
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(R.string.page_one_tab);
                        break;
                    case 1:
                        tab.setText(R.string.page_two_tab);
                        break;
                    case 2:
                        tab.setText(R.string.page_three_tab);
                        break;
                }
            }
        }).attach();
    }

    public void backToMainMenu(View view) {
        finish();
    }
}
