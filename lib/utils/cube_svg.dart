import 'package:flutter_svg/svg.dart';
import 'package:format/format.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'dart:async';

class CubeSvg {
  static final Map<String, String> svgData = {};

  static final List<String> svgPaths = [
    'assets/svg_templates/2x2x2.svg',
    'assets/svg_templates/3x3x3.svg',
    'assets/svg_templates/4x4x4.svg',
    'assets/svg_templates/5x5x5.svg',
    'assets/svg_templates/Pyraminx.svg',
    'assets/svg_templates/Square-1.svg',
  ];

  static final Map<String, String> svgSolved = {
    '2x2x2': 'B' * 4 + 'Y' * 4 + 'R' * 4,
    '3x3x3': 'B' * 9 + 'Y' * 9 + 'R' * 9,
    '4x4x4': 'B' * 16 + 'Y' * 16 + 'R' * 16,
    '5x5x5': 'B' * 25 + 'Y' * 25 + 'R' * 25,
    'Pyraminx': 'Y' * 9 + 'R' * 9,
    'Square-1': 'G' * 7 + 'W' * 8 + 'R' * 8,
  };

  static var colors = {
    'W': '#ffffff',
    'Y': '#fff144',
    'R': '#ea0600',
    'O': '#ffa40d',
    'G': '#07a42e',
    'B': '#0025ff',
    'X': '#9c9c9c'
  };

  static Future<void> initCubeSvg() async {
    for (var path in svgPaths) {
      var data = await rootBundle.loadString(path);
      String filename = path.split('/').last.split('.').first;
      svgData[filename] = data;
    }
  }

  static Widget cubeCardSolved(String title) {
    String? notation = svgSolved[title];
    if (notation == null) {
      return Text('Error: SVG not found: $title');
    }
    return cubeCard(title, notation);
  }

  static Widget cubeSvg(String title, String notation, [double? height]) {
    var svg = svgData[title];
    if (svg == null) {
      return Text('Error: SVG not found: $title');
    }

    // array of colors (map every char in notation to color
    var colorsArray = notation.split('').map((e) => colors[e]).toList();
    var out = svg.format(colorsArray);

    return SvgPicture.string(
      out,
      height: height ?? 125,
    );
  }

  static Widget cubeCard(String title, String notation) {
    return Container(
      margin: const EdgeInsets.all(10),
      child: Card(
        child: InkWell(
          splashColor: Colors.blue.withAlpha(30),
          child: Column(
            children: <Widget>[
              Container(
                margin: const EdgeInsets.all(10),
                child: Text(
                  title,
                  style: const TextStyle(fontSize: 20),
                ),
              ),
              cubeSvg(title, notation)
            ],
          ),
        ),
      ),
    );
  }
}
