import 'package:cube_guide/utils/app_controller.dart';
import 'package:flutter_svg/svg.dart';
import 'package:format/format.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:flutter/services.dart';
import 'dart:async';

import '../utils/models.dart';

class CubeSvg {
  static final Map<String, String> svgData = {};
  static late AppController c;

  static final List<String> svgPaths = [
    'assets/svg_templates/2x2x2.svg',
    'assets/svg_templates/3x3x3.svg',
    'assets/svg_templates/4x4x4.svg',
    'assets/svg_templates/5x5x5.svg',
    'assets/svg_templates/pyraminx.svg',
    'assets/svg_templates/square-1.svg',
    'assets/svg_templates/2x2x2_oll.svg',
    'assets/svg_templates/3x3x3_oll.svg',
    'assets/svg_templates/3x3x3_pll.svg',
  ];

  // static var colors = {
  //   'W': '#ffffff',
  //   'Y': '#fff144',
  //   'R': '#ea0600',
  //   'O': '#ffa40d',
  //   'G': '#07a42e',
  //   'B': '#0025ff',
  //   'X': '#9c9c9c',
  //   'T': '',
  //   'F': 'none'
  // };

  static Future<void> initCubeSvg(AppController controller) async {
    c = controller;
    print("Loading SVGs");
    for (var path in svgPaths) {
      var data = await rootBundle.loadString(path);
      String filename = path.split('/').last.split('.').first;
      svgData[filename] = data;
    }
  }

  static Widget cubeSvg(
    String title,
    String notation, {
    double? width,
    double? height,
    String? frontSide,
    String? topSide,
  }) {
    title = switch (title) {
      "cube_3x3x3" || "f2l" || "coll" || "mw" || "olc" => "3x3x3",
      "cll" || "eg1" || "eg2" => "2x2x2_oll",
      "oll" || "ell" => "3x3x3_oll",
      _ => title
    };
    var svg = svgData[title];
    if (svg == null) {
      // Center by vertical and horizontal axis
      return const SizedBox(
        width: 125,
        height: 125,
        child: CircularProgressIndicator(),
      );
      // return const CircularProgressIndicator();
    }

    return Obx(() {
      final map = (frontSide != null && topSide != null)
          ? _rotatedColors(frontSide!, topSide!)
          : c.rotatedColors;

      var colorsArray = notation.split('').map((e) => map[e]).toList();
      var out = svg.format(colorsArray);

      return SvgPicture.string(
        out,
        width: width,
        height: height,
      );
    });
  }

  /// Compute rotated colors for a cube with [front] and [top] sides.
  static Map<String, String> _rotatedColors(String front, String top) {
    final up = colorVectors[top]!;
    final frontVec = colorVectors[front]!;
    final right = _cross(up, frontVec);

    String fromVector(List<int> v) {
      for (final entry in colorVectors.entries) {
        final vec = entry.value;
        if (vec[0] == v[0] && vec[1] == v[1] && vec[2] == v[2]) {
          return entry.key;
        }
      }
      return 'X';
    }

    final map = <String, String>{
      'Y': top,
      'W': fromVector([-up[0], -up[1], -up[2]]),
      'B': front,
      'G': fromVector([-frontVec[0], -frontVec[1], -frontVec[2]]),
      'R': fromVector(right),
      'O': fromVector([-right[0], -right[1], -right[2]]),
    };

    return {
      for (final entry in c.colors.entries)
        entry.key: map.containsKey(entry.key)
            ? c.colors[map[entry.key]]!
            : entry.value,
    };
  }

  static List<int> _cross(List<int> a, List<int> b) {
    return [
      a[1] * b[2] - a[2] * b[1],
      a[2] * b[0] - a[0] * b[2],
      a[0] * b[1] - a[1] * b[0],
    ];
  }
}
