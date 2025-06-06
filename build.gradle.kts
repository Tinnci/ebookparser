plugins {
    id("com.android.library")
}

android {
    // 为这个库模块定义一个独立的命名空间
    namespace = "com.gacode.ebookparser"
    
    // 使用与主应用相同的 compileSdk
    compileSdk = 34

    defaultConfig {
        // minSdk 也应与主应用保持一致
        minSdk = 21
    }

    // 明确指定此模块的 Java 编译选项
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // 这个库模块本身没有其他外部依赖
} 