import 'package:cube_guide/utils/app_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:settings_ui/settings_ui.dart';
import 'package:flutter_colorpicker/flutter_colorpicker.dart';
import 'package:cube_guide/utils/constants.dart';

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
                title: const Text('Appearance'),
                tiles: <SettingsTile>[
                  SettingsTile.switchTile(
                    onToggle: (value) {
                      controller.setGrid(value);
                    },
                    initialValue: controller.isGrid.value,
                    leading: const Icon(Icons.apps),
                    title: const Text('Grid or list view'),
                  ),
                ],
              ),
              SettingsSection(
                title: const Text('Cube colors'),
                tiles: <SettingsTile>[
                  _colorTile('W', controller),
                  _colorTile('Y', controller),
                  _colorTile('R', controller),
                  _colorTile('O', controller),
                  _colorTile('G', controller),
                  _colorTile('B', controller),
                ],
              ),
              SettingsSection(
                title: const Text('Cube orientation'),
                tiles: <SettingsTile>[
                  SettingsTile.navigation(
                    leading: const Icon(Icons.filter_center_focus),
                    title: const Text('Front side'),
                    valueWidget: Container(
                      width: 24,
                      height: 24,
                      decoration: BoxDecoration(
                        color: _hexToColor(
                          controller.colors[controller.frontSide.value]!,
                        ),
                        border: Border.all(color: Colors.black26),
                      ),
                    ),
                    onPressed: (context) {
                      _showSidePicker(context, true, controller);
                    },
                  ),
                  SettingsTile.navigation(
                    leading: const Icon(Icons.height),
                    title: const Text('Top side'),
                    valueWidget: Container(
                      width: 24,
                      height: 24,
                      decoration: BoxDecoration(
                        color: _hexToColor(
                          controller.colors[controller.topSide.value]!,
                        ),
                        border: Border.all(color: Colors.black26),
                      ),
                    ),
                    onPressed: (context) {
                      _showSidePicker(context, false, controller);
                    },
                  ),
                ],
              )
            ],
          );
        }
      )
    );
  }

  SettingsTile _colorTile(String key, AppController controller) {
    return SettingsTile.navigation(
      leading: Container(
        width: 24,
        height: 24,
        decoration: BoxDecoration(
          color: _hexToColor(controller.colors[key]!),
          border: Border.all(color: Colors.black26),
        ),
      ),
      title: Text('$key color'),
      onPressed: (context) {
        _showColorPicker(context, key, controller);
      },
    );
  }

  void _showColorPicker(
      BuildContext context, String key, AppController controller) {
    Color startColor = _hexToColor(controller.colors[key]!);
    Color pickedColor = startColor;
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text('Select $key color'),
          content: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                BlockPicker(
                  pickerColor: startColor,
                  onColorChanged: (color) {
                    pickedColor = color;
                  },
                ),
                const SizedBox(height: 10),
                ElevatedButton(
                  onPressed: () async {
                    await showDialog(
                      context: context,
                      builder: (context) {
                        return AlertDialog(
                          content: ColorPicker(
                            pickerColor: pickedColor,
                            onColorChanged: (color) {
                              pickedColor = color;
                            },
                          ),
                          actions: [
                            TextButton(
                              onPressed: () => Navigator.of(context).pop(),
                              child: const Text('Done'),
                            ),
                          ],
                        );
                      },
                    );
                    setState(() {});
                  },
                  child: const Text('Custom color'),
                ),
              ],
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                String value =
                    '#${pickedColor.value.toRadixString(16).substring(2)}';
                controller.setColor(key, value);
                Navigator.of(context).pop();
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }

  void _showSidePicker(
      BuildContext context, bool isFront, AppController controller) {
    String selected =
        isFront ? controller.frontSide.value : controller.topSide.value;
    final other = isFront ? controller.topSide.value : controller.frontSide.value;
    final banned = {other, oppositeSides[other]};
    final choices =
        ['W', 'Y', 'R', 'O', 'G', 'B'].where((c) => !banned.contains(c)).toList();
    showDialog(
      context: context,
      builder: (context) {
        return StatefulBuilder(
          builder: (context, setState) {
            return AlertDialog(
              title: Text(isFront ? 'Select front side' : 'Select top side'),
              content: Column(
                mainAxisSize: MainAxisSize.min,
                children: choices
                    .map(
                      (side) => RadioListTile<String>(
                        value: side,
                        groupValue: selected,
                        title: Row(
                          children: [
                            Container(
                              width: 20,
                              height: 20,
                              decoration: BoxDecoration(
                                color: _hexToColor(controller.colors[side]!),
                                shape: BoxShape.circle,
                                border: Border.all(color: Colors.black26),
                              ),
                            ),
                            const SizedBox(width: 8),
                            Text(side),
                          ],
                        ),
                        onChanged: (value) {
                          setState(() {
                            selected = value!;
                          });
                        },
                      ),
                    )
                    .toList(),
              ),
              actions: [
                TextButton(
                  onPressed: () => Navigator.of(context).pop(),
                  child: const Text('Cancel'),
                ),
                TextButton(
                  onPressed: () {
                    if (isFront) {
                      controller.setFrontSide(selected);
                    } else {
                      controller.setTopSide(selected);
                    }
                    Navigator.of(context).pop();
                  },
                  child: const Text('OK'),
                ),
              ],
            );
          },
        );
      },
    );
  }

  Color _hexToColor(String hex) {
    hex = hex.replaceFirst('#', '');
    return Color(int.parse('ff$hex', radix: 16));
  }
}
