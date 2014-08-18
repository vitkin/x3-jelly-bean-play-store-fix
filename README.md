# X3 Jelly Bean Play Store Fix

[Xposed](http://repo.xposed.info/) module that fixes Google Play Store 4.9.13
crash on
[LG Optimus 4X HD (P880)](http://forum.xda-developers.com/optimus-4x-hd) when
tapping on the "hamburger" button at top left corner.

## Additional Information

When tapping on the "hamburger" button, the Google Play Store crashes with the
following "OK" dialog box:

```
Unfortunately Google Play Store has stopped.
```

Here is the error visible using `adb logcat *:W`:

```
W/dalvikvm(15097): threadid=1: thread exiting with uncaught exception (group=0x4175d2a0)
E/AndroidRuntime(15097): FATAL EXCEPTION: main
E/AndroidRuntime(15097): java.lang.IllegalArgumentException: Invalid payload item type
E/AndroidRuntime(15097): at android.util.EventLog.writeEvent(Native Method)
E/AndroidRuntime(15097): at android.app.Activity.onMenuItemSelected(Activity.java:2716)
E/AndroidRuntime(15097): at android.support.v4.app.FragmentActivity.onMenuItemSelected(FragmentActivity.java:373)
E/AndroidRuntime(15097): at com.google.android.finsky.activities.MainActivity.onMenuItemSelected(MainActivity.java:1129)
E/AndroidRuntime(15097): at com.android.internal.widget.ActionBarView$3.onClick(ActionBarView.java:172)
E/AndroidRuntime(15097): at android.view.View.performClick(View.java:4095)
E/AndroidRuntime(15097): at android.view.View$PerformClick.run(View.java:17078)
E/AndroidRuntime(15097): at android.os.Handler.handleCallback(Handler.java:615)
E/AndroidRuntime(15097): at android.os.Handler.dispatchMessage(Handler.java:92)
E/AndroidRuntime(15097): at android.os.Looper.loop(Looper.java:137)
E/AndroidRuntime(15097): at android.app.ActivityThread.main(ActivityThread.java:4872)
E/AndroidRuntime(15097): at java.lang.reflect.Method.invokeNative(Native Method)
E/AndroidRuntime(15097): at java.lang.reflect.Method.invoke(Method.java:511)
E/AndroidRuntime(15097): at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:790)
E/AndroidRuntime(15097): at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:557)
E/AndroidRuntime(15097): at de.robv.android.xposed.XposedBridge.main(XposedBridge.java:132)
E/AndroidRuntime(15097): at dalvik.system.NativeStart.main(Native Method)
W/ActivityManager( 415): Force finishing activity com.android.vending/.AssetBrowserActivity
```

## Additional Information

The error is due to the resource that contains the menu item condensed title
"Applis" which obviously includes some special characters that make the native
method `android.util.EventLog.writeEvent(int, java.lang.Object[])` to crash.

## Resolution

This fix removes any potential special characters from any selected menu item
condensed title before the call to
`android.app.Activity.onMenuItemSelected(int, android.view.MenuItem)`.

## Building

This project is based on Maven and refers to the `XposedBridgeApi-54.jar` as an
artifact.
By the time of writing those lines AFAIK that artifact is not available from any
public Maven repository. Therefore you'll need to add it manually to your local
Maven repository as follow:

1. Download `XposedBridgeApi-54.jar` from the
[XDA Forum Thread](http://forum.xda-developers.com/showpost.php?p=51828909&postcount=1).

2. Place it under your local Maven repository in the following folder:
    * Windows:
      `%USERPROFILE%\.m2\repository\de\robv\android\xposed\XposedBridgeApi\54`

    * UNIX/Linux:
      `~/.m2/repository/de/robv/android/xposed/XposedBridgeApi/54`

3. Create in that folder a `XposedBridgeApi-54.pom` file with the following content:
```XML
<?xml version="1.0" encoding="UTF-8"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
         xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <groupId>de.robv.android.xposed</groupId>
  <artifactId>XposedBridgeApi</artifactId>
  <version>54</version>
  <description>Xposed API</description>
</project>
```
