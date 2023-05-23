package com.bedu.sacuchero.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bedu.sacuchero.R
import com.bedu.sacuchero.databinding.ActivityLoggedBinding

class LoggedActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityLoggedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoggedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarLogged.toolbar)

        binding.appBarLogged.fab.setOnClickListener { view ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Contáctanos")
            builder.setMessage("guillermo.add@hotmail.com")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                // Acción a realizar cuando se presiona el botón Aceptar
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()

        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_logged)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_kart, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_logged)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}