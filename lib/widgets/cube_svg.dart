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

  static Widget cubeSvg(String title, String notation, {double? width, double? height}) {
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
      var colorsArray = notation.split('').map((e) => c.colors[e]).toList();
      var out = svg.format(colorsArray);

      return SvgPicture.string(
        out,
        width: width,
        height: height,
      );
    });
  }
}
