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
      builder:
          (BuildContext context, AsyncSnapshot<List<MethodGroup>> snapshot) {
        if (snapshot.hasData) {
          print(snapshot.data![0]);
          print(snapshot.data![0].methods);
          print(snapshot.data![0].methods.length);
          return ListView.builder(
              itemCount: snapshot.data!.length,
              itemBuilder: (context, index) {
                MethodGroup group = snapshot.data![index];
                int counter = 0;
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
                  content: ListView.builder(
                      itemCount: group.methods.length + 1,
                      primary: false,
                      scrollDirection: Axis.vertical,
                      shrinkWrap: true,
                      itemBuilder: (context, index) {
                        if (index == 0) {
                          if (!group.has_description) return Container();
                          return Container(
                            margin: const EdgeInsets.all(10),
                            child: Text(
                              '${cubePrefix}.groups.${group.prefix}.description'
                                  .tr(),
                              style: const TextStyle(fontSize: 16),
                            ),
                          );
                        }
                        return methodCard(
                            group.methods[index - 1],
                            group.prefix,
                            index % 2 == 1
                        );
                      }),
                );
              });
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
    );
  }

  Widget methodCard(Method method, String groupPrefix, bool isOnLeftSide) {
    print(
        "Method Card for ${method.prefix} of ${groupPrefix} with ${isOnLeftSide}");
    String assetName = "assets/methods/";
    String cubePrefix = widget.cube.prefix;

    Widget image = SvgPicture.asset(
      '${assetName}${method.prefix}/${method.prefix}0.svg', // TODO: TEST CASE
      // "${assetName}f2l/f2l0.svg",
      // height: 125,
      width: 120,
      placeholderBuilder: (BuildContext context) => Container(
          padding: const EdgeInsets.all(30.0),
          child: const CircularProgressIndicator()),
    );
    Widget column = Expanded(
      child: Column(children: <Widget>[
        Container(
          margin: const EdgeInsets.all(10),
          child: Text(
            '${cubePrefix}.groups.${groupPrefix}.methods.${method.prefix}.title'
                .tr(),
            style: const TextStyle(fontSize: 20),
          ),
        ),
        if (method.has_description)
          Container(
            margin: const EdgeInsets.all(10),
            child: Text(
              '${cubePrefix}.groups.${groupPrefix}.methods.${method.prefix}.description'
                  .tr(),
              maxLines: 3,
              overflow: TextOverflow.ellipsis,
              style: const TextStyle(fontSize: 16),
            ),
          )
      ]),
    );

    List<Widget> rowChildren = [image, column];

    // if (!isOnLeftSide) {
    //   rowChildren = rowChildren.reversed.toList();
    // }

    return Container(
      margin: const EdgeInsets.all(10),
      child: Card(
        child: InkWell(
          splashColor: Colors.blue.withAlpha(30),
          child: Container(
            padding: const EdgeInsets.all(10),
            child: Row(
              children: rowChildren,
            ),
          ),
        ),
      ),
    );

    // return Container(margin: const EdgeInsets.all(10), child: Row());
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
                  '${cubePrefix}.groups.${groupPrefix}.methods.${method.prefix}.title'
                      .tr(),
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
