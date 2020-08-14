package com.vk.app_arduino;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Settings extends Fragment {

    static Settings newInstance() {
        return new Settings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

       RecyclerView recycleViewSettings = view.findViewById(R.id.recycleViewSettings);
        recycleViewSettings.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recycleViewSettings.setLayoutManager(llm);

        List<settings_cards> cardsSettings = new ArrayList<>();
        cardsSettings.add(new settings_cards("Напоминание", getResources().getDrawable(R.drawable.ic_notifications_28)));
        cardsSettings.add(new settings_cards ("Звук", getResources().getDrawable(R.drawable.ic_notifications_28)));
        cardsSettings.add(new settings_cards("У меня проблема", getResources().getDrawable(R.drawable.ic_notifications_28)));
        cardsSettings.add(new settings_cards("Сбросить прогресс", getResources().getDrawable(R.drawable.ic_notifications_28)));
        cardsSettings.add(new settings_cards("Регистрация/Авторизация",getResources().getDrawable(R.drawable.ic_notifications_28)));
        cardsSettings.add(new settings_cards("О Разработчиках",getResources().getDrawable(R.drawable.ic_share_external_outline_28)));

        recycleViewSettings.setAdapter(new AdapterCardViewSettings(getContext(), cardsSettings));

        return view;
    }
}