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
    print("Getting methods for group ${map['id']}");
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
  final String menu_state;
  final int cube_id;
  final int method_group_id;
  final bool has_description;


  Method({required this.id, required this.prefix, required this.count, required this.picmode, required this.menu_state, required this.cube_id, required this.method_group_id, required this.has_description});

  static Future<Method> fromMap(Map<String, dynamic> map) async {

    return Method(
      id: map['id'],
      prefix: map['prefix'],
      count: map['count'],
      picmode: map['picmode'],
      menu_state: map['menu_state'],
      cube_id: map['cube_id'],
      method_group_id: map['method_group_id'],
      has_description: map['has_description'] == 1,
    );
  }
}


class AlgGroup {
  final int id;
  final int my_order;
  final int? category_id;
  final String pic_state;
  final int method_id;

  final List<Alg> algs;

  AlgGroup({required this.id, required this.my_order, required this.category_id, required this.pic_state, required this.method_id, required this.algs});

  static Future<AlgGroup> fromMap(Map<String, dynamic> map) async {
    print("Creating AlgGroup from map: ${map}");
    List<Alg> algs = await DBHelper.getAlgsByGroupId(map['id']);
    return AlgGroup(
      id: map['id'],
      my_order: map['my_order'],
      category_id: map['category_id'],
      pic_state: map['pic_state'],
      method_id: map['method_id'],
      algs: algs,
    );
  }
}

class Alg {
  final int id;
  final int my_order;
  final String text;
  final int alg_group_id;
  final bool is_custom;

  Alg({required this.id, required this.my_order, required this.text, required this.alg_group_id, required this.is_custom});

  static Alg fromMap(Map<String, dynamic> map) {
    return Alg(
      id: map['id'],
      my_order: map['my_order'],
      text: map['text'],
      alg_group_id: map['alg_group_id'],
      is_custom: map['is_custom'] == 1,
    );
  }
}