package com.example.foldertree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;

public class MyHolder extends TreeNode.BaseNodeViewHolder<MyHolder.IconTreeItem> {

    ImageView ar;
    boolean file;

    public MyHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.select_fold, null, false);
        TextView tvValue = (TextView) view.findViewById(R.id.folder);
        tvValue.setText(value.text);
        ImageView imageView = (ImageView) view.findViewById(R.id.fileic);
        imageView.setImageResource(value.icon);
        ar = view.findViewById(R.id.icon);
        file = !value.folder;
        ar.setImageResource(R.drawable.closedar);
        if (!value.folder) {
            ar.setImageResource(R.drawable.ic_null);
        }
        node.setClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {

                if (file) {
                    return;
                }
                if (node.isExpanded()) {
                    ar.setImageResource(R.drawable.closedar);
                } else {
                    ar.setImageResource(R.drawable.expandedar);
                }


            }
        });
        return view;
    }

    public static class IconTreeItem {
        public int icon;
        public String text;
        boolean folder;
    }
}
