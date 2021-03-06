package com.vk.app_arduino;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class AdapterCardViewSettings extends RecyclerView.Adapter<AdapterCardViewSettings.SettingsViewHolder>{
    private Context mContext;
    private List<settings_cards> cards;
    Dialog dialog;


    AdapterCardViewSettings(Context mContext, List<settings_cards> cards){
        this.mContext = mContext;
        this.cards = cards;
    }


    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_settings_card, parent,false);
        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        holder.ImgSettings.setImageDrawable(cards.get(position).getImg());
        holder.TextSettings.setText(cards.get(position).getText());

        dialog = new Dialog(holder.cv.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        holder.itemView.setOnClickListener(view -> {
            switch(cards.get(position).getText()) {
                case "Напоминание":
                    dialog.setContentView(R.layout.dialog_notification_settings);
                    dialog.findViewById(R.id.OK_button).setOnClickListener(v -> dialog.dismiss());
                   // dialog.findViewById(R.id.Cancle_button).setOnClickListener(v -> dialog.dismiss());
                    dialog.show();
                    break;
                case "Виджеты":
                    dialog.setContentView(R.layout.dialog_widget);
                    dialog.findViewById(R.id.button3).setOnClickListener(v -> dialog.dismiss());
                    dialog.findViewById(R.id.button4).setOnClickListener(v -> dialog.dismiss());
                    dialog.show();
                    break;
                case "Звук":
                    dialog.setContentView(R.layout.dialog_sound);
                    dialog.findViewById(R.id.button3).setOnClickListener(v -> dialog.dismiss());
                    dialog.findViewById(R.id.button4).setOnClickListener(v -> dialog.dismiss());
                    dialog.show();
                    break;
                case "У меня проблема":
                    dialog.setContentView(R.layout.dialog_problems);
                    dialog.findViewById(R.id.button3).setOnClickListener(v -> dialog.dismiss());
                    dialog.findViewById(R.id.button4).setOnClickListener(v -> dialog.dismiss());
                    dialog.show();
                    break;
                case "Сбросить прогресс":
                    dialog.setContentView(R.layout.dialog_reset_profile);
                    dialog.findViewById(R.id.button3).setOnClickListener(v -> dialog.dismiss());
                    dialog.findViewById(R.id.button4).setOnClickListener(v -> dialog.dismiss());
                    dialog.show();
                    break;
                case "О Разработчиках":
                    dialog.setContentView(R.layout.dialog_developers);
                    dialog.findViewById(R.id.button3).setOnClickListener(v -> dialog.dismiss());
                    dialog.findViewById(R.id.button4).setOnClickListener(v -> dialog.dismiss());
                    dialog.show();
                    break;
            }
        });

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class SettingsViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/  {
        CardView cv;
        ImageView ImgSettings;
        TextView TextSettings;

        SettingsViewHolder(View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.cv);
            ImgSettings = itemView.findViewById(R.id.img_settings);
            TextSettings = itemView.findViewById(R.id.text_settings);
        }
    }
}
