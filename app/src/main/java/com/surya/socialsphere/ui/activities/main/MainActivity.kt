package com.surya.socialsphere.ui.activities.main

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.surya.socialsphere.R
import com.surya.socialsphere.ui.fragments.*
import com.surya.socialsphere.ui.fragments.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var iconHome: ImageView
    private lateinit var txtHome: TextView
    private lateinit var iconExplore: ImageView
    private lateinit var txtExplore: TextView
    private lateinit var iconMessages: ImageView
    private lateinit var txtMessages: TextView
    private lateinit var iconProfile: ImageView
    private lateinit var txtProfile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iconHome = findViewById(R.id.iconHome)
        txtHome = findViewById(R.id.txtHome)
        iconExplore = findViewById(R.id.iconExplore)
        txtExplore = findViewById(R.id.txtExplore)
        iconMessages = findViewById(R.id.iconMessages)
        txtMessages = findViewById(R.id.txtMessages)
        iconProfile = findViewById(R.id.iconProfile)
        txtProfile = findViewById(R.id.txtProfile)


        // Load HomeFragment by default
        if (savedInstanceState == null) {
            switchFragment(HomeFragment(), 0)
        }

        findViewById<View>(R.id.navHome).setOnClickListener {
            switchFragment(HomeFragment(), 0)
        }
        findViewById<View>(R.id.navExplore).setOnClickListener {
            switchFragment(ExploreFragment(), 1)
        }
        findViewById<View>(R.id.navCreate).setOnClickListener {
            val bottomSheet = CreatePostBottomSheet()
            bottomSheet.show(supportFragmentManager, CreatePostBottomSheet.TAG)
        }
        findViewById<View>(R.id.navCreate).setOnClickListener {
            switchFragment(CreateFragment(), 2)
        }
        findViewById<View>(R.id.navMessages).setOnClickListener {
            switchFragment(MessagesFragment(), 3)
        }
        findViewById<View>(R.id.navProfile).setOnClickListener {
            switchFragment(ProfileFragment(), 4)
        }
    }

    private fun switchFragment(fragment: Fragment, tabIndex: Int) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        updateTabColors(tabIndex)
    }

    private fun updateTabColors(activeIndex: Int) {
        val activeColor = 0xFF3B82F6.toInt()
        val inactiveColor = 0xFF6B7280.toInt()

        iconHome.setColorFilter(if (activeIndex == 0) activeColor else inactiveColor)
        txtHome.setTextColor(if (activeIndex == 0) activeColor else inactiveColor)

        iconExplore.setColorFilter(if (activeIndex == 1) activeColor else inactiveColor)
        txtExplore.setTextColor(if (activeIndex == 1) activeColor else inactiveColor)

        iconMessages.setColorFilter(if (activeIndex == 3) activeColor else inactiveColor)
        txtMessages.setTextColor(if (activeIndex == 3) activeColor else inactiveColor)

        iconProfile.setColorFilter(if (activeIndex == 4) activeColor else inactiveColor)
        txtProfile.setTextColor(if (activeIndex == 4) activeColor else inactiveColor)
    }
}