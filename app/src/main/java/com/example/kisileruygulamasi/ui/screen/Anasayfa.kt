package com.example.kisileruygulamasi.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kisileruygulamasi.R
import com.example.kisileruygulamasi.data.entity.Kisiler
import com.example.kisileruygulamasi.ui.viewmodel.AnasayfaViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Anasayfa(navController: NavController, anasayfaViewModel: AnasayfaViewModel){
    val aramaYapiliyorMu = remember { mutableStateOf(false) }
    val tfAra = remember { mutableStateOf("") }

    //listeyi viewmodelden çekip observsastate özelliği ile bağlarız, listOf() boş gelirse diye boş bir liste döndürsün
    val kisilerListesi = anasayfaViewModel.kisilerListesi.observeAsState(listOf())

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {SnackbarHostState()}

    //başka sayfalara gidip buraya geri döndüğümüzde kisileryükle çalışmalı
    LaunchedEffect (key1 = true) {
        anasayfaViewModel.kisileriYukle()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    //true ise
                    if(aramaYapiliyorMu.value){
                        TextField(
                            value = tfAra.value,
                            onValueChange = {
                                tfAra.value = it
                                anasayfaViewModel.ara(it)
                            },
                            label = { Text(text = "Ara") },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedLabelColor = Color.Black,
                                unfocusedIndicatorColor = Color.White
                            )
                        )
                    }else{ //false ise (baştaki hali, araya tıklayınca aşaıdaki şey işle true olup üst görünür)
                        Text(text = "Kişiler")
                    }

                },
                actions = {
                    if(aramaYapiliyorMu.value){
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = false
                            tfAra.value = "" //kapaua basınca silsin diy, tekrar ara basarsak boş başlasın
                        }) {
                            Icon(painter = painterResource(id = R.drawable.kapat_resim), contentDescription = "")
                        }
                    }else{
                        IconButton(onClick = {
                            aramaYapiliyorMu.value = true
                        }) {
                            Icon(painter = painterResource(id = R.drawable.ara_resim), contentDescription = "")
                        }
                    }


                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("kisiKayitSayfa")
                },
                content = {
                    Icon(painter = painterResource(id = R.drawable.ekle_resim),
                        contentDescription = "")
                }
            )
        },
        //silme uyarısı için olan
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        /*
        Column (
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                val kisi = Kisiler(1, "Ahmet", "1111")
                val kisiJson = Gson().toJson(kisi)
                navController.navigate("kisiDetaySayfa/${kisiJson}")
            }) {
                Text(text = "Detay")
            }
        }*/
        //sayfaya tamamen yapılmasını sğaladık ayrıca yukardaki topbarın da eltında klamasnı sağladık
        LazyColumn (modifier = Modifier.fillMaxSize().padding(paddingValues)){
            items(
                count = kisilerListesi.value.count(),
                itemContent = { //countdaki veri kümesinin sizeına göre tekrarlı çalışır
                    val kisi = kisilerListesi.value[it] //it değeri o veri kümesindeli indexi (0,1,2) değerini verir
                    Card(modifier = Modifier.padding(all = 5.dp)) {
                        Row (
                            modifier = Modifier.fillMaxWidth().clickable {  //rowu tıklanabilir yapmak için
                                val kisiJson = Gson().toJson(kisi)
                                navController.navigate("kisiDetaySayfa/$kisiJson")
                            },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column (modifier = Modifier.padding(all = 10.dp)) {
                                Text(text = kisi.kisi_ad, fontSize = 20.sp)
                                Text(text = kisi.kisi_tel)
                            }

                            IconButton(onClick = {
                                scope.launch {
                                    val snackbar = snackbarHostState.showSnackbar(
                                        message = "${kisi.kisi_ad} silinsin mi?",
                                        actionLabel = "EVET"
                                    )

                                    if(snackbar == SnackbarResult.ActionPerformed){
                                        anasayfaViewModel.sil(kisi.kisi_id)
                                    }
                                }
                            }) {
                                Icon(painter = painterResource(id = R.drawable.kapat_resim),
                                    contentDescription = "", tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}