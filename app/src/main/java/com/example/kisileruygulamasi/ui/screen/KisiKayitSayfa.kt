package com.example.kisileruygulamasi.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kisileruygulamasi.ui.viewmodel.KisiKayitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KisiKayitSayfa(kisiKayitViewModel: KisiKayitViewModel){
    //textfield ile çalışmak için state özelliği olan değişkenler ile çalılmalısın
    val tfKisiAd = remember { mutableStateOf("") }
    val tfKisiTel = remember { mutableStateOf("") }


    Scaffold(topBar = { TopAppBar(title = { Text(text = "Kişi Kayıt") }) }) { paddingValues ->
        Column (
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value=tfKisiAd.value,
                onValueChange={tfKisiAd.value = it},  //it denilen kullanıcıdan alınan
                label = { Text(text="Kişi Ad") }
            )
            TextField(
                value=tfKisiTel.value,
                onValueChange={tfKisiTel.value = it},
                label = { Text(text="Kişi Tel") }
            )
            Button(onClick = {
                //butona basınca textfieldlardan en son ne varsa o bilgiyi alır, yukardaki fonk ile kullanırız (ilerideki veritaban işlemleri için vs ileriye dönül yazdık şimdiden ki şimdi de log ile konsola yazdırarak kontrol ettik zaten, sonrasında da list ile ana saygaya çekeceğiz)
                kisiKayitViewModel.kaydet(tfKisiAd.value, tfKisiTel.value)

            }) {
                Text(text = "KAYDET")
            }
        }
    }
}