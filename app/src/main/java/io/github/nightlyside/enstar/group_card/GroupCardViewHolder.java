package io.github.nightlyside.enstar.group_card;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;

import io.github.nightlyside.enstar.R;

public class GroupCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView groupImage;
    public TextView groupTitle;
    public TextView groupMembers;

    public GroupCardViewHolder(View itemView) {
        super(itemView);
        groupImage = itemView.findViewById(R.id.group_image);
        groupTitle = itemView.findViewById(R.id.group_title);
        groupMembers = itemView.findViewById(R.id.group_members);
    }
}
