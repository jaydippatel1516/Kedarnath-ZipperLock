plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.kedarnath.zipperlockscreen"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kedarnath.zipperlockscreen"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    implementation ("com.nostra13.universalimageloader:universal-image-loader:1.9.5")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation ("com.airbnb.android:lottie:5.2.0")
    implementation ("com.karumi:dexter:6.2.3")
    implementation ("com.github.thekhaeng:pushdown-anim-click:1.1.1")
}