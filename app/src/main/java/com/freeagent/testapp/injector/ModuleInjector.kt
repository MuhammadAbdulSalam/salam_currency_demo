package com.freeagent.testapp.injector

import com.freeagent.testapp.api.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ModuleInjector {

    /**
     * Retrofit Api Injection
     */
    @Singleton
    @Provides
    fun provideRetrofitService(): RetrofitBuilder {
        return RetrofitBuilder.create()
    }

}