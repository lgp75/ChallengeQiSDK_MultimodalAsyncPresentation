package com.softbankrobotics.multimodalpresentationasync

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aldebaran.qi.sdk.QiContext
import com.aldebaran.qi.sdk.QiSDK
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks
import com.aldebaran.qi.sdk.`object`.actuation.Animate
import com.aldebaran.qi.sdk.`object`.conversation.Say
import com.aldebaran.qi.sdk.builder.AnimateBuilder
import com.aldebaran.qi.sdk.builder.AnimationBuilder
import com.aldebaran.qi.sdk.builder.SayBuilder
import com.aldebaran.qi.sdk.design.activity.RobotActivity
import com.aldebaran.qi.sdk.design.activity.conversationstatus.SpeechBarDisplayStrategy
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule


private const val TAG = "MyLogTag"

class MainActivity : RobotActivity(), RobotLifecycleCallbacks {

    lateinit var qiContext: QiContext;
    // for part 3
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        QiSDK.register(this, this)
        setSpeechBarDisplayStrategy(SpeechBarDisplayStrategy.OVERLAY)
        // For Part 3:
        mediaPlayer = MediaPlayer()
        setContentView(R.layout.activity_main)
    }

    override fun onPause() {
        super.onPause()
        // for Part 3:
        mediaPlayer.stop()
    }

    override fun onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this)
        super.onDestroy()
        // For Part 3:
        mediaPlayer.release()
    }

    override fun onRobotFocusGained(qiContext: QiContext) {
        this.qiContext = qiContext
        Log.i(TAG, "Robot focus gained, running.")
        runPresentation()
    }

    override fun onRobotFocusLost() {
        TODO("Not yet implemented")
    }

    override fun onRobotFocusRefused(reason: String?) {
        TODO("Not yet implemented")
    }

    ////////////////////////////
    //        helpers         //
    ////////////////////////////

    private fun makeSay(text: String) : Say {
        return SayBuilder.with(qiContext)
                .withText(text)
                .build()
    }

    private fun setImage(resource: Int) {
            splashImageView.setImageResource(resource)
            splashImageView.visibility = View.VISIBLE
    }

    private fun clearImage() {
            splashImageView.visibility = View.GONE
    }

    private fun makeAnimate(animResource: Int) : Animate {
        val animation = AnimationBuilder.with(qiContext)
                .withResources(animResource)
                .build()

        return AnimateBuilder.with(qiContext)
                .withAnimation(animation)
                .build()
    }

    // For Part 3:
    private fun playMedia(mediaResource: Int) {
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(applicationContext, mediaResource)
        mediaPlayer.start()
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            applicationContext,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    ////////////////////////
    // Presentation logic //
    ////////////////////////

    private fun runPresentation() {

        /////////////////////////////////////////////////
        //////////////////  HANDS ON   //////////////////
        // Code part 4 then part 2
        // Use showToast to notify which Part is being played
        // Check assets
        // Correct bugs

        // Part 1: "Making me talk..."

        setImage(R.drawable.scene1)
        makeSay("Okay, so ... making me talk is a first step a bit like ...").run()
        Thread.sleep(200)
        makeSay("a rolling rock ...").run()
        setImage(R.drawable.scene2)

        // Part 2: "Let me show you..."

        Thread.sleep(800)
        setImage(R.drawable.scene3)

        /////////////////////////////////////////////////
        //////////////////  HANDS ON 2 //////////////////
        // Assets :
        //      animation : nicereaction_a002
        //      image : scene4
        //      text for say: "But there's much more to do. Let me show you !"
        //
        // 1/ Make a say and an animation using nicereaction_a002
        // 2/ Run them synchronously and display image
        // 3/ Run them asynchronously
        // 4/ Add a callback after animation to display the scene4 image when anim ends
        // 5/ Run all simultaneously
        //                                             //
        /////////////////////////////////////////////////

        // Part 3: "I can make sound"

        playMedia(R.raw.stone_breaks)
        setImage(R.drawable.scene5)
        makeSay("Like me, a rock can make sound.").run()
        setImage(R.drawable.scene6)
        Thread.sleep(1_000)
        setImage(R.drawable.scene7)
        makeSay("and light.").run()
        Thread.sleep(1_000)
        setImage(R.drawable.scene8)

        // Part 4: "And become beautiful"

        Timer("Diamond", false).schedule(2000) {
            setImage(R.drawable.scene9)
            playMedia(R.raw.magic)
        }

        /////////////////////////////////////////////////
        //////////////////  HANDS ON 1 //////////////////
        // Assets :
        //      animation : yeah_b001
        //      image : scene10
        //      text for say: "and become beautiful and precious! All this thanks to you"
        //
        // Step by step
        //
        // 1/ Make a say with text and an animation using yeah_b001
        // 2/ Run them synchronously and display image
        // 3/ Run them asynchronously
        // 3/ Run them simultaneously

        //                                             //
        /////////////////////////////////////////////////

        makeSay("I can't wait for what we are gonna do !").run()
        ////////////////////////////////////////////////////////////////
        Thread.sleep(1_000)
        clearImage()
    }

}
