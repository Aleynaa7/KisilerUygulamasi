package com.example.kisileruygulamasi.di

import com.example.kisileruygulamasi.data.datasource.KisilerDataSource
import com.example.kisileruygulamasi.data.repo.KisilerRepository
import com.example.kisileruygulamasi.retrofit.ApiUtils
import com.example.kisileruygulamasi.retrofit.KisilerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton //tek uerde oluşturup her yerde sğalayabilmek anlamına geliyor
    fun provideKisilerRepository(kisilerDataSource: KisilerDataSource) : KisilerRepository{ //genelde provide keywordü ile başlayarak tanımlanır ama ismi önemli değil sonrasına viewmodellerde bağımsız olarka tanımlama adına tanımlanan var değişkenindeki türü yani burada KisilerRepository çekilir
        return KisilerRepository(kisilerDataSource)
    }

    @Provides
    @Singleton
    fun provideKisilerDataSource(kisilerDao: KisilerDao) : KisilerDataSource{
        return KisilerDataSource(kisilerDao) //datasource'a interface eerişmek için bunu eklemiştik, burada da çekmemiz gerektiğinden üst satırda isteyip buraya gnderdik
    }

    //yukarda aldık ama bu interfacei bir fonksiyon yazarak göndermemiz laızm, sadece yukarıda o şkeilde yazmak yetetli değil, bir fonk lazım onu d ayazmış olduk
    @Provides
    @Singleton
    fun provideKisilerDao() : KisilerDao{
        //interfacei göndermemiz lazım bunu da apiuilts classı ile sağlarız
        return ApiUtils.getKisilerDao()

    }
}

//APP MODULE SAĞLAMA İŞLEMİNİ YAPIYOR yani verileri gönderiyor
