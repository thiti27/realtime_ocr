
package com.trytocode.realtime1

import android.media.SoundPool
import android.os.Bundle
import android.view.SurfaceView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var cameraview: SurfaceView
    private lateinit  var text: TextView
    private lateinit  var text1: TextView
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    private var RequestCameraPermissionID = 111
    private var count = 0
    private var count1 = 0
    private var soundPool: SoundPool? = null
    private val soundId = 1
    private val  requestCodeCameraPermission = 1001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController: NavController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }


}