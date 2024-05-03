import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'dart:typed_data';
import 'dart:io';
import 'package:flutter/services.dart';

import 'method.dart';

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
      List<int> bytes =
          data.buffer.asUint8List(data.offsetInBytes, data.lengthInBytes);

      // Write and flush the bytes written
      await File(path).writeAsBytes(bytes, flush: true);
    } else {
      print("Opening existing database");
    }

    _database = await openDatabase(
        path,
        version: 1,
    //     onCreate: (db, version) {
    //       db.execute('''
    //       CREATE TABLE tutorials (
    //       id INTEGER PRIMARY KEY AUTOINCREMENT,
    //       imageUrl TEXT,
    //       formula TEXT,
    //       category TEXT
    //     )''');
    // }
    );
  }

  static Future<Cube> getCubeById(int id) async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'cubes',
      where: 'id = ?',
      whereArgs: [id],
    );

    return Cube.fromMap(maps[0]);
  }

  static Future<List<Cube>> getCubes() async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query('cube');

    return List.generate(maps.length, (i) {
      return Cube.fromMap(maps[i]);
    });
  }

  static Future<List<MethodGroup>> getMethodGroups(Cube cube) async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'method_group',
      where: 'cube_id = ?',
      whereArgs: [cube.id],
    );

    List<MethodGroup> groups = [];
    for (var map in maps) {
      groups.add(await MethodGroup.fromMap(map, cube));
    }
    return groups;
  }

  static Future<List<Method>> getMethodsByGroupId(int group_id) async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'method',
      where: 'method_group_id = ?',
      whereArgs: [group_id],
    );

    return List.generate(maps.length, (i) {
      return Method.fromMap(maps[i]);
    });
  }
}
