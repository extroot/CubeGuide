import 'package:cube_guide_flutter/utils/db_helper.dart';

class Cube {
  final int id;
  final String prefix;
  final String menu_state;
  final bool is_wca;

  Cube({required this.id, required this.prefix, required this.menu_state, required this.is_wca});

  static Cube fromMap(Map<String, dynamic> map) {
    return Cube(
      id: map['id'],
      prefix: map['prefix'],
      menu_state: map['menu_state'],
      is_wca: map['is_wca'] == 1,
    );
  }
}


class MethodGroup {
  final int id;
  final String prefix;
  final bool has_description;
  final int cube_id;
  final Cube cube;

  final List<Method> methods;

  MethodGroup({required this.id, required this.prefix, required this.has_description, required this.cube_id, required this.cube, required this.methods});

  static Future<MethodGroup> fromMap(Map<String, dynamic> map, Cube cube) async {
    List<Method> methods = await DBHelper.getMethodsByGroupId(map['id']);
    return MethodGroup(
      id: map['id'],
      prefix: map['prefix'],
      has_description: map['has_description'] == 1,
      cube_id: map['cube_id'],
      cube: cube,
      methods: methods,
    );
  }

}


class Method {
  final int id;
  final String prefix;
  final int count;
  final String picmode;
  final int cube_id;
  final int method_group_id;
  final bool has_description;

  Method({required this.id, required this.prefix, required this.count, required this.picmode, required this.cube_id, required this.method_group_id, required this.has_description});

  static Method fromMap(Map<String, dynamic> map) {
    return Method(
      id: map['id'],
      prefix: map['prefix'],
      count: map['count'],
      picmode: map['picmode'],
      cube_id: map['cube_id'],
      method_group_id: map['method_group_id'],
      has_description: map['has_description'] == 1,
    );
  }
}