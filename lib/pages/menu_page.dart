import 'package:cube_guide_flutter/utils/cube_svg.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:format/format.dart';

class MenuPage extends StatelessWidget {
  const MenuPage({super.key});

  @override
  Widget build(BuildContext context) {
    return GridView.count(
      crossAxisCount: 2,
      children: <Widget>[
        CubeSvg.cubeCardSolved('3x3x3'),
        CubeSvg.cubeCardSolved('2x2x2'),
        CubeSvg.cubeCardSolved('4x4x4'),
        CubeSvg.cubeCardSolved('5x5x5'),
        CubeSvg.cubeCardSolved('Pyraminx'),
        CubeSvg.cubeCardSolved('Square-1'),
      ],
    );
  }
}
