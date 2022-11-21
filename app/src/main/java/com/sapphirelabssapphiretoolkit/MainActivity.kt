package com.sapphirelabssapphiretoolkit

import android.content.ContentValues.TAG
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var NoteCal_Location = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GetAllInstalled();

        // Check if we found the test app
        PopulateAllSlots();
    }

    fun GetAllInstalled() {
        val pm = packageManager
        //get a list of installed apps.
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        for (packageInfo in packages) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName)
            // Log.d(TAG, "Source dir : " + packageInfo.sourceDir)
            // Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName))

            if (packageInfo.packageName.contains("notecal")) {
                NoteCal_Location = packageInfo.sourceDir;
            }
        }
    }

    fun PopulateAllSlots() {
        if (NoteCal_Location.isNotEmpty()) {
            Log.d(TAG, "Found the test app. Populating list.");
            var SlotOne = findViewById<CheckBox>(R.id.CB_SlotOne);
            SlotOne.text = "NoteCal"
        } else {
            Log.d(TAG, "Did NOT the test app.");
            var SlotOne = findViewById<CheckBox>(R.id.CB_SlotOne);
            SlotOne.text = "NoteCal not found!"
        }
    }
    fun GetOpenSlot() {

    }

    // End of class
}