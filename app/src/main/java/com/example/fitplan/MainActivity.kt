package com.example.fitplan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fitplan.UI.fragments.sign_out.SignOutFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)as NavHostFragment

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.myWorkout_btn -> {
                    navController.navController.navigate(R.id.myPlansFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.planWorkout_btn ->{
                    navController.navController.navigate(R.id.planWorkoutFragment2)
                    return@setOnItemSelectedListener true
                }
                R.id.social_btn ->{
                    navController.navController.navigate(R.id.socialFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.run_btn ->{
                    navController.navController.navigate(R.id.runFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.signOut_btn ->{
                    val signOutDialog = SignOutFragment()
                    signOutDialog.show(supportFragmentManager, "SignOutDialog")
                    return@setOnItemSelectedListener true
                }
                else -> {return@setOnItemSelectedListener false}
            }
        }
    }
}