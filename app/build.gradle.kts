@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinKapt)
}

android {
    namespace = "com.shashank.countrylist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.shashank.countrylist"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"endwswxlonboardapiqa.azurewebsites.net/\"")
            buildConfigField("String", "AUTH_URL", "\"endwswxlonboardauthqa.azurewebsites.net/\"")
            buildConfigField("String", "GRANT_TYPE", "\"password\"")
            buildConfigField("String", "CLIENT_ID", "\"wxlapp-mobile\"")
            buildConfigField("String", "CLIENT_SECRET", "\"secret\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug{
            buildConfigField("String", "BASE_URL", "\"endwswxlonboardapiqa.azurewebsites.net/\"")
            buildConfigField("String", "AUTH_URL", "\"endwswxlonboardauthqa.azurewebsites.net/\"")
            buildConfigField("String", "GRANT_TYPE", "\"password\"")
            buildConfigField("String", "CLIENT_ID", "\"wxlapp-mobile\"")
            buildConfigField("String", "CLIENT_SECRET", "\"secret\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.android.compiler)
    implementation(libs.compose.ui.util)
    implementation(libs.navigation.compose)
    implementation(libs.compose.material)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.coroutines)
    implementation(libs.keystore)
    implementation(libs.okhttp)
    implementation(libs.gson.converter)
    implementation(libs.splashscreen)
    implementation(libs.view.models)
    implementation(libs.material.icons)
    implementation(libs.compose.lifecycle)
}