package io.github.skkallayath.flutterphotofilters;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterPhotoFiltersPlugin */
public class FlutterPhotoFiltersPlugin implements MethodCallHandler {
  private static final String CHANNEL = "flutter_photo_filters";
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), CHANNEL);
    final PhotoFilterDelegate delegate = new PhotoFilterDelegate(registrar.activity());
    registrar.addActivityResultListener(delegate);

    channel.setMethodCallHandler(new FlutterPhotoFiltersPlugin(registrar, delegate));
  }
   
  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (registrar.activity() == null) {
      result.error("no_activity", "flutter_photo_filters plugin requires a foreground activity.", null);
      return;
    }
    if (call.method.equals("filterImage")) {
      delegate.filterImage(call, result);
    }
  }

  FlutterPhotoFiltersPlugin(Registrar registrar, PhotoFilterDelegate delegate) {
    this.registrar = registrar;
    this.delegate = delegate;
  }
}
