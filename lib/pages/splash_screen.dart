import 'dart:async';

import 'package:cube_guide/pages/intro_screen.dart';
import 'package:cube_guide/pages/menu_page.dart';
import 'package:cube_guide/widgets/cube_svg.dart';
import 'package:cube_guide/utils/db_helper.dart';
import 'package:cube_guide/utils/models.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'package:cube_guide/utils/constants.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({Key? key}) : super(key: key);

  @override
  _SplashScreenState createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  late bool _isFirstTime;
  final List<Widget> cubeRotations = [
    CubeSvg.cubeSvg("3x3x3", "GGGGGGGGGWWWWWWWWWRRRRRRRRR"),
    CubeSvg.cubeSvg("3x3x3", "GGYGGYGGYWWWWWWGGGRRRRRRRRR"),
    CubeSvg.cubeSvg("3x3x3", "GGBGGBGGBWWWWWWYYYRRRRRRRRR"),
    CubeSvg.cubeSvg("3x3x3", "GGWGGWGGWWWWWWWBBBRRRRRRRRR")
  ];
  var pos = 0.obs;
  late Timer _timer;


  void incrementPos() {
    pos++;
    if (pos >= cubeRotations.length) {
      pos -= cubeRotations.length;
    }
  }

  Future<void> checkIfFirstTime() async {
    final SharedPreferences prefs = await SharedPreferences.getInstance();
    if (prefs.containsKey(prefsSeenKey)) {
      setState(() {
        _isFirstTime = true; // TODO set to false
      });
    } else {
      prefs.setBool(prefsSeenKey, true);
      setState(() {
        _isFirstTime = true;
      });
    }
  }

  Future<void> navigationPage() async {
    MenuEntry _firstMenuEntry = await DBHelper.getMainMenuEntry();
    final Widget _page = MenuPage(menuEntry: _firstMenuEntry);
    if (!_isFirstTime) {
      Get.off<Widget>(
        () => _page,
        transition: Transition.rightToLeftWithFade,
        curve: Curves.easeIn,
        duration: const Duration(milliseconds: 300),
      );
    } else {
      print('First time');
      Get.off<Widget>(() => IntroScreen(page: _page));
    }
  }

  Future<Timer> _loadWidget() async {
    const Duration _duration = Duration(milliseconds: 1000);
    return Timer(_duration, navigationPage);
  }

  @override
  void initState() {
    checkIfFirstTime();
    super.initState();
    _loadWidget();
    _timer = Timer.periodic(const Duration(milliseconds: 300), (Timer t) {
      incrementPos();
    });
  }

  @override
  Widget build(BuildContext context) {
    // run every 300 ms incrementPos to change the cube rotation
    return Container(
      height: Get.height,
      width: Get.width,
      decoration: const BoxDecoration(
        gradient: LinearGradient(
          colors: <Color>[Color(0xFF6B8EEF), Color(0xFF0C2979)],
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
        ),
      ),
      child: Scaffold(
        backgroundColor: Colors.transparent,
        body: Center(
          // Use Obx to update the cube rotation
          // child: CubeSvg.cubeSvg(cubeRotation[pos.value], "3x3x3"),
          child: Obx(() => cubeRotations[pos.value]),
        )
      ),
    );
  }

  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }
}
