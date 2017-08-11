# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\xiaocoder\androidstudiosdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


#---------------------------------------------------android studio自带的混淆规则-----start------------------------------------------------
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-dontoptimize
-dontpreverify

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn android.support.**

-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}
#---------------------------------------------------android studio自带的混淆规则--------end---------------------------------------------


#--------------------------------------------------记录生成的日志数据,gradle build时在本项目根目录输出---start----------------------
 #混淆时是否记录日志
-verbose
#apk 包内所有 class 的内部结构
-dump proguard/class_files.txt
#未混淆的类和成员
-printseeds proguard/seeds.txt
#列出从 apk 中删除的代码
-printusage proguard/unused.txt
#混淆前后的映射
-printmapping proguard/mapping.txt
#--------------------------------------------------记录生成的日志数据，gradle build时 在本项目根目录输出---end-----------------

#---------------------------------------------------通用----------start--------------------------------------------------------------------
# 混淆时所采用的算法
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#忽略警告
#-ignorewarning

#避免混淆泛型 如果混淆报错建议关掉
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

-keep class * extends android.app.Activity
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider

#不混淆Fragment，V4，V7，注解 等相关类
-keep public class * extends android.app.Fragment
-keep public class * extends android.view.View
-keep public class * extends android.app.Application
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.backup.BackupAgentHelper

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

-keep class * extends java.lang.annotation.Annotation { *; }


-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keep class **.R$* {*;}
-keep class **.R{*;}
#---------------------------------------------------通用----------end--------------------------------------------------------------------


#------------------------------model--------------------------
#-keep class com.matrix.app.entity.json.** { *; }



#------------------------------js----------------------------
-keepattributes *JavascriptInterface*

-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
#------------------------------反射-------------------------



#-----------------------------第三方, 一般只需要添加忽略警告和保持class不被混淆的声明-------------------------
##----------------------okhttp-------------------------------
-dontwarn okio.**

##----------------------Bugly--------------------------------
-dontwarn com.tencent.bugly.**
-keep class com.tencent.bugly.** {*;}
