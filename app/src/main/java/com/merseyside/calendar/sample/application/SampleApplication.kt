package com.merseyside.calendar.sample.application

import com.merseyside.archy.BaseApplication

class SampleApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        private lateinit var instance: SampleApplication

        fun getInstance(): SampleApplication {
            return instance
        }
    }
}