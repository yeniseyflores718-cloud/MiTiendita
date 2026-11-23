package com.example.mitiendita.vista

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mitiendita.R
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class administrador : AppCompatActivity() {
    private lateinit var bottom_nav: BottomNavigationView
    private lateinit var cardReportes: MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_administrador)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idUsuario = intent.getIntExtra("id_usuario", intent.getIntExtra("idUsuario", 1))

        cardReportes = findViewById(R.id.cardReportes)
        cardReportes.setOnClickListener {
            val intent = Intent(this, ReportesVentaspro::class.java)
            intent.putExtra("id_usuario", idUsuario)
            startActivity(intent)
        }

        bottom_nav = findViewById(R.id.bottom_nav)
        bottom_nav.selectedItemId = R.id.nav_menu

        bottom_nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_menu -> {
                    true
                }

                R.id.nav_inventario -> {
                    val intent = Intent(this, inventario::class.java)
                    startActivity(intent)
                    true
                }

                else -> true
            }
        }
    }
}