import 'package:cube_guide/utils/app_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:settings_ui/settings_ui.dart';

class SettingsPage extends StatefulWidget {
  const SettingsPage({Key? key}) : super(key: key);

  @override
  _SettingsPageState createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Settings')),
      body: GetX<AppController>(
        builder: (controller) {
          return SettingsList(
            sections: [
              SettingsSection(
                title: Text('Common2'),
                tiles: <SettingsTile>[
                  SettingsTile.navigation(leading: Icon(Icons.language), title: Text('Language'), value: Text('English')),
                  SettingsTile.switchTile(
                    onToggle: (value) {},
                    initialValue: true,
                    leading: Icon(Icons.format_paint),
                    title: Text('Enable custom theme'),
                  ),
                ],
              ),
              SettingsSection(
                  title: Text('Appearance'),
                  tiles: <SettingsTile>[
                    SettingsTile.switchTile(
                      onToggle: (value) {
                        controller.setGrid(value);
                      },
                      initialValue: controller.isGrid.value,
                      leading: Icon(Icons.apps),
                      title: Text('Grid or list view'),
                    )
                  ]
              )
            ],
          );
        }
      )
    );

    // return Scaffold
    //   appBar: AppBar(
    //     title: const Text('Settings'),
    //   ),
    //   body: ListView(
    //     children: <Widget>[
    //       ListTile(
    //         title: const Text('Language'),
    //         subtitle: const Text('English'),
    //         onTap: () {
    //           // Open language selection dialog
    //         },
    //       ),
    //       ListTile(
    //         title: const Text('Grid?'),
    //         subtitle: const Text('No/Yes'),
    //         onTap: () {
    //           // Open theme selection dialog
    //         },
    //       ),
    //       ListTile(
    //         title: const Text('Show one/2/all formulas'),
    //         subtitle: const Text('Number input'),
    //         onTap: () {
    //           // Open theme selection dialog
    //         },
    //       ),
    //       ListTile(
    //         title: const Text('Top and front colors'),
    //         subtitle: const Text('Somehow color input'),
    //         onTap: () {
    //           // Open theme selection dialog
    //         },
    //       ),
    //       ListTile(
    //         title: const Text('Rate the app'),
    //         onTap: () {
    //           // Open the app store
    //         },
    //       ),
    //       ListTile(
    //         title: const Text('Privacy Policy'),
    //         onTap: () {
    //           // Open the privacy policy page
    //         },
    //       ),
    //       ListTile(
    //         title: const Text('Terms of Service'),
    //         onTap: () {
    //           // Open the terms of service page
    //         },
    //       ),
    //     ],
    //   ),
    // );
  }
}
