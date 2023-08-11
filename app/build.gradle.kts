plugins {
    id("com.android.application")
    id("realm-android")


}



apply{

    plugin("realm-android")

}



android {
    namespace = "com.example.cncdcattleedcandroid"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.cncdcattleedcandroid"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures{

        viewBinding = true;
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.github.blackfizz:eazegraph:1.2.5l@aar")
    implementation("com.nineoldandroids:library:2.4.0")
    //for logging
    implementation("com.orhanobut:logger:2.2.0")

    implementation("com.google.android.gms:play-services-location:21.0.1")


    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    //Gson
    implementation("com.squareup.retrofit2:converter-gson:2.1.0")

    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")

    //chart
    implementation ("com.github.AnyChart:AnyChart-Android:1.1.5")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("com.github.SMehranB:AnimatedTextView:1.1.0")
    implementation ("com.daimajia.androidanimations:library:2.4@aar")


    //animation
    implementation ("com.airbnb.android:lottie:3.4.0")

    //loading Dialog
    implementation("com.github.basusingh:BeautifulProgressDialog:1.001")


    //table data grid

    implementation("com.github.evrencoskun:TableView:v0.8.9.4")


    //image loading
    implementation("com.github.bumptech.glide:glide:4.15.1")


    //realm database


}