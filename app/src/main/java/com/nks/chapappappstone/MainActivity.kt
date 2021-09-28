package com.nks.chapappappstone

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.nks.chapappappstone.R
import com.google.firebase.auth.FirebaseUser
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import com.nks.chapappappstone.Fragments.ChatFragment
import com.nks.chapappappstone.Fragments.UsersFragment
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nks.chapappappstone.LoginRegister
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var profile_pic: CircleImageView
    lateinit var username: TextView
    private var mAuth: FirebaseAuth? = null
    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle("")
        profile_pic = findViewById(R.id.profile_image)
        username = findViewById(R.id.username)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser!!
        username.text=user.displayName
        Glide.with(applicationContext).load(user.photoUrl).into(profile_pic)


        val navView: BottomNavigationView=findViewById(R.id.nav_view)
        val navController = findNavController(this, R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

//        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
//        val viewPager = findViewById<ViewPager>(R.id.view_pager)
//        val viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter(
//            supportFragmentManager
//        )
//        viewPagerAdapter.addFragment(ChatFragment(), "Chats")
//        viewPagerAdapter.addFragment(UsersFragment(), "Users")
//        viewPager.adapter = viewPagerAdapter
//        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(
                    Intent(
                        this@MainActivity,
                        LoginRegister::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                return true
            }
        }
        return false
    }

    internal inner class ViewPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(
        fm!!
    ) {
        private val fragments: ArrayList<Fragment>
        private val titles: ArrayList<String>
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        init {
            fragments = ArrayList()
            titles = ArrayList()
        }
    }
}