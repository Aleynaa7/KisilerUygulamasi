package com.example.kisileruygulamasi.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kisileruygulamasi.data.entity.Kisiler
import com.example.kisileruygulamasi.ui.viewmodel.AnasayfaViewModel
import com.example.kisileruygulamasi.ui.viewmodel.KisiDetayViewModel
import com.example.kisileruygulamasi.ui.viewmodel.KisiKayitViewModel
import com.google.gson.Gson

@Composable
fun SayfaGecisleri(anasayfaViewModel: AnasayfaViewModel,
                   kisiDetayViewModel: KisiDetayViewModel,
                   kisiKayitViewModel: KisiKayitViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa"){
        composable("anasayfa"){
            Anasayfa(navController = navController, anasayfaViewModel = anasayfaViewModel)
        }
        composable("kisiKayitSayfa"){
            KisiKayitSayfa(kisiKayitViewModel = kisiKayitViewModel)
        }
        composable(
            //kisi tanımlanna şey alttaki üç yerdede aynı olmalı
            "kisiDetaySayfa/{kisi}",
            arguments = listOf(navArgument("kisi") {type=NavType.StringType})
        ){
            //anasayfada string olarak oluşan veriyi alıyoruz , nesneye tekrar döndürmöemiz gerekir gsona yani
            val json = it.arguments?.getString("kisi")
            val nesne = Gson().fromJson(json, Kisiler::class.java)
            KisiDetaySayfa(gelenKisi = nesne, kisiDetayViewModel = kisiDetayViewModel)
        }
    }
}