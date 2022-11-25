package com.sapphirelabssapphiretoolkit

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.security.AccessController.getContext
import java.util.*


class MainActivity : AppCompatActivity() {

    // Set up all boxes because Kotlin is stupid when it comes to finding all views by type.
    var Slot0_Name = R.id.CB_Slot_0
    var Slot1_Name = R.id.CB_Slot_1
    var Slot2_Name = R.id.CB_Slot_2
    var Slot3_Name = R.id.CB_Slot_3
    var Slot4_Name = R.id.CB_Slot_4
    var Slot5_Name = R.id.CB_Slot_5

    var Slot0_Path = R.id.CB_Slot_0_Path
    var Slot1_Path = R.id.CB_Slot_1_Path
    var Slot2_Path = R.id.CB_Slot_2_Path
    var Slot3_Path = R.id.CB_Slot_3_Path
    var Slot4_Path = R.id.CB_Slot_4_Path
    var Slot5_Path = R.id.CB_Slot_5_Path

    var NextOpenSlot = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GetAllInstalled();
    }

    fun GetAllInstalled() {
        val pm = packageManager
        //get a list of installed apps.
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
        var CurrentSlot:Int = 0
        var UpdateSlot:Boolean = false

        for (packageInfo in packages) {
            Log.d(TAG, "Installed package :" + packageInfo.packageName)
            // Log.d(TAG, "Source dir : " + packageInfo.sourceDir)
            // Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName))

            if (packageInfo.packageName.contains("com.delphicoder.flud")) {
                val Name = packageInfo.packageName
                val Path = packageInfo.sourceDir
                UpdateSlot = true
            }

            if (packageInfo.packageName.contains("com.android.egg")) {
                val Name = packageInfo.packageName
                val Path = packageInfo.sourceDir
                UpdateSlot = true
            }

            if (UpdateSlot) {
                UpdateSlot = false
                var SlotToUpdate = findViewById<CheckBox>(GetOpenSlot());
                var SlotToUpdateText = findViewById<TextView>(GetOpenSlot());
                SlotToUpdate.text = packageInfo.packageName
                SlotToUpdateText.text = packageInfo.sourceDir
                CurrentSlot++
            }
        }
    }

    fun GetOpenSlot(): Int {
        when (NextOpenSlot) {
            0 -> { NextOpenSlot++; return Slot0_Name}
            1 -> { NextOpenSlot++; return Slot0_Path}
            2 -> { NextOpenSlot++; return Slot1_Name}
            3 -> { NextOpenSlot++; return Slot1_Path}
            4 -> { NextOpenSlot++; return Slot2_Name}
            5 -> { NextOpenSlot++; return Slot2_Path}
            6 -> { NextOpenSlot++; return Slot3_Name}
            7 -> { NextOpenSlot++; return Slot3_Path}
            8 -> { NextOpenSlot++; return Slot4_Name}
            9 -> { NextOpenSlot++; return Slot4_Path}
            10 -> { NextOpenSlot++; return Slot5_Name}
            11 -> { NextOpenSlot++; return Slot5_Path}
            else -> {
                Log.d(TAG, "Couldn't find a free slot, defaulting to Slot0");
                return Slot0_Name
            }
        }
    }

    // The uninstall functor. If testing is true, does not actually do the uninstall.
    fun DoUninstall(Testing:Boolean, Package:String) {
        if (Testing) {
            Log.d(TAG, "Uninstalling $Package for user 0.");
        } else {
            if (VERSION.SDK_INT >= VERSION_CODES.P) {
                Log.d(TAG, "We're running on Android P or above, switch to alternative uninstall.")
                val intent = Intent(Intent.ACTION_DELETE)
                intent.data = Uri.parse("package:com.example.mypackage")
                startActivity(intent)
            } else {
                val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE)
                intent.data = Uri.parse("package:com.example.mypackage")
                startActivity(intent)
            }

        }
    }

    // End of class
}
