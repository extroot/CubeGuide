import 'package:flutter/material.dart';
import 'package:in_app_review/in_app_review.dart';
import 'pages/beginners_page.dart';
import 'pages/menu_page.dart';
import 'pages/method_page.dart';
import 'pages/settings_page.dart';
import 'utils/db_helper.dart';
import 'utils/method.dart';
import 'utils/cube_svg.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await DBHelper.initDB();
  await CubeSvg.initCubeSvg();
  runApp(RubiksCubeApp());
}

class RubiksCubeApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Rubik\'s Cube Tutorials',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        fontFamily: 'Lato'
      ),
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  Widget _currentScreen = MenuPage();
  String _currentTitle = 'Menu';
  final InAppReview inAppReview = InAppReview.instance;

  void _navigateTo(String title) async {
    List<Alg> algs = [];
    Method method;
    switch (title) {
      case 'Menu':
        _currentScreen = MenuPage();
        break;
      case 'For Beginners':
        _currentScreen = BeginnersPage();
        break;
      case 'F2L':
        method = await DBHelper.getMethodById(1);
        algs = await DBHelper.getAlgsByMethod(method);
        _currentScreen = TutorialPage(title: 'F2L', algs: algs, method: method);
        break;
      case 'OLL':
        method = await DBHelper.getMethodById(2);
        algs = await DBHelper.getAlgsByMethod(method);
        _currentScreen = TutorialPage(title: 'OLL', algs: algs, method: method);
        break;
      case 'PLL':
        method = await DBHelper.getMethodById(3);
        algs = await DBHelper.getAlgsByMethod(method);
        _currentScreen = TutorialPage(title: 'PLL', algs: algs, method: method);
        break;
      case 'Settings':
        _currentScreen = SettingsPage();
        break;
      default:
        _currentScreen = BeginnersPage();
        break;
    }
    // if (_currentScreen is TutorialPage) {
    //   (_currentScreen as TutorialPage).resetScrollPosition();
    // }
    setState(() {
      _currentTitle = title;
      Navigator.pop(context);
    });
  }

  @override
  Widget build(BuildContext context) {
    final bool expanded = _currentScreen is TutorialPage && _currentTitle == 'F2L';
    return Scaffold(
      appBar: AppBar(
        title: Text(_currentTitle),
      ),
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            const DrawerHeader(
              decoration: BoxDecoration(color: Colors.blue),
              child: Text('Rubik\'s Cube Tutorials',
                  style: TextStyle(color: Colors.white)),
            ),
            ListTile(
              title: Text('Menu'),
              onTap: () => _navigateTo('Menu'),
            ),
            ListTile(
              title: Text('3x3x3 For Beginners'),
              onTap: () => _navigateTo('For Beginners'),
            ),
            ListTile(
              title: Text('3x3x3 Patterns'),
              onTap: () => _navigateTo('For Beginners'),
            ),
            ListTile(
              title: Text('How to rotate the cube'),
              onTap: () => _navigateTo('For Beginners'),
            ),
            ExpansionTile(
                title: Text('3x3x3 CFOP'),
                initiallyExpanded: true,
                children: <Widget>[
                  ListTile(
                    title: Text('F2L'),
                    onTap: () => _navigateTo('F2L'),
                  ),
                  ListTile(
                    title: Text('OLL'),
                    onTap: () => _navigateTo('OLL'),
                  ),
                  ListTile(
                    title: Text('PLL'),
                    onTap: () => _navigateTo('PLL'),
                  ),
                ]),
            ExpansionTile(
                title: Text('One hand 3x3x3'),
                initiallyExpanded: false,
                children: <Widget>[
                  ListTile(
                    title: Text('OH OLL'),
                    onTap: () => _navigateTo('OLL'),
                  ),
                  ListTile(
                    title: Text('OH PLL'),
                    onTap: () => _navigateTo('PLL'),
                  ),
                  ListTile(
                    title: Text('OH COLL'),
                    onTap: () => _navigateTo('COLL'),
                  ),
                ]),
            ExpansionTile(
                title: Text('3x3x3 PRO'),
                initiallyExpanded: false,
                children: <Widget>[]),
            ExpansionTile(
                title: Text('2x2x2'),
                initiallyExpanded: false,
                children: <Widget>[]),
            ExpansionTile(
                title: Text('5x5x5'),
                initiallyExpanded: false,
                children: <Widget>[]),
            ExpansionTile(
                title: Text('MegaMinx'),
                initiallyExpanded: false,
                children: <Widget>[]),
            ExpansionTile(
                title: Text('Pyraminx'),
                initiallyExpanded: false,
                children: <Widget>[]),
            ExpansionTile(
                title: Text('Square-1'),
                initiallyExpanded: false,
                children: <Widget>[]),
            ListTile(
              title: Text('Rate this app'),
              onTap: () async {
                if (await inAppReview.isAvailable()) {
                  inAppReview.requestReview();
                }
              },
            ),
            ListTile(
              title: Text('Settings'),
              onTap: () => _navigateTo('Settings'),
            ),
          ],
        ),
      ),
      body: _currentScreen,
    );
  }
}
