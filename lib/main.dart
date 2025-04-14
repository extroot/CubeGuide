import 'package:cube_guide/pages/splash_screen.dart';
import 'package:cube_guide/utils/app_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:in_app_review/in_app_review.dart';
import 'utils/db_helper.dart';
import 'utils/cube_svg.dart';
import 'package:easy_localization/easy_localization.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await EasyLocalization.ensureInitialized();
  await DBHelper.initDB();

  final AppController c = Get.put(AppController());
  await CubeSvg.initCubeSvg(c);

  runApp(EasyLocalization(
      supportedLocales: const [Locale('en', '')],
      useOnlyLangCode: true,
      path: 'assets/translations', // <-- change the path of the translation files
      fallbackLocale: const Locale('en', ''),
      child: RubiksCubeApp()
  ),);
}

class RubiksCubeApp extends StatelessWidget {
  const RubiksCubeApp({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'Rubik\'s Cube Tutorials',
      localizationsDelegates: context.localizationDelegates,
      supportedLocales: context.supportedLocales,
      locale: context.locale,
      theme: ThemeData(
        primarySwatch: Colors.blue,
        fontFamily: 'Lato'
      ),
      home: SplashScreen(),

    );
  }
}
