import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:format/format.dart';
import '../utils/method.dart';
import '../utils/db_helper.dart';
import '../utils/cube_svg.dart';
import 'cube_page.dart';

class MenuPage extends StatefulWidget {
  // list of Cube objects

  // constructor
  MenuPage({Key? key}) : super(key: key);

  @override
  _MenuPageState createState() => _MenuPageState();
}

class _MenuPageState extends State<MenuPage> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: DBHelper.getCubes(),
      builder: (BuildContext context, AsyncSnapshot<List<Cube>> snapshot) {
        if (snapshot.hasData) {
          return GridView.count(
            crossAxisCount: 2,
            padding: const EdgeInsets.all(10),
            crossAxisSpacing: 5,
            mainAxisSpacing: 10,
            children: snapshot.data!.map((cube) {
              return cubeCard(cube);
            }).toList(),
          );
        } else {
          return const CircularProgressIndicator();
        }
      },
    );
  }

  Widget cubeCard(Cube cube) {
    return Card(
        elevation: 3,
        child: InkWell(
            splashColor: Colors.blue.withAlpha(30),
            child: Column(
              children: <Widget>[
                Container(
                  margin: const EdgeInsets.only(top: 7, bottom: 3),
                  child: Text(
                    '${cube.prefix}.title'.tr(),
                    style: const TextStyle(fontSize: 18),
                  )
                ),
                CubeSvg.cubeSvg(cube.prefix, cube.menu_state, height: 125)
              ],
            ),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => CubePage(cube: cube),
                ),
              );
            }));
  }
}

// method to get data for GridView

// class MenuPage extends StatelessWidget {
//   const MenuPage({super.key});
//
//   @override
//   Widget build(BuildContext context) {
//     return GridView.count(
//       crossAxisCount: 2,
//       children: <Widget>[
//         CubeSvg.cubeCardSolved('3x3x3'),
//         CubeSvg.cubeCardSolved('2x2x2'),
//         CubeSvg.cubeCardSolved('4x4x4'),
//         CubeSvg.cubeCardSolved('5x5x5'),
//         CubeSvg.cubeCardSolved('Pyraminx'),
//         CubeSvg.cubeCardSolved('Square-1'),
//       ],
//     );
//   }
// }
