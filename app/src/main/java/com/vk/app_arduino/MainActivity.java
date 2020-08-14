package com.vk.app_arduino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @SuppressLint({"ResourceType", "SourceLockedOrientationActivity"})

    private static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager fragmentManager;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*getSupportFragmentManager().beginTransaction().
                replace(R.id.MainConstraintLayout, CurrentTasksAtHome.newInstance()).commit();*/

        BubbleNavigationConstraintView bubbleNavigation = findViewById(R.id.bottom_navigation_view_linear);

        //Значок уведомления возле кнопки на меню
       /* bubbleNavigation.setBadgeValue(0, "40");
        bubbleNavigation.setBadgeValue(1, null); //invisible badge
        bubbleNavigation.setBadgeValue(2, "7");*/

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
                        fragment = new Settings();
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




/* ImageButton buttonBackToMainMenu = view.findViewById(R.id.buttonBackToMainMenu);
        buttonBackToMainMenu.setOnClickListener(view12 -> {
            FragmentTransaction ft = Objects.requireNonNull(getActivity())
                    .getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_left_to_right, R.anim.exit_left_to_right,
                    R.anim.enter_right_to_left, R.anim.exit_right_to_left);
            ft.replace(R.id.MainConstraintLayout, FragmentMain.newInstance()).commit();
        });

        ImageButton buttonSettings = view.findViewById(R.id.ButtonSettings);
        buttonSettings.setOnClickListener(view1 -> {
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).
                    getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                    R.anim.enter_left_to_right, R.anim.exit_left_to_right);
            ft.replace(R.id.MainConstraintLayout, FragmentSettingsMenu.newInstance()).commit();
        });
        */