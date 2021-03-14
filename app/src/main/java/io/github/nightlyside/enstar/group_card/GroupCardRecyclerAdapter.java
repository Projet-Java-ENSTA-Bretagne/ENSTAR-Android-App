package io.github.nightlyside.enstar.group_card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.nightlyside.enstar.R;
import io.github.nightlyside.enstar.entities.Group;
import io.github.nightlyside.enstar.network.ImageRequester;

public class GroupCardRecyclerAdapter extends RecyclerView.Adapter<GroupCardViewHolder> {

    private List<Group> groupList;
    private ImageRequester imgRequester;

    public GroupCardRecyclerAdapter(Context context, List<Group> groupList) {
        this.groupList = groupList;
        this.imgRequester = ImageRequester.getInstance(context);
    }

    @Override
    public GroupCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_fragment, parent, false);
        return new GroupCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(GroupCardViewHolder holder, int position) {
        if (groupList != null && position < groupList.size()) {
            Group group = groupList.get(position);
            holder.groupTitle.setText(group.getName());
            holder.groupMembers.setText(group.getMembers().split(";").length + " members");
            imgRequester.setImageFromUrl(holder.groupImage, group.getImage());
        }

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
