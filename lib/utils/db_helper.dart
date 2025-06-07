import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'dart:io';
import 'package:flutter/services.dart';

import 'models.dart';

class DBHelper {
  static Database? _database;

  static Future<Database> get database async {
    if (_database == null) {
      await initDB();
    }
    return _database!;
  }

  static Future<void> initDB() async {
    final databasePath = await getDatabasesPath();
    final path = join(databasePath, 'rubik_cube.db');

    var exists = await databaseExists(path);

    if (!exists || true) {
      // Should happen only the first time you launch your application
      print("Creating new copy from asset");

      // Make sure the parent directory exists
      try {
        await Directory(dirname(path)).create(recursive: true);
      } catch (_) {}

      // Copy from asset
      ByteData data = await rootBundle.load(url.join("assets", "test.db"));
      List<int> bytes = data.buffer.asUint8List(data.offsetInBytes, data.lengthInBytes);

      // Write and flush the bytes written
      await File(path).writeAsBytes(bytes, flush: true);
    } else {
      // Copy all user created data to the new database
      print("Opening existing database");
    }

    _database = await openDatabase(
      path,
      version: 1,
    );
  }

  static Future<MenuEntry> getMainMenuEntry() async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'menu_entry',
      where: 'id = 1', // TODO: FIX IN PROD TO 1
    );
    MenuEntry entry = await MenuEntry.fromMap(maps[0]);
    return entry;
  }

  // getMenuGroupsByEntryId
  static Future<List<MenuGroup>> getMenuGroupsByEntryId(int entry_id) async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'menu_group',
      where: 'parent_menu_entry = ?',
      whereArgs: [entry_id],
    );

    List<MenuGroup> groups = [];
    for (var map in maps) {
      MenuGroup group = await MenuGroup.fromMap(map);
      groups.add(group);
    }
    return groups;
  }

  static Future<List<MenuEntry>> getMenuEntriesByGroupId(int group_id) async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'menu_entry',
      where: 'parent_menu_group = ?',
      whereArgs: [group_id],
    );

    List<MenuEntry> entries = [];
    for (var map in maps) {
      MenuEntry entry = await MenuEntry.fromMap(map);
      entries.add(entry);
    }
    return entries;
  }

  static Future<List<Item>> getItemsByEntryId(int entry_id) async {
    final db = await database;
    print("Getting items for entry_id: $entry_id");

    final List<Map<String, dynamic>> maps = await db.query(
      'item',
      where: 'menu_entry_id = ?',
      whereArgs: [entry_id],
      orderBy: 'my_order',
    );
    print("Got ${maps.length} items");

    List<Item> items = [];
    for (var map in maps) {
      Item it = await Item.fromMap(map);
      print("Got item from db: ${it.type}");
      items.add(it);
    }
    print("Returning ${items.length} items");
    return items;
  }

  static Future<List<Alg>> getAlgsByItemId(int item_id) async {
    final db = await database;
    print("Getting algs for item_id: $item_id");
    final List<Map<String, dynamic>> maps = await db.query(
      'alg',
      where: 'alg_group_id = ?',
      whereArgs: [item_id],
    );

    List<Alg> algs = [];
    for (var map in maps) {
      algs.add(Alg.fromMap(map));
    }
    print("Returning ${algs.length} algs");
    return algs;
  }

  // Save Item to DB
  static Future<void> saveItem(Item item) async {
    final db = await database;
    await db.insert(
      'item',
      item.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  static Future<void> updateSelectedAlgItem(Item item, int newSelectedAlgOrder) async {
    final db = await database;
    await db.update(
      'item',
      {'selected_alg_order': newSelectedAlgOrder},
      where: 'id = ?',
      whereArgs: [item.id],
    );
    item.selected_alg_order = newSelectedAlgOrder;
  }
}
