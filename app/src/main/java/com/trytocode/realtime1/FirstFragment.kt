package com.trytocode.realtime1

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var text: TextView
    private lateinit var cameraview: SurfaceView
    private lateinit  var text1: TextView
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    private var RequestCameraPermissionID = 111
    private var count = 0
    private var count1 = 0
    private var soundPool: SoundPool? = null
    private val soundId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)

        cameraview = view.findViewById(R.id.surface)
        text = view.findViewById(R.id.text)
        text1 = view.findViewById(R.id.text1)
        val textRecognizer = TextRecognizer.Builder(applicationContext).build()
        if (textRecognizer.isOperational) {
            cameraSource = CameraSource.Builder(applicationContext, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
//                .setRequestedPreviewSize(720, 480)
//                .setRequestedFps(2.0f)
                .setRequestedPreviewSize(1024, 768)
                .setRequestedFps(30.0f)
                .setAutoFocusEnabled(true)
                .build()
            this.cameraview!!.holder!!.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(
                    holder: SurfaceHolder, format: Int,
                    width: Int, height: Int
                ) {

                }

                override fun surfaceCreated(holder: SurfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(
                                applicationContext,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                this@FirstFragment, arrayOf(Manifest.permission.CAMERA),
                                RequestCameraPermissionID
                            )
                            return
                        }

                        cameraSource.start(cameraview.holder)

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    cameraSource.stop()
                }
            })

            textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {}
                override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                    val items = detections.detectedItems
                    if (items.size() >= 6 ) {
                        count++
                        if(count %2 ==1) {
                            count1++
                            text.post {
                                val a = SweetAlertDialog(
                                    this@MainActivity,
                                    SweetAlertDialog.SUCCESS_TYPE
                                )
                                a.setTitleText("Success!")
                                a.show();
                                soundPool?.play(soundId, 1F, 1F, 0, 0, 1F)
                                Handler().postDelayed({
                                    a.cancel()
                                }, 1500)

                                val stringBuilder = StringBuilder()
                                for (i in 0 until items.size()) {
                                    val item = items.valueAt(i)
                                    stringBuilder.append(item.value)
                                    stringBuilder.append(" ")
                                }
                                text.text = stringBuilder.toString()
                                text1.text = count1.toString()

                            }
                        }


                    }
                }
            })

        }




        return view;
//        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}