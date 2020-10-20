package no.realitylab.arface

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.AugmentedFaceNode
import no.realitylab.arface.internetService.MainActivity
import java.util.*
import kotlin.collections.ArrayList

class FilterFace(augmentedFace: AugmentedFace?,
                 val context: Context): AugmentedFaceNode(augmentedFace) {

    private var cardNode: Node? = null
    private var imageView: ImageView? = null
    private var decoder = ArrayList<String>()
    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable
    var faceRegionsActivity = FaceRegionsActivity()
    var animals = ArrayList<Bitmap>()





//    fun bitmap():ArrayList<Bitmap>{
//     //   faceRegionsActivity.SerciveInternet()
//        faceRegionsActivity.arrayimagehead
//        return faceRegionsActivity.arrayimagehead
//    }

    override fun onActivate() {
        super.onActivate()
        cardNode = Node()
        cardNode?.setParent(this)

        mHandler = Handler()

        ViewRenderable.builder()
            .setView(context, R.layout.card_layout)
            .build()
            .thenAccept { uiRenderable: ViewRenderable ->
                uiRenderable.isShadowCaster = false
                uiRenderable.isShadowReceiver = false
                cardNode?.renderable = uiRenderable
                imageView = uiRenderable.view.findViewById(R.id.imageView)
          //      textView = uiRenderable.view.findViewById(R.id.title)
            }
            .exceptionally { throwable: Throwable? ->
                throw AssertionError(
                    "Could not create ui element",
                    throwable
                )
            }
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        augmentedFace?.let {face ->
            val rightForehead = face.getRegionPose(AugmentedFace.RegionType.FOREHEAD_RIGHT)
            val leftForehead = face.getRegionPose(AugmentedFace.RegionType.FOREHEAD_LEFT)
            val center = face.centerPose

            cardNode?.worldPosition = Vector3((leftForehead.tx() + rightForehead.tx()) / 2,
                (leftForehead.ty() + rightForehead.ty()) / 2 + 0.05f , center.tz() / 0.35f)
        }
    }

    fun animate() {
    //   faceRegionsActivity = FaceRegionsActivity()
        //bundle ile string yolla burda bitmape cevir

        // animals = bitmap()// Runnable
        // Delay in milliseconds
// Runnable
        // Delay in milliseconds

        // Schedule the task to repeat
// Schedule the task to repeat
        //     textView?.text = animals[index]
        //   textView?.text = animals[currentIndex]
//        when {
//            faceRegionsActivity.index() == 1 -> {
//        //        animals = arrayOf()
//            }
//            faceRegionsActivity.index() == 2 -> {
//           //     animals = arrayOf()
//            }
//            faceRegionsActivity.index() == 3 -> {
//         //       animals = arrayOf(R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d)
//            }
//        }

        val index = (animals.indices).random()
        val rounds = (1..2).random()
        var currentIndex = 0
        var currentRound = 0


        mRunnable = Runnable {
            imageView?.setImageBitmap(animals[currentIndex])
         //   textView?.text = animals[currentIndex]
            currentIndex ++
            if (currentIndex == animals.size) {
                currentIndex = 0
                currentRound ++
            }

            if (currentRound == rounds) {
                imageView?.setImageBitmap(animals[index])
           //     textView?.text = animals[index]
            } else {
                // Schedule the task to repeat
                mHandler.postDelayed(
                    mRunnable, // Runnable
                    100 // Delay in milliseconds
                )
            }
        }

        // Schedule the task to repeat
        mHandler.postDelayed(
            mRunnable, // Runnable
            100 // Delay in milliseconds
        )
    }

    fun refresh() {
        imageView?.setImageResource(R.drawable.set)
    //    textView?.text = context.getText(R.string.quiz_title)
     //   textView?.text = context.getText(R.string.quiz_title)
    }
}