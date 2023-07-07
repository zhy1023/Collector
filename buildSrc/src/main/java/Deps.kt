import App.gradlePluginVersion
import KtVersion.coroutines_version
import KtVersion.datastore_version
import KtVersion.jetpack_version
import KtVersion.kt_plugin_version
import KtVersion.kt_version
import KtVersion.ktx_version
import KtVersion.room_version
import LibVersion.activity_ktx_version
import LibVersion.appcompat_version
import LibVersion.arouter_api_version
import LibVersion.arouter_compiler_version
import LibVersion.constraintlayout_version
import LibVersion.fragment_ktx_version
import LibVersion.glide_version
import LibVersion.legacy_version
import LibVersion.material_version
import LibVersion.okhttp_version
import LibVersion.recyclerview_version
import LibVersion.retrofit_version

object App {
    const val compileSdkVersion = 33
    const val buildToolsVersion = "33.0.2"
    const val targetSdkVersion = 33
    const val minSdkVersion = 26
    const val appId = "com.zy.collector"
    const val versionCode = 1
    const val version_name = "1.0"
    const val version_code = 1

    const val gradlePluginVersion = "7.3.1"
}

object LibVersion {
    const val multidex_version = "2.0.1"
    const val okhttp_version = "4.9.0"
    const val retrofit_version = "2.9.0"
    const val legacy_version = "1.0.0"
    const val activity_ktx_version = "1.3.1"
    const val fragment_ktx_version = "1.3.6"
    const val arouter_api_version = "1.5.1"
    const val arouter_compiler_version = "1.5.1"
    const val material_version = "1.4.0"
    const val appcompat_version = "1.3.0"
    const val recyclerview_version = "1.1.0"
    const val constraintlayout_version = "2.1.2"
    const val glide_version = "4.10.0"
}

object KtVersion {
    const val coroutines_version = "1.6.4"
    const val datastore_version = "1.0.0"
    const val ktx_version = "1.7.0"
    const val kt_version = "1.7.20"
    const val kt_plugin_version = "1.7.20"
    const val jetpack_version = "2.4.1"
    const val room_version = "2.3.0"
}

object Deps {
    const val gradlePlugin = "com.android.tools.build:gradle:$gradlePluginVersion"

    //gradle
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kt_plugin_version"

    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kt_version"
    const val kotlinCore = "androidx.core:core-ktx:$ktx_version"

    //引入协程
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"

    //ktx扩展列表：https://developer.android.com/kotlin/ktx/extensions-list
    //Jetpack相关
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$jetpack_version"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$jetpack_version"
    const val activity_ktx = "androidx.activity:activity-ktx:$activity_ktx_version"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:$fragment_ktx_version"

    //将 Kotlin 协程与生命周期感知型组件一起使用: https://developer.android.com/topic/libraries/architecture/coroutines
    //LifecycleScope协程
    const val lifecycle_runtime =
        "androidx.lifecycle:lifecycle-runtime-ktx:$jetpack_version"

    //ProcessLifecycleOwner给整个 app进程 提供一个lifecycle
    const val lifecycle_process = "androidx.lifecycle:lifecycle-process:$jetpack_version"

    //帮助实现Service的LifecycleOwner
    const val lifecycle_service = "androidx.lifecycle:lifecycle-service:$jetpack_version"
    const val datastore = "androidx.datastore:datastore:$datastore_version"
    const val datastore_pf = "androidx.datastore:datastore-preferences:$datastore_version"

    //room
    const val room_runtime = "androidx.room:room-runtime:$room_version"
    const val room_compiler = "androidx.room:room-compiler:$room_version"

    //AndroidX相关
    const val androidx_material = "com.google.android.material:material:$material_version"
    const val androidx_appcompat = "androidx.appcompat:appcompat:$appcompat_version"
    const val androidx_recyclerView = "androidx.recyclerview:recyclerview:$recyclerview_version"
    const val androidx_constraintLayout =
        "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
    const val androidx_legacy = "androidx.legacy:legacy-support-v4:$legacy_version"
    const val androidx_multidex = "androidx.multidex:multidex:2.0.1"
    const val androidx_viewpager2 = "androidx.viewpager2:viewpager2:1.1.0-beta01"

    //ARouter
    const val arouter_api = "com.alibaba:arouter-api:$arouter_api_version"
    const val arouter_compiler = "com.alibaba:arouter-compiler:$arouter_compiler_version"

    //okhttp
    const val okhttp = "com.squareup.okhttp3:okhttp:$okhttp_version"
    const val okhttp_log_interceptor = "com.squareup.okhttp3:logging-interceptor:$okhttp_version"

    //retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"

    const val glide = "com.github.bumptech.glide:glide:$glide_version"
    const val glide_compiler = "com.github.bumptech.glide:compiler:$glide_version"
}