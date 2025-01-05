package com.example.kisileruygulamasi.data.repo

import android.util.Log
import com.example.kisileruygulamasi.data.datasource.KisilerDataSource
import com.example.kisileruygulamasi.data.entity.Kisiler

//@ınject construcyor kullanma vazen gerek yok burası da oralardan biri , appmodule ile çekeceğiz direkt isteyeceğiz yani:: yani içeriden bunu çözdük bu yüzden @inject cons yazmaya gerek yok, kayıt3 ilk 10 dkda bir yerde söylüyr bunu!!
//BURADA DA İSTEMİŞ OLDUK (app modulede sağlama işlemi yapılır buradan istenenlerle)
class KisilerRepository (var kisilerDataSource: KisilerDataSource) { //dolaylı olarak kisiler datasourcedan bir nesneye ihtiyaç duyarak veri aktarılacak bu yüzden inject ya da constructor gerek yok
    //repository kaydet fonk ile datasourcedaki kaydeti çalıştıracak
    suspend fun kaydet(kisi_ad:String,kisi_tel:String) = kisilerDataSource.kaydet(kisi_ad, kisi_tel)

    suspend fun guncelle(kisi_id:Int,kisi_ad:String,kisi_tel:String) = kisilerDataSource.guncelle(kisi_id, kisi_ad, kisi_tel)

    suspend fun sil(kisi_id:Int) = kisilerDataSource.sil(kisi_id)

    suspend fun kisileriYukle() : List<Kisiler> = kisilerDataSource.kisileriYukle()

    suspend fun ara(aramaKelimesi:String) : List<Kisiler> = kisilerDataSource.ara(aramaKelimesi)
}