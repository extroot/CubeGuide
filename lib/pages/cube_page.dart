import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:flutter_svg/svg.dart';
import 'package:format/format.dart';
import 'package:sticky_headers/sticky_headers.dart';
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
    String cubePrefix = widget.cube.prefix;
    return FutureBuilder(
      future: DBHelper.getMethodGroups(widget.cube),
      builder: (BuildContext context, AsyncSnapshot<List<MethodGroup>> snapshot) {
        if (snapshot.hasData) {
          return ListView.builder(
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                MethodGroup group = snapshot.data![index];
                return StickyHeader(
                  header: Container(
                    height: 50.0,
                    color: Colors.blue[700],
                    padding: EdgeInsets.symmetric(horizontal: 16.0),
                    alignment: Alignment.centerLeft,
                    child: Text(
                      '${cubePrefix}.groups.${group.prefix}.title'.tr(),
                      style: const TextStyle(color: Colors.white),
                    ),
                  ),
                  content: GridView.count(
                    primary: false,
                    crossAxisCount: 2,
                    shrinkWrap: true,
                    children: group.methods.map((method) {
                      return methodCard(method, group.prefix);
                    }).toList(),
                  ),
                );
              });

          // return GridView.count(
          //   crossAxisCount: 2,
          //   children: snapshot.data!.map((method) {
          //     return methodCard(method);
          //   }).toList(),
          // );
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
        title: Text(widget.cube.prefix),
      ),
      body: page(),
      // bottomNavigationBar: BottomNavigationBar(
      //   items: <BottomNavigationBarItem>[
      //     BottomNavigationBarItem(
      //       icon: CubeSvg.cubeSvg('3x3x3', 'B' * 9 + 'Y' * 9 + 'R' * 9, 35),
      //       label: '3x3x3',
      //       backgroundColor: Colors.red,
      //     ),
      //     BottomNavigationBarItem(
      //       icon: CubeSvg.cubeSvg('2x2x2', 'B' * 4 + 'Y' * 4 + 'R' * 4, 35),
      //       label: '2x2x2',
      //       backgroundColor: Colors.green,
      //     ),
      //     BottomNavigationBarItem(
      //       icon: CubeSvg.cubeSvg('Square-1', 'B' * 7 + 'Y' * 8 + 'R' * 8, 35),
      //       label: 'Other',
      //       backgroundColor: Colors.purple,
      //     ),
      //   ],
      //   currentIndex: 0,
      //   selectedItemColor: Colors.amber[800],
      // ),
    );
  }

  Widget methodCard(Method method, String groupPrefix) {
    String assetName = "assets/methods/";
    String cubePrefix = widget.cube.prefix;
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
                  '${cubePrefix}.groups.${groupPrefix}.methods.${method.prefix}.title'.tr(),
                  style: const TextStyle(fontSize: 20),
                ),
              ),
              SvgPicture.asset(
                '${assetName}f2l/f2l0.svg', // TODO: TEST CASE
                // assetName + method.picmode + "/" + method.picmode + "0.svg",
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
