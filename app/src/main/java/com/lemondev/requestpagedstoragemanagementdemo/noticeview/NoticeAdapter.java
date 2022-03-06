package com.lemondev.requestpagedstoragemanagementdemo.noticeview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.lemondev.requestpagedstoragemanagementdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 2022/3/3
 * Created by vibrantBobo
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private List<String> mNoticeList = new ArrayList<>();

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);

        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.noticeView.setText(mNoticeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNoticeList.size();
    }

    public void setNoticeList(List<String> mNoticeList) {
        this.mNoticeList = mNoticeList;
        notifyDataSetChanged();
    }

    protected class NoticeViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView noticeView;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeView = itemView.findViewById(R.id.notice_textview);
        }
    }
}
