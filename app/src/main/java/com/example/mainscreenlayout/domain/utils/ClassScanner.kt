package com.example.mainscreenlayout.domain.utils

import android.content.Context
import android.util.Log
import dalvik.system.DexFile

import dalvik.system.PathClassLoader
import java.io.IOException
import java.util.*
import kotlin.reflect.KClass

abstract class ClassScanner(context: Context) {

    private val TAG = "kek"
    private lateinit var context: Context

    init {
        this.context = context
    }

    fun getContext(): Context {
        return context
    }

    @Throws(IOException::class, ClassNotFoundException::class, NoSuchMethodException::class)
    fun scan() {
        val timeBegin = System.currentTimeMillis()
        val classLoader = getContext().getClassLoader() as PathClassLoader
        //PathClassLoader classLoader = (PathClassLoader) Thread.currentThread().getContextClassLoader();//This also works good
        val dexFile = DexFile(getContext().getPackageCodePath())
        val classNames: Enumeration<String> = dexFile.entries()
        while (classNames.hasMoreElements()) {
            val className: String = classNames.nextElement()
            if (isTargetClassName(className)) {
                //Class<?> aClass = Class.forName(className);//java.lang.ExceptionInInitializerError
                //Class<?> aClass = Class.forName(className, false, classLoader);//tested on 魅蓝Note(M463C)_Android4.4.4 and Mi2s_Android5.1.1
                val aClass =
                    classLoader.loadClass(className).kotlin //tested on 魅蓝Note(M463C)_Android4.4.4 and Mi2s_Android5.1.1
                if (isTargetClass(aClass)) {
                    onScanResult(aClass)
                }
            }
        }
        val timeEnd = System.currentTimeMillis()
        val timeElapsed = timeEnd - timeBegin
        Log.d(TAG, "scan() cost " + timeElapsed + "ms")
    }

    protected abstract fun isTargetClassName(className: String?): Boolean

    protected abstract fun isTargetClass(clazz: KClass<*>?): Boolean

    protected abstract fun onScanResult(clazz: KClass<*>?)
}