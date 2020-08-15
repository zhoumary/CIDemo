package com.example.cidemo.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cidemo.R;
import com.example.cidemo.databinding.ItemMatchBinding;
import com.example.cidemo.model.MatchItem;
import com.example.cidemo.view.MainActivity;

import java.util.List;

public class MatchItemAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MatchItem> matchList;
    private OnItemClickListener mOnItemClickListener = null;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemMatchBinding mItemMatchBinding;

        public ViewHolder(ItemMatchBinding itemMatchBinding) {
            super(itemMatchBinding.getRoot());
            this.mItemMatchBinding = itemMatchBinding;
        }
    }

    public MatchItemAdapter(Context mContext, List<MatchItem> matchList) {
        this.mContext = mContext;
        this.matchList = matchList;
    }

    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemMatchBinding itemMatchBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_match, viewGroup, false);
         View view = LayoutInflater.from(mContext).inflate(R.layout.item_match, viewGroup, false);
        return new ViewHolder(itemMatchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder mViewHolder = (ViewHolder) viewHolder;
        // dataBinding绑定
        MatchItem matchItem = matchList.get(position);
        mViewHolder.mItemMatchBinding.setMatchItem(matchItem);
        // 设置点击事件，将接口方法回调给MainActivity
        if (mOnItemClickListener != null) {
            mViewHolder.mItemMatchBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onShortClick(position);
                }
            });
            mViewHolder.mItemMatchBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
        // 直接在adapter里设置点击事件
        mViewHolder.mItemMatchBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsUrlPrefix = mContext.getResources().getString(R.string.news_url_prefix);
                String httpUrl = newsUrlPrefix + matchList.get(position).getItem_id();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("httpUrl", httpUrl);
                mContext.startActivity(intent);
            }
        });
        mViewHolder.mItemMatchBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    // 定义点击事件的接口
    public interface OnItemClickListener {
        void onShortClick(int position); // 单击
        void onLongClick(int position); // 长按
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
