import 'package:cube_guide/utils/db_helper.dart';

class MenuEntry {
  final int id;
  final String prefix;
  final String menu_picmode;
  final String menu_state;
  final bool show_description;
  final bool is_method;

  MenuEntry(
      {required this.id,
      required this.prefix,
      required this.menu_picmode,
      required this.menu_state,
      required this.show_description,
      required this.is_method});

  static Future<MenuEntry> fromMap(Map<String, dynamic> map) async {
    return MenuEntry(
      id: map['id'],
      menu_state: map['menu_state'],
      prefix: map['prefix'],
      menu_picmode: map['menu_picmode'],
      show_description: map['show_description'] == 1,
      is_method: map['is_method'] == 1,
    );
  }
}

class MenuGroup {
  final int id;
  final String prefix;
  final bool show_description;
  final bool show_title;
  final bool show_grid;
  final List<MenuEntry> menu_entries;

  MenuGroup({
    required this.id,
    required this.prefix,
    required this.show_description,
    required this.menu_entries,
    required this.show_title,
    required this.show_grid,
  });

  static Future<MenuGroup> fromMap(Map<String, dynamic> map) async {
    List<MenuEntry> menuEntries = await DBHelper.getMenuEntriesByGroupId(map['id']);
    return MenuGroup(
      id: map['id'],
      prefix: map['prefix'],
      show_description: map['show_description'] == 1,
      menu_entries: menuEntries,
      show_title: map['show_title'] == 1,
      show_grid: map['show_grid'] == 1,
    );
  }
}

class Item {
  final int id;
  final int my_order;
  final String pic_state;
  final String picmode;
  final String? prefix;
  final int menu_entry_id;
  final bool has_title;
  final String type;
  final List<Alg> algs;

  Item({
    required this.id,
    required this.my_order,
    required this.pic_state,
    required this.picmode,
    required this.menu_entry_id,
    required this.has_title,
    required this.type,
    required this.prefix,
    required this.algs
  });

  static Future<Item> fromMap(Map<String, dynamic> map) async {
    List<Alg> algs = await DBHelper.getAlgsByItemId(map['id']);
    print("Got ${algs.length} algs for item ${map['id']}");
    print("map: $map");
    return Item(
      id: map['id'],
      my_order: map['my_order'],
      pic_state: map['pic_state'],
      picmode: map['picmode'],
      menu_entry_id: map['menu_entry_id'],
      has_title: map['has_title'] == 1,
      type: map['type'],
      prefix: map['prefix'],
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
