package com.example.dynamicshortscuttestapp

import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addbtn=findViewById<Button>(R.id.AddBtn)
        addbtn.setOnClickListener {
            val shortcutManager = getSystemService(ShortcutManager::class.java)
            if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
                intent.action = Intent.ACTION_DEFAULT
                //intent.setClassName(packagename, activityname)
                //intent.putExtra("key", "こんにちわ")
                val pinShortcutInfo = ShortcutInfo.Builder(this, "myshortcut")
                    .setShortLabel("D-App")//アプリ名
                    .setLongLabel("Dynamic-ShortCut Sample Application")
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_launcher_background))
                    .setIntent(intent)
                    .build()
                val pinnedShortcutCallbackIntent =
                    shortcutManager.createShortcutResultIntent(pinShortcutInfo)
                val successCallback =
                    PendingIntent.getBroadcast(
                        this, 0, pinnedShortcutCallbackIntent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                val b = shortcutManager.requestPinShortcut(
                    pinShortcutInfo,
                    successCallback.intentSender
                )
                //Util.showToast(this, "set pinned shortcuts " + (b ? "success" : "failed") + "!");
            }
        }

        val getbtn = findViewById<Button>(R.id.GetBtn)
        getbtn.setOnClickListener {
            val sm = getSystemService(ShortcutManager::class.java)
                .getShortcuts(ShortcutManager.FLAG_MATCH_PINNED)

            for (sms in sm) {
                //Log.d(TAG,  "packagename: " + sms.) // ComponentInfo{com.example.dynamicshortscuttestapp/com.example.dynamicshortscuttestapp.MainActivity}
                Log.d(TAG, "packagename: " + sms.id) //packagename: my-shortcut
            }
        }

        val disablebtn=findViewById<Button>(R.id.DisableBtn)
        disablebtn.setOnClickListener {
            val id = listOf<String>("myshortcut")
            val del = getSystemService(ShortcutManager::class.java)
                .disableShortcuts(id)
        }

        val enablebtn=findViewById<Button>(R.id.EnableBtn)
        enablebtn.setOnClickListener {
            val id = listOf<String>("myshortcut")
            val del = getSystemService(ShortcutManager::class.java)
                .enableShortcuts(id)
        }

        val delebtn=findViewById<Button>(R.id.DeleteBtn)
        delebtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DELETE)
            intent.data = Uri.parse("package:com.example.dynamicshortscuttestapp")
            startActivity(intent)
        }
    }
}
