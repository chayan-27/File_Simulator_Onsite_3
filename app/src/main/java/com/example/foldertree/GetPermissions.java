package com.example.foldertree;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static java.lang.Thread.sleep;

public class GetPermissions extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.require_permission);
        startApp();
    }

    @AfterPermissionGranted(123)
    private void startApp() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        if(EasyPermissions.hasPermissions(this, permission)) {
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        startActivity(new Intent(GetPermissions.this, MainActivity.class));
                        finish();
                    }

                }
            });
            thread.start();

        } else {
            EasyPermissions.requestPermissions(this, "Permission Required",
                    123, permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}