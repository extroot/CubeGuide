import 'package:cube_guide/pages/settings_page.dart';
import 'package:cube_guide/utils/app_controller.dart';
import 'package:cube_guide/utils/cube_svg.dart';
import 'package:cube_guide/utils/db_helper.dart';
import 'package:cube_guide/utils/models.dart';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:format/format.dart';
import 'package:get/get.dart';
import 'package:sticky_headers/sticky_headers.dart';
import 'method_page.dart';

class MenuPage extends StatefulWidget {
  final MenuEntry menuEntry;

  // constructor
  const MenuPage({super.key, required this.menuEntry});

  // second named constructor without menuEntry

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
    return Scaffold(
      appBar: AppBar(
        title: Text(context.tr('${widget.menuEntry.prefix}.title')),
        actions: <Widget>[
          IconButton(
            icon: const Icon(Icons.settings),
            tooltip: 'Open settings',
            onPressed: () {
              // TODO: Remove (test for the obs on cubeSvg)
              // var c = Get.find<AppController>();
              // if (c.colors['W'] == '#ffffff') {
              //   c.setColor('W', '#000000');
              // } else {
              //   c.setColor('W', '#ffffff');
              // }
              Get.to(() => SettingsPage());
            },
          ),
        ],
      ),
      body: FutureBuilder(
        future: DBHelper.getMenuGroupsByEntryId(widget.menuEntry.id),
        builder: (BuildContext context, AsyncSnapshot<List<MenuGroup>> snapshot) {
          if (snapshot.hasData) {
            return ListView.builder(
              itemCount: snapshot.data!.length + (widget.menuEntry.show_description ? 1 : 0),
              // primary: true,
              // scrollDirection: Axis.vertical,
              // shrinkWrap: true,
              itemBuilder: (context, index) {
                if (widget.menuEntry.show_description && index == 0) {
                  return Container(
                    margin: const EdgeInsets.all(10),
                    child: Text(
                      context.tr('${widget.menuEntry.prefix}.description'),
                      style: const TextStyle(fontSize: 16),
                    ),
                  );
                }
                MenuGroup group = snapshot.data![index - (widget.menuEntry.show_description ? 1 : 0)];
                return menuGroup(group);
              },
            );
          } else {
            return const Center(child: CircularProgressIndicator());
          }
        },
      ),
    );
  }

  Widget menuGroup(group) {
    if (group.show_title) {
      return StickyHeader(
        header: Container(
          height: 50.0,
          color: Colors.blue[700],
          padding: const EdgeInsets.symmetric(horizontal: 16.0),
          alignment: Alignment.centerLeft,
          child: Text(
            context.tr('${widget.menuEntry.prefix}.groups.${group.prefix}.title'),
            style: const TextStyle(color: Colors.white),
          ),
        ),
        content: menuGroupInside(group),
      );
    } else {
      return menuGroupInside(group);
    }
  }

  Widget menuGroupInside(MenuGroup menuGroup) {
    return Column(
      children: <Widget>[
        if (menuGroup.show_description)
          Container(
            margin: const EdgeInsets.all(10),
            child: Text(
              context.tr('${widget.menuEntry.prefix}.groups.${menuGroup.prefix}.description'),
              style: const TextStyle(fontSize: 16),
            ),
          ),
        if (menuGroup.show_grid) menuGroupGrid(menuGroup) else menuGroupList(menuGroup),
      ],
    );
  }

  Widget menuGroupGrid(MenuGroup menuGroup) {
    return GridView.count(
      crossAxisCount: 2,
      primary: false,
      scrollDirection: Axis.vertical,
      shrinkWrap: true,
      children: menuGroup.menu_entries.map((menuEntry) => menuEntryCard(menuEntry, menuGroup)).toList(),
    );
  }

  Widget menuGroupList(MenuGroup menuGroup) {
    return ListView.builder(
      itemCount: menuGroup.menu_entries.length,
      primary: false,
      scrollDirection: Axis.vertical,
      shrinkWrap: true,
      itemBuilder: (context, index) {
        return menuEntryRow(menuGroup.menu_entries[index], menuGroup);
      },
    );
  }

  Widget menuEntryRow(MenuEntry menuEntry, MenuGroup parentGroup) {
    print("Method Card for ${menuEntry.prefix} of ${parentGroup.prefix}");
    String assetName = "assets/methods/";

    Widget image;
    if (menuEntry.menu_state != '') {
      image = CubeSvg.cubeSvg(menuEntry.menu_picmode, menuEntry.menu_state, width: 125);
    } else {
      image = SvgPicture.asset(
        '${assetName}olc/olc0.svg', // TODO: TEST CASE
        // "${assetName}f2l/f2l0.svg",
        // height: 125,
        width: 120,
        placeholderBuilder:
            (BuildContext context) =>
                Container(padding: const EdgeInsets.all(30.0), child: const CircularProgressIndicator()),
      );
    }
    Widget column = Expanded(
      child: Column(
        children: <Widget>[
          Container(
            margin: const EdgeInsets.only(left: 10, right: 10, top: 10, bottom: 5),
            width: double.infinity,
            child: Text(
              context.tr('${menuEntry.prefix}.title'),
              style: const TextStyle(fontSize: 20),
              textAlign: TextAlign.left,
            ),
          ),
          if (menuEntry.show_description)
            Container(
              margin: const EdgeInsets.only(left: 10, right: 10, bottom: 10),
              width: double.infinity,
              child: Text(
                context.tr('${menuEntry.prefix}.description'),
                maxLines: 3,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 16),
              ),
            ),
        ],
      ),
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
          child: Container(padding: const EdgeInsets.all(10), child: Row(children: rowChildren)),
          onTap: () {
            onTapMenuEntry(context, menuEntry, parentGroup);
          },
        ),
      ),
    );
  }

  Widget menuEntryCard(MenuEntry menuEntry, MenuGroup parentGroup) {
    return Card(
      elevation: 3,
      child: InkWell(
        splashColor: Colors.blue.withAlpha(30),
        child: Column(
          children: <Widget>[
            Container(
              margin: const EdgeInsets.only(top: 7, bottom: 3),
              child: Text(context.tr('${menuEntry.prefix}.title'), style: const TextStyle(fontSize: 20)),
            ),
            CubeSvg.cubeSvg(menuEntry.menu_picmode, menuEntry.menu_state, height: 125),
          ],
        ),
        onTap: () {
          onTapMenuEntry(context, menuEntry, parentGroup);
        },
      ),
    );
  }
}

void onTapMenuEntry(context, MenuEntry menuEntry, MenuGroup parentGroup) {
  // Start MethodPage
  if (menuEntry.is_method) {
    Get.to(() => MethodPage(menuEntry: menuEntry));
  } else {
    print("Menu Entry ${menuEntry.prefix} is not a method");
    Get.to(preventDuplicates: false, () => MenuPage(menuEntry: menuEntry));
  }
}
