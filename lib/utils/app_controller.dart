import 'package:cube_guide/utils/constants.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';

class AppController extends GetxController {
  final box = GetStorage();

  var isGrid = false.obs;
  var numberOfFormulas = 1.obs;

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

  // TODO: Color shift by choosing top and front sides of the cube

  @override
  void onInit() {
    super.onInit();
    isGrid.value = box.read(prefsIsGridKey) ?? false;
    numberOfFormulas.value = box.read(prefsNumberOfFormulasKey) ?? 1;

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
  }
}
