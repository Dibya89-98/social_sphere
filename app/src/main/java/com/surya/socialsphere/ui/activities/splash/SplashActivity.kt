package com.surya.socialsphere.ui.activities.splash

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.surya.socialsphere.R
import com.surya.socialsphere.ui.activities.login.LoginActivity
import com.surya.socialsphere.ui.activities.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val clayCard = findViewById<View>(R.id.clayCard)
        val imgLogo = findViewById<View>(R.id.imgLogo)
        val progressBarFill = findViewById<View>(R.id.progressBarFill)

        // 1. Initial State for Entrance Animation
        clayCard.alpha = 0f
        clayCard.scaleX = 0.8f
        clayCard.scaleY = 0.8f

        // 2. Entrance Pop-In Animation
        clayCard.animate()
            .alpha(1f)
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(850)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                startPulsingAnimation(imgLogo)
            }
            .start()

        // 3. Smooth Bottom Loading Bar Animation (duration matches delay)
        val progressAnimator = ValueAnimator.ofInt(0, 160).apply {
            duration = 2800
            addUpdateListener { animator ->
                val params = progressBarFill.layoutParams
                params.width = (animator.animatedValue as Int) * resources.displayMetrics.density.toInt()
                progressBarFill.layoutParams = params
            }
        }
        progressAnimator.start()

        // 4. Transition to MainActivity after 3 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 3000)
    }

    // Breathing neon pulse loop effect on the logo
    private fun startPulsingAnimation(view: View) {
        view.animate()
            .scaleX(1.05f)
            .scaleY(1.05f)
            .setDuration(950)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                view.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(950)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .withEndAction {
                        startPulsingAnimation(view)
                    }
                    .start()
            }
            .start()
    }
}