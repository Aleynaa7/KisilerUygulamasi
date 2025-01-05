package com.example.kisileruygulamasi.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kisileruygulamasi.data.entity.Kisiler
import com.example.kisileruygulamasi.data.repo.KisilerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnasayfaViewModel @Inject constructor(var kisilerRepository: KisilerRepository) : ViewModel() { //kişilerRepository nesnesi türünde bir dğeişken oluştur, alttakine gerek aklamz: bağımsız olması yolunca yapılan işlemler
    //var kisilerRepository = KisilerRepository() //BUNLAR BİRER BAĞIMLILIKTIR BİZ BAĞIMSIZ YAĞMAYA ÇALIŞIYORUZ DEPENDECY INJECTİON
    var kisilerListesi = MutableLiveData<List<Kisiler>>() //bu livedatanın amacı arayüze kişiler listesini iletmektir, hangi türde gndereceksek onu yazarıs (list gibi)

    //mainactivityde başta anasayfaview model nesnesi oluştuğu an bu init fonksitonu oluşur o da kisileriyukle fonksiyonunu direkt çalıştırır: bu olmazsa boş liste döner aşağıdaki kisileriYukle fonk çalışmaz
    init {
        kisileriYukle()
    }

    fun sil(kisi_id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            kisilerRepository.sil(kisi_id)
            kisileriYukle() //silinen kişinin silinmiş hali ile kisilerin gelmesi için, yani veriyabanından silme işlemini zaten göreceğiz ama arayüze de silinmiş halinin yansıması için bunu yapıyoruz
        }
    }

    fun kisileriYukle(){
        CoroutineScope(Dispatchers.Main).launch {
            kisilerListesi.value = kisilerRepository.kisileriYukle()
        }
    }

    fun ara(aramaKelimesi:String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                kisilerListesi.value = kisilerRepository.ara(aramaKelimesi)
            }catch (e:Exception){}
        }
    }
}