import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:flutter_svg/svg.dart';
import 'package:format/format.dart';
import '../utils/cube_svg.dart';
import '../utils/method.dart';
import '../utils/db_helper.dart';

class CubePage extends StatefulWidget {
  final Cube cube;

  CubePage({required this.cube}) : super();

  // init

  @override
  _CubePageState createState() => _CubePageState();
}

class _CubePageState extends State<CubePage> {
  @override
  void initState() {
    super.initState();
  }

  Widget page() {
    return FutureBuilder(
      future: DBHelper.getMethodsByCube(widget.cube),
      builder: (BuildContext context, AsyncSnapshot<List<Method>> snapshot) {
        if (snapshot.hasData) {
          return GridView.count(
            crossAxisCount: 2,
            children: snapshot.data!.map((method) {
              return methodCard(method);
            }).toList(),
          );
        } else {
          return CircularProgressIndicator();
        }
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(widget.cube.title),
        ),
        body: page(),
        bottomNavigationBar: BottomNavigationBar(
          items: <BottomNavigationBarItem>[
            BottomNavigationBarItem(
              icon: CubeSvg.cubeSvg('3x3x3', 'B' * 9 + 'Y' * 9 + 'R' * 9, 35),
              label: '3x3x3',
              backgroundColor: Colors.red,
            ),
            BottomNavigationBarItem(
              icon: CubeSvg.cubeSvg('2x2x2', 'B' * 4 + 'Y' * 4 + 'R' * 4, 35),
              label: '2x2x2',
              backgroundColor: Colors.green,
            ),
            BottomNavigationBarItem(
              icon: CubeSvg.cubeSvg('Square-1', 'B' * 7 + 'Y' * 8 + 'R' * 8, 352),
              label: 'Other',
              backgroundColor: Colors.purple,
            ),
          ],
          currentIndex: 0,
          selectedItemColor: Colors.amber[800],
        ),
    );
  }

  Widget methodCard(Method method) {
    String assetName = "assets/methods/";
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
                  method.prefix,
                  style: const TextStyle(fontSize: 20),
                ),
              ),
              SvgPicture.asset(
                assetName + method.picmode + "/" + method.picmode + "0.svg",
                height: 125,
                placeholderBuilder: (BuildContext context) => Container(
                    padding: const EdgeInsets.all(30.0),
                    child: const CircularProgressIndicator()),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
