package com.square.apple.pdf_app.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.square.apple.pdf_app.R;


public class SimpleDrawerItem extends DrawerItem<SimpleDrawerItem.ViewHolder> {

    private int selectedItemIconTint;
    private int selectedItemTextTint;

    private int normalItemIconTint;
    private int normalItemTextTint;

    private Drawable icon;
    private String title;

    public SimpleDrawerItem(String title) {
        this.icon = icon;
        this.title = title;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_option_drawer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {
        holder.title.setText(title);
//        holder.icon.setImageDrawable(icon);

        holder.title.setTextColor(isChecked ? selectedItemTextTint : normalItemTextTint);
//        holder.icon.setColorFilter(isChecked ? selectedItemIconTint : normalItemIconTint);
    }

//    public SimpleDrawerItem withSelectedIconTint(int selectedItemIconTint) {
//        this.selectedItemIconTint = selectedItemIconTint;
//        return this;
//    }

    public SimpleDrawerItem withSelectedTextTint(int selectedItemTextTint) {
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }

//    public SimpleDrawerItem withIconTint(int normalItemIconTint) {
//        this.normalItemIconTint = normalItemIconTint;
//        return this;
//    }

    public SimpleDrawerItem withTextTint(int normalItemTextTint) {
        this.normalItemTextTint = normalItemTextTint;
        return this;
    }

    static class ViewHolder extends DrawerAdapter.ViewHolder {

        private ImageView icon;
        private TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
//            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
