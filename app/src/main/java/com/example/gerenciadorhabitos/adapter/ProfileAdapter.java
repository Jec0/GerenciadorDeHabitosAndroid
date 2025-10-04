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

    public ProfileAdapter(List<Profile> profiles, OnProfileListener listener) {
        this.profiles = profiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_profile, parent, false);
        return new ProfileViewHolder(view, listener);
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

    static class ProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvProfileName;
        private final ImageView ivProfileIcon;
        private final OnProfileListener listener;

        public ProfileViewHolder(@NonNull View itemView, OnProfileListener listener) {
            super(itemView);
            tvProfileName = itemView.findViewById(R.id.tvProfileName);
            ivProfileIcon = itemView.findViewById(R.id.ivProfileIcon);
            this.listener = listener;
            itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View v) {
            listener.onProfileClick(getAdapterPosition());
        }
    }

    public interface OnProfileListener {
        void onProfileClick(int position);
    }
}