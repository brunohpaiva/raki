# Skip runtime check for isOnAndroidDevice().
# One line to make it easy to remove with sed.
-assumevalues class com.google.protobuf.Android { static boolean ASSUME_ANDROID return true; }

-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
  <fields>;
}