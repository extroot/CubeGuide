import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';

class MenuPage extends StatelessWidget {
  const MenuPage({super.key});

  @override
  Widget build(BuildContext context) {
    return GridView.count(
      crossAxisCount: 2,
      children: <Widget>[
        Container(
          margin: EdgeInsets.all(10),
          child: Card(
            child: InkWell(
              splashColor: Colors.blue.withAlpha(30),
              child: Column(
                children: <Widget>[
                  Container(
                    margin: EdgeInsets.all(10),
                    child: Text(
                      '3x3x3',
                      style: TextStyle(fontSize: 20),
                    ),
                  ),
                  SvgPicture.asset(
                    'assets/3x3x3.svg',
                    height: 125,
                  ),
                ],
              ),
            ),
          ),
        ),
        // Repeat the above for each cube size
      ],
    );
  }
// Navigate to the tutorial page for this cube size
}
