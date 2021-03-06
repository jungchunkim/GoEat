# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the liate number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.

-keep class com.GOEAT.Go_Eat.HomeFragment
-keep class com.google.gson.*{*;}
-keep class com.GOEAT.Go_Eat.DataType.*{*;}
-keep class com.GOEAT.Go_Eat.DataType.SimpleFoodInfo
-keep class com.GOEAT.Go_Eat.FindPasswordActivity
-keep class com.GOEAT.Go_Eat.Server_Request.GMailSender
-keep class com.GOEAT.Go_Eat.PwdAuthNumberActivity
-keep class javax.mail.*{*;}
-keep class javax.mail.internet.MimeUtility
