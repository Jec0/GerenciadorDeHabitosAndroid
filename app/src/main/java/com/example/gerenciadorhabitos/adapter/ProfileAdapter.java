package com.example.gerenciadorhabitos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gerenciadorhabitos.R;
import com.example.gerenciadorhabitos.model.Profile;
import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private final List<Profile> profiles;
    private final OnProfileListener listener;

    public interface OnProfileListener {
        void onProfileClick(Profile profile);
        void onAddProfileClick();
        void onProfileLongClick(Profile profile, int adapterPosition);
    }

    public ProfileAdapter(List<Profile> profiles, OnProfileListener listener) {
        this.profiles = profiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_profile, parent, false);
        return new ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        if (position == 0) {
            holder.bindAddButton();
        } else {
            Profile profile = profiles.get(position - 1);
            holder.bind(profile);
        }
    }

    @Override
    public int getItemCount() {
        return profiles.size() + 1;
    }

    class ProfileViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvProfileName;
        private final ImageView ivProfileIcon;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProfileName = itemView.findViewById(R.id.tvProfileName);
            ivProfileIcon = itemView.findViewById(R.id.ivProfileIcon);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    if (position == 0) {
                        listener.onAddProfileClick();
                    } else {
                        listener.onProfileClick(profiles.get(position - 1));
                    }
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position > 0 && position != RecyclerView.NO_POSITION) {
                    listener.onProfileLongClick(profiles.get(position - 1), position);
                    return true;
                }
                return false;
            });
        }

        void bind(Profile profile) {
            ivProfileIcon.setVisibility(View.GONE);
            tvProfileName.setVisibility(View.VISIBLE);
            tvProfileName.setText(profile.name);
        }

        void bindAddButton() {
            tvProfileName.setVisibility(View.GONE);
            ivProfileIcon.setVisibility(View.VISIBLE);
            ivProfileIcon.setImageResource(android.R.drawable.ic_input_add);
        }
    }
}