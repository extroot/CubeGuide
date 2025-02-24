import 'package:flutter/material.dart';
import 'package:in_app_review/in_app_review.dart';
import 'pages/beginners_page.dart';
import 'pages/menu_page.dart';
import 'pages/method_page.dart';
import 'pages/settings_page.dart';
import 'utils/db_helper.dart';
import 'utils/models.dart';
import 'utils/cube_svg.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:easy_localization/easy_localization.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await EasyLocalization.ensureInitialized();
  await DBHelper.initDB();
  await CubeSvg.initCubeSvg();
  runApp(EasyLocalization(
      supportedLocales: const [Locale('en', '')],
      useOnlyLangCode: true,
      path: 'assets/translations', // <-- change the path of the translation files
      fallbackLocale: const Locale('en', ''),
      child: RubiksCubeApp()
  ),);
}

class RubiksCubeApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Rubik\'s Cube Tutorials',
      localizationsDelegates: context.localizationDelegates,
      supportedLocales: context.supportedLocales,
      locale: context.locale,
      theme: ThemeData(
        primarySwatch: Colors.blue,
        fontFamily: 'Lato'
      ),
      home: FutureBuilder(
          future: DBHelper.getMainMenuEntry(),
          builder: (BuildContext context, AsyncSnapshot<MenuEntry> snapshot) {
            if (snapshot.hasData) {
              MenuEntry mainMenuEntry = snapshot.data!;
              return MenuPage(menuEntry: mainMenuEntry);
            } else {
              return const Center(
                  child: CircularProgressIndicator()
              );
            }})

    );
  }
}
