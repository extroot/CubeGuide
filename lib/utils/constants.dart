// Shared Preferences Keys

import 'package:introduction_screen/introduction_screen.dart';

const String prefsSeenKey = 'seen';
const String prefsIsGridKey = 'is_grid';
const String prefsNumberOfFormulasKey = 'number_of_formulas';

const String prefsColorWhiteKey = 'color_white';
const String prefsColorYellowKey = 'color_yellow';
const String prefsColorRedKey = 'color_red';
const String prefsColorOrangeKey = 'color_orange';
const String prefsColorGreenKey = 'color_green';
const String prefsColorBlueKey = 'color_blue';
const String prefsColorGreyKey = 'color_grey';
const String prefsFrontSideKey = 'front_side';
const String prefsTopSideKey = 'top_side';

/// Map of cube colors to their opposite sides.
const Map<String, String> oppositeSides = {
  'W': 'Y',
  'Y': 'W',
  'B': 'G',
  'G': 'B',
  'R': 'O',
  'O': 'R',
};

/// 3D vectors representing cube sides used for orientation math.
const Map<String, List<int>> colorVectors = {
  'Y': [0, 1, 0],
  'W': [0, -1, 0],
  'B': [0, 0, 1],
  'G': [0, 0, -1],
  'R': [1, 0, 0],
  'O': [-1, 0, 0],
};
