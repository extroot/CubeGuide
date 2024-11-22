import 'package:cube_guide_flutter/utils/db_helper.dart';

class MenuEntry {
  final int id;
  final String prefix;
  final String picmode;
  final String menu_state;
  final bool has_description;
  final int? method_id;

  MenuEntry(
      {required this.id,
      required this.prefix,
      required this.picmode,
      required this.menu_state,
      required this.has_description,
      required this.method_id
      });

  static Future<MenuEntry> fromMap(Map<String, dynamic> map) async {
    return MenuEntry(
      id: map['id'],
      menu_state: map['menu_state'],
      prefix: map['prefix'],
      picmode: map['picmode'],
      has_description: map['has_description'] == 1,
      method_id: map['method_id'],
    );
  }
}

class MenuGroup {
  final int id;
  final String prefix;
  final bool has_description;
  final bool show_title;
  final bool show_grid;
  final List<MenuEntry> menu_entries;

  MenuGroup({
    required this.id,
    required this.prefix,
    required this.has_description,
    required this.menu_entries,
    required this.show_title,
    required this.show_grid,
  });

  static Future<MenuGroup> fromMap(Map<String, dynamic> map) async {
    List<MenuEntry> menuEntries = await DBHelper.getMenuEntriesByGroupId(map['id']);
    return MenuGroup(
      id: map['id'],
      prefix: map['prefix'],
      has_description: map['has_description'] == 1,
      menu_entries: menuEntries,
      show_title: map['show_title'] == 1,
      show_grid: map['show_grid'] == 1,
    );
  }
}

class Method {
  final int id;
  final String prefix;
  final String picmode;
  final List<AlgGroup> alg_groups;

  Method(
      {required this.id,
      required this.prefix,
      required this.picmode,
      required this.alg_groups});

  static Future<Method> fromMap(Map<String, dynamic> map) async {
    List<AlgGroup> algGroups = await DBHelper.getAlgGroupsByMethodId(map['id']);
    return Method(
      id: map['id'],
      prefix: map['prefix'],
      picmode: map['picmode'],
      alg_groups: algGroups,
    );
  }
}

class AlgGroup {
  final int id;
  final int my_order;
  final int? category_id;
  final String pic_state;
  final int method_id;
  final String? picmode;
  final List<Alg> algs;

  AlgGroup(
      {required this.id, required this.my_order, required this.pic_state, required this.method_id, required this.algs})
      : category_id = null,
        picmode = null;

  static Future<AlgGroup> fromMap(Map<String, dynamic> map) async {
    List<Alg> algs = await DBHelper.getAlgsByGroupId(map['id']);
    return AlgGroup(
      id: map['id'],
      my_order: map['my_order'],
      // category_id: map['category_id'],
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

  Alg(
      {required this.id,
      required this.my_order,
      required this.text,
      required this.alg_group_id,
      required this.is_custom});

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
