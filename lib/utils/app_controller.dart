import 'package:cube_guide/utils/constants.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';

class AppController extends GetxController {
  final box = GetStorage();

  var isGrid = false.obs;
  var numberOfFormulas = 1.obs;
  var frontSide = 'B'.obs;
  var topSide = 'Y'.obs;

  var colors =
      <String, String>{
        'W': '#ffffff',
        'Y': '#fff144',
        'R': '#ea0600',
        'O': '#ffa40d',
        'G': '#07a42e',
        'B': '#0025ff',
        'X': '#9c9c9c',
        'T': '',
        'F': 'none',
      }.obs;

  /// Colors adjusted for the current cube orientation.
  final rotatedColors = <String, String>{}.obs;

  /// Recompute [rotatedColors] according to [frontSide] and [topSide].
  void _updateRotatedColors() {
    final up = colorVectors[topSide.value]!;
    final front = colorVectors[frontSide.value]!;
    final right = _cross(up, front);

    String fromVector(List<int> v) {
      for (final entry in colorVectors.entries) {
        final vec = entry.value;
        if (vec[0] == v[0] && vec[1] == v[1] && vec[2] == v[2]) {
          return entry.key;
        }
      }
      return 'X';
    }

    final map = <String, String>{
      'Y': topSide.value,
      'W': fromVector([-up[0], -up[1], -up[2]]),
      'B': frontSide.value,
      'G': fromVector([-front[0], -front[1], -front[2]]),
      'R': fromVector(right),
      'O': fromVector([-right[0], -right[1], -right[2]]),
    };

    rotatedColors.value = {
      for (final entry in colors.entries)
        entry.key: map.containsKey(entry.key)
            ? colors[map[entry.key]]!
            : entry.value,
    };
  }

  /// Cross product of two 3D vectors with integer values.
  List<int> _cross(List<int> a, List<int> b) {
    return [
      a[1] * b[2] - a[2] * b[1],
      a[2] * b[0] - a[0] * b[2],
      a[0] * b[1] - a[1] * b[0],
    ];
  }

  // Handles persisted settings and computes rotated colors based on
  // user-selected cube orientation.

  @override
  void onInit() {
    super.onInit();
    isGrid.value = box.read(prefsIsGridKey) ?? false;
    numberOfFormulas.value = box.read(prefsNumberOfFormulasKey) ?? 1;
    frontSide.value = box.read(prefsFrontSideKey) ?? 'B';
    topSide.value = box.read(prefsTopSideKey) ?? 'Y';

    colors.value = {
      'W': box.read(prefsColorWhiteKey) ?? '#ffffff',
      'Y': box.read(prefsColorYellowKey) ?? '#fff144',
      'R': box.read(prefsColorRedKey) ?? '#ea0600',
      'O': box.read(prefsColorOrangeKey) ?? '#ffa40d',
      'G': box.read(prefsColorGreenKey) ?? '#07a42e',
      'B': box.read(prefsColorBlueKey) ?? '#0025ff',
      'X': box.read(prefsColorGreyKey) ?? '#9c9c9c',
      'T': '',
      'F': 'none',
    };
    _updateRotatedColors();
  }

  void toggleGrid() {
    isGrid.value = !isGrid.value;
    box.write(prefsIsGridKey, isGrid.value);
  }

  void setGrid(bool value) {
    isGrid.value = value;
    box.write(prefsIsGridKey, isGrid.value);
  }

  void setNumberOfFormulas(int value) {
    numberOfFormulas.value = value;
    box.write(prefsNumberOfFormulasKey, numberOfFormulas.value);
  }

  void setFrontSide(String value) {
    frontSide.value = value;
    box.write(prefsFrontSideKey, value);
    _updateRotatedColors();
  }

  void setTopSide(String value) {
    topSide.value = value;
    box.write(prefsTopSideKey, value);
    _updateRotatedColors();
  }

  void setColor(String key, String value) {
    colors[key] = value;

    switch (key) {
      case 'W':
        box.write(prefsColorWhiteKey, value);
        break;
      case 'Y':
        box.write(prefsColorYellowKey, value);
        break;
      case 'R':
        box.write(prefsColorRedKey, value);
        break;
      case 'O':
        box.write(prefsColorOrangeKey, value);
        break;
      case 'G':
        box.write(prefsColorGreenKey, value);
        break;
      case 'B':
        box.write(prefsColorBlueKey, value);
        break;
      case 'X':
        box.write(prefsColorGreyKey, value);
        break;
    }
    _updateRotatedColors();
  }

  void setColors(Map<String, String> value) {
    value['T'] = '';
    value['F'] = 'none';

    colors.value = value;
    box.write(prefsColorWhiteKey, value['W']);
    box.write(prefsColorYellowKey, value['Y']);
    box.write(prefsColorRedKey, value['R']);
    box.write(prefsColorOrangeKey, value['O']);
    box.write(prefsColorGreenKey, value['G']);
    box.write(prefsColorBlueKey, value['B']);
    box.write(prefsColorGreyKey, value['X']);
    _updateRotatedColors();
  }
}
