package io.github.skkallayath.flutterphotofilters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.Date;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

import static android.app.Activity.RESULT_OK;

public class PhotoFilterDelegate implements PluginRegistry.ActivityResultListener {
    private final Activity activity;
    private MethodChannel.Result pendingResult;
    private MethodCall methodCall;
    // private FileUtils fileUtils;

    public PhotoFilterDelegate(Activity activity) {
        this.activity = activity;
        // fileUtils = new FileUtils();
    }

    public void filterImage(MethodCall call, MethodChannel.Result result) {
        String sourcePath = call.argument("source_path");
        String title = call.argument("toolbar_title");
        Long color = call.argument("toolbar_color");
        Boolean circleShape = call.argument("circle_shape");
        methodCall = call;
        pendingResult = result;

        File outputDir = activity.getCacheDir();
        File outputFile = new File(outputDir, "filter_image_" + (new Date()).getTime() + ".jpg");

        Uri sourceUri = Uri.fromFile(new File(sourcePath));
        Uri destinationUri = Uri.fromFile(outputFile);
        PhotoFilterActivity _activity = new PhotoFilterActivity(this.activity, sourceUri, destinationUri);

    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                finishWithSuccess(data.getExtras().getString("resultUri"));
                return true;
            } else {
                pendingResult.success(null);
                clearMethodCallAndResult();
                return true;
            }
        }
        return false;
    }

    private void finishWithSuccess(String imagePath) {
        pendingResult.success(imagePath);
        clearMethodCallAndResult();
    }

    private void finishWithError(String errorCode, String errorMessage, Throwable throwable) {
        pendingResult.error(errorCode, errorMessage, throwable);
        clearMethodCallAndResult();
    }

    private void clearMethodCallAndResult() {
        methodCall = null;
        pendingResult = null;
    }
}