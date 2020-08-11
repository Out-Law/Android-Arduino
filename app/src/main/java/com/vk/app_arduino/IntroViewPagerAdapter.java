package com.vk.app_arduino;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends RecyclerView.Adapter<IntroViewPagerAdapter.IntroViewHolderAdapter> {

    private  List<ScreenItem> screenItems;

    public IntroViewPagerAdapter(List<ScreenItem> screenItems) {
        this.screenItems = screenItems;
    }

    @NonNull
    @Override
    public IntroViewHolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IntroViewHolderAdapter(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.layout_screen, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolderAdapter holder, int position) {
        holder.setIntroViewHolderAdapter(screenItems.get(position));
    }

    @Override
    public int getItemCount() {
        return screenItems.size();
    }

    class IntroViewHolderAdapter extends RecyclerView.ViewHolder {

        private TextView textTitle, textDescription;
        private ImageView imageScreen;

        IntroViewHolderAdapter(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.intro_title);
            textDescription = itemView.findViewById(R.id.intro_description);
            imageScreen = itemView.findViewById(R.id.intro_img);
        }

        void setIntroViewHolderAdapter(ScreenItem screenItem) {
            textTitle.setText(screenItem.getTitle());
            textDescription.setText(screenItem.getDescription());
            imageScreen.setImageResource(screenItem.getScreenImg());
        }
    }
}
