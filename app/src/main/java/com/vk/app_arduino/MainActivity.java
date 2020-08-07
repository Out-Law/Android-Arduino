package com.vk.app_arduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager fragmentManager;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BubbleNavigationConstraintView bubbleNavigation = findViewById(R.id.bottom_navigation_view_linear);

        //Значок уведомления возле кнопки на меню
        bubbleNavigation.setBadgeValue(0, "40");
        bubbleNavigation.setBadgeValue(1, null); //invisible badge
        bubbleNavigation.setBadgeValue(2, "7");

        //НУжно для того чтобы открыть сразу первый фрагмент
        if(savedInstanceState == null){
            fragmentManager = getSupportFragmentManager();
            CurrentTasksAtHome currentTasksAtHome = new CurrentTasksAtHome();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, currentTasksAtHome)
                    .commit();
        }

        bubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position){
                    case 0:
                        fragment = new CurrentTasksAtHome();
                        break;
                    case 1:
                        fragment = new ControlOfTheHouse();
                        break;
                    case 2:
                        fragment = new HomeStatistics();
                        break;
                }
                if (fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }
                else{
                    Log.e(TAG, "Error in creating fragment");
                }
            }
        });
    }
}