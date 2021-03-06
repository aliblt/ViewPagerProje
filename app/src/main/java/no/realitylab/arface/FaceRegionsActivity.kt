package no.realitylab.arface

import android.Manifest
import android.Manifest.permission
import android.app.ActivityManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.CamcorderProfile
import android.media.MediaPlayer.OnPreparedListener
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.google.ar.core.ArCoreApk
import com.google.ar.core.AugmentedFace
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.Renderable
import kotlinx.android.synthetic.main.activity_regions.*
import no.realitylab.arface.internetService.Category
import no.realitylab.arface.internetService.JsonPlaceHolderApi
import no.realitylab.arface.internetService.MainActivity
import no.realitylab.arface.internetService.TopLevel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class FaceRegionsActivity : AppCompatActivity(), View.OnTouchListener {
    companion object {
        const val MIN_OPENGL_VERSION = 3.0
    }

    lateinit var arFragment: FaceArFragment
    var faceNodeMap = HashMap<AugmentedFace, FilterFace>()
    var refresh: Boolean = false
    private lateinit var videoRecorderJava: VideoRecorderJava
    private var counter = 0
    var t = Timer()
    var tt: TimerTask? = null
    var gir:Boolean = false
    var arrayimage = ArrayList<Bitmap>()
    var sliderList = ArrayList<Slider>()
    lateinit var categories: List<Category>
    var nameCat = ArrayList<String>()
    lateinit var call: Call<TopLevel>
    lateinit var topLevel: TopLevel
    lateinit var mainActivity: MainActivity
    lateinit var jsonPlaceHolderApi: JsonPlaceHolderApi
    var models = ArrayList<Model>()
    lateinit var modelAdapter:ModelAdapter
    lateinit var mediaRecorder:MediaRecorder
    var index = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!checkIsSupportedDeviceOrFinish()) {
            return
        }

        setContentView(R.layout.activity_regions)


        modelAdapter = ModelAdapter(models,this)

        SerciveInternet()

        TimeUnit.SECONDS.sleep(10L)

        arFragment = face_fragment as FaceArFragment
        mainActivity = MainActivity()
        val sceneView = arFragment.arSceneView
        sceneView.cameraStreamRenderPriority = Renderable.RENDER_PRIORITY_FIRST
        val scene = sceneView.scene



//        Log.e("Slider",viewPageImageSlider.currentItem.toString())
//
//
//
//            viewPageImageSlider.adapter = modelAdapter
//            viewPageImageSlider.offscreenPageLimit = 5
//
//            viewPageImageSlider.setPadding(0, 0, 0, 0)
//
//
//        viewPageImageSlider.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//
//            }
//
//            override fun onPageSelected(position: Int) {
//
//                if (index!=position)
//                {
//
//                    gir = true
//
//                }
//                index = position
//
//                scene.addOnUpdateListener {
//                    sceneView.session
//                        ?.getAllTrackables(AugmentedFace::class.java)?.let {
//                            for (f in it) {
//
//
//                                if (!faceNodeMap.containsKey(f) || gir) {
//                                    var a = faceNodeMap.get(f)
//                                    if (a != null) {
//                                        a.animals = categories[index].arrayImages
//                                    }else{
//                                        gir = false
//                                        val faceNode = FilterFace(f, applicationContext)
//                                        faceNode.animals = categories[index].arrayImages
//                                        faceNode.setParent(scene)
//                                        faceNodeMap.put(f, faceNode)
//                                    }
//
//                                }
//                            }
//
//                            // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
//                            val iter = faceNodeMap.entries.iterator()
//                            while (iter.hasNext()) {
//                                val entry = iter.next()
//                                val face = entry.key
//                                if (face.trackingState == TrackingState.STOPPED) {
//                                    val faceNode = entry.value
//                                    faceNode.setParent(null)
//                                    iter.remove()
//                                }
//                            }
//                        }
//                }
//
//                Log.e("position",position.toString())
//            }
//        })
//
//        viewPageImageSlider.clipToPadding = false
//        viewPageImageSlider.clipChildren = false
//        viewPageImageSlider.offscreenPageLimit = 5
//        viewPageImageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//
//        var compositePageTransformer = CompositePageTransformer()
//        compositePageTransformer.addTransformer(MarginPageTransformer(40))
//        compositePageTransformer.addTransformer(ViewPager2.PageTransformer { page, position ->
//            var r = 1 - Math.abs(position)
//            page.scaleY = 0.85f + r + 0.15f
//        })
//
//        viewPageImageSlider.setPageTransformer(compositePageTransformer)
//
//        viewPageImageSlider.registerOnPageChangeCallback(object : OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                if (index!=position)
//                {
//
//                    gir = true
//
//                }
//                index = position
//
//                scene.addOnUpdateListener {
//                    sceneView.session
//                        ?.getAllTrackables(AugmentedFace::class.java)?.let {
//                            for (f in it) {
//
//
//                                if (!faceNodeMap.containsKey(f) || gir) {
//                                    var a = faceNodeMap.get(f)
//                                    if (a != null) {
//                                        a.animals = categories[index].arrayImages
//                                    }else{
//                                        gir = false
//                                        val faceNode = FilterFace(f, applicationContext)
//                                        faceNode.animals = categories[index].arrayImages
//                                        faceNode.setParent(scene)
//                                        faceNodeMap.put(f, faceNode)
//                                    }
//
//                                }
//                            }
//
//                            // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
//                            val iter = faceNodeMap.entries.iterator()
//                            while (iter.hasNext()) {
//                                val entry = iter.next()
//                                val face = entry.key
//                                if (face.trackingState == TrackingState.STOPPED) {
//                                    val faceNode = entry.value
//                                    faceNode.setParent(null)
//                                    iter.remove()
//                                }
//                            }
//                        }
//                }
//
//                Log.e("position",position.toString())
//            }
//        })


        videoRecorderJava = VideoRecorderJava()
        mediaRecorder = MediaRecorder()
        videoRecorderJava.setVideoQuality(CamcorderProfile.QUALITY_HIGH,resources.configuration.orientation)
        videoRecorderJava.setSceneView(arFragment.arSceneView)

        button.setOnTouchListener(this)

        buttonCross.setOnClickListener {
            videoView.stopPlayback()
            videoView.visibility = View.INVISIBLE
            progressLayout.visibility = View.VISIBLE
       //     viewPageImageSlider.visibility = View.VISIBLE
            buttonCross.visibility = View.INVISIBLE
        }
    }


    fun SerciveInternet(){
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            // Request customization: add request headers
            val requestBuilder = original.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6Ikc3SEFpVGRrb3VzWnF3cDlOYlRkaitETllXWE5aSVFhaVJ1QzI1aVFoaDA9IiwibmJmIjoxNjAzMTkyNzY4LCJleHAiOjE2MDMyNzkxNjgsImlhdCI6MTYwMzE5Mjc2OH0.Wj6BY4Y-mBDRJ9-hZKBEv91YYD_baGNjCc9nqg0CVqI")

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val okHttpClient = httpClient.build()
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://moodytest-env.eba-mmzgp9iv.eu-central-1.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)


        call = jsonPlaceHolderApi.topLevel

        call.enqueue(object :Callback<TopLevel> {
            override fun onResponse(call: Call<TopLevel>, response: Response<TopLevel>) {
                topLevel = response.body()!!
                categories = topLevel.categories

                    for (category in categories) {
                        val decodedString = Base64.decode(category.image, Base64.DEFAULT)
                        val decodeByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                        nameCat.add(category.name)
                        arrayimage.add(decodeByte)
                        sliderList.add(Slider(decodeByte))
                        models.add(Model(decodeByte))
                        modelAdapter.notifyDataSetChanged()


                        category.imageBitmap = decodeByte

                        category.arrayImages = ArrayList<Bitmap>()

                        for (categoryDetails in category.categoryDetails) {
                            val decodedString2 = Base64.decode(categoryDetails.image, Base64.DEFAULT)
                            val decodeByte2 = BitmapFactory.decodeByteArray(decodedString2,0,decodedString2.size)
                          //  arrayimagehead.add(decodeByte2)
                            category.arrayImages.add(decodeByte2)

                        }
                    }
            }

            override fun onFailure(call: Call<TopLevel>, t: Throwable) {

                showLongToast("Failed")

            }
        })

    }

    fun toggleRecording(){
        var recording = videoRecorderJava.onToggleRecord()
        if (recording){
            showLongToast("Recording")
        } else {
            showLongToast("Stopped")
            var path = videoRecorderJava.videoPath.absolutePath
            showLongToast("video saved $path")

            // Send  notification of updated content.
            val values = ContentValues()
            values.put(MediaStore.Video.Media.TITLE, "Sceneform Video")
            values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            values.put(MediaStore.Video.Media.DATA, path)
            contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
        }
    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(this, permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),989)
        if (ActivityCompat.checkSelfPermission(this, permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO),99)
    }

    fun showLongToast(msg: String?) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    private fun startQuiz() {
        for (face in faceNodeMap.values) {
            face.animate()
        }
    }

    private fun refresh() {
        for (face in faceNodeMap.values) {
            face.refresh()
        }
    }

    private fun checkIsSupportedDeviceOrFinish() : Boolean {
        if (ArCoreApk.getInstance().checkAvailability(this) == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE) {
            Toast.makeText(this, "Augmented Faces requires ARCore", Toast.LENGTH_LONG).show()
            finish()
            return false
        }
        val openGlVersionString =  (getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager)
            ?.deviceConfigurationInfo
            ?.glEsVersion

        openGlVersionString?.let { s ->
            if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
                Toast.makeText(this, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show()
                finish()
                return false
            }
        }
        return true
    }

    fun progressBar(){

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        progressLayout.setHorizontalGravity(width / 2)
        progressLayout.setVerticalGravity(height / 2)

    }


    fun save(){
        var path = videoRecorderJava.videoPath.absolutePath
        val values = ContentValues()
        values.put(MediaStore.Video.Media.TITLE, "Sceneform Video")
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        values.put(MediaStore.Video.Media.DATA, path)
        contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
    }



    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        progressBar()

        when(event?.action){
            MotionEvent.ACTION_DOWN ->{

                    t = Timer()
                    tt = object : TimerTask() {
                        override fun run() {
                            counter++
                            progress_bar.progress = counter
                            if(!videoRecorderJava.isRecording) {
                                videoRecorderJava.startRecordingVideo()
                            //    animation()
                                startQuiz()
                            }
                            if (counter == 100) {
                                event.action = MotionEvent.ACTION_UP
                            }

                        }
                    }
                    t.schedule(tt, 0, 100)
                    progress_bar.visibility = View.VISIBLE
            }
            MotionEvent.ACTION_UP -> {
                counter = 0
                progress_bar.progress = counter
                t.cancel()

                videoRecorderJava.stopRecordingVideo()
          //      save()

                    var path = videoRecorderJava.videoPath.absolutePath
                    val values = ContentValues()
                    values.put(MediaStore.Video.Media.TITLE, "Sceneform Video")
                    values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                    values.put(MediaStore.Video.Media.DATA, path)
                    contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)

                    videoView.visibility = View.VISIBLE
                    videoView.setVideoURI(Uri.parse(path))
                    videoView.start()
                    progressLayout.visibility = View.INVISIBLE
             //       viewPageImageSlider.visibility = View.INVISIBLE
                    buttonCross.visibility = View.VISIBLE

                    videoView.setOnPreparedListener(OnPreparedListener { mp ->
                        mp.isLooping = true
                    })



            }
            else -> {
                return false
            }
        }
        return true
    }

}

