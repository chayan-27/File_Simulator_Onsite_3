package com.example.foldertree;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import java.io.File;


public class MainActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    TreeNode root;
    AndroidTreeView tView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);
        root = TreeNode.root();
        tView = new AndroidTreeView(this, root);
        MyHolder.IconTreeItem nodeItem1 = new MyHolder.IconTreeItem();
        nodeItem1.text = "Internal Storage";
        nodeItem1.icon = R.drawable.ic_folder;
        nodeItem1.folder = true;
        final TreeNode internalStorage = new TreeNode(nodeItem1).setViewHolder(new MyHolder(this));
        final File rootD = new File("/storage/emulated/0");
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                startTree(rootD, internalStorage);
            }
        });
        thread.start();


       // startTree(rootD, internalStorage);
        tView.setDefaultAnimation(false);
        tView.setUse2dScroll(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        root.addChild(internalStorage);
        this.addContentView(tView.getView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void startTree(File f, TreeNode node) {
        File[] files = f.listFiles();
        for (File file : files) {
            MyHolder.IconTreeItem nodeItem = new MyHolder.IconTreeItem();
            nodeItem.text = file.getName();
            if (file.isDirectory()) {
                nodeItem.icon = R.drawable.ic_folder;
                if (file.listFiles() == null||file.listFiles().length==0) {
                    nodeItem.folder = false;
                } else {
                    nodeItem.folder = true;
                }
            } else {
                nodeItem.icon = R.drawable.ic_file;
                nodeItem.folder = false;
            }
            TreeNode fileNode = new TreeNode(nodeItem).setViewHolder(new MyHolder(this));
            node.addChild(fileNode);
            if (file.isDirectory()) {
                startTree(file, fileNode);
            }
        }
    }


}
