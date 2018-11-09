import 'dart:async';
import 'dart:io';
import 'dart:ui';

import 'package:flutter/services.dart';
import 'package:meta/meta.dart';

class FlutterPhotoFilters {
  static const MethodChannel _channel =
      const MethodChannel('flutter_photo_filters');

  static Future<File> filterImage({
    @required String sourcePath,
    String toolbarTitle, // for only Android
    Color toolbarColor, // for only Android
    bool circleShape: false,
  }) async {
    assert(sourcePath != null);

    final String resultPath =
        await _channel.invokeMethod('filterImage', <String, dynamic>{
      'source_path': sourcePath,
      'toolbar_title': toolbarTitle,
      'toolbar_color': toolbarColor?.value,
      'circle_shape': circleShape
    });
    return resultPath == null ? null : new File(resultPath);
  }
}
