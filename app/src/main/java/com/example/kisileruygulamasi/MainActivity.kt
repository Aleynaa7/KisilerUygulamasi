package com.example.kisileruygulamasi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kisileruygulamasi.ui.screen.SayfaGecisleri
import com.example.kisileruygulamasi.ui.theme.KisilerUygulamasiTheme
import com.example.kisileruygulamasi.ui.viewmodel.AnasayfaViewModel
import com.example.kisileruygulamasi.ui.viewmodel.KisiDetayViewModel
import com.example.kisileruygulamasi.ui.viewmodel.KisiKayitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val anasayfaViewModel : AnasayfaViewModel by viewModels()
    val kisiKayitViewModel : KisiKayitViewModel by viewModels()
    val kisiDetayViewModel : KisiDetayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KisilerUygulamasiTheme {
                SayfaGecisleri(
                    anasayfaViewModel = anasayfaViewModel,
                    kisiDetayViewModel = kisiDetayViewModel,
                    kisiKayitViewModel = kisiKayitViewModel)
            }
        }
    }
}


//uygulamalar ikiye ayrılır, ui ve data (veritabanı)
//projede zaten ui paketi var üstünden devam et (başta theme geliyor o açıdan)