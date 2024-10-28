import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'dart:typed_data';
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
    print("Getting method groups for cube ${cube.id} ${cube.prefix}");
    final List<Map<String, dynamic>> maps = await db.query(
      'method_group',
      where: 'cube_id = ?',
      whereArgs: [cube.id],
    );
    print("Got ${maps.length} groups for cube ${cube.id} ${cube.prefix}");

    List<MethodGroup> groups = [];
    for (var map in maps) {
      groups.add(await MethodGroup.fromMap(map, cube));
    }
    print("Returning ${groups.length} groups for cube ${cube.id} ${cube.prefix}");
    return groups;
  }

  static Future<List<Method>> getMethodsByGroupId(int group_id) async {
    final db = await database;
    print("Getting methods for group ${group_id}");
    final List<Map<String, dynamic>> maps = await db.query(
      'method',
      where: 'method_group_id = ?',
      whereArgs: [group_id],
    );
    print("Got ${maps.length} methods for group ${group_id}");

    List<Method> methods = [];
    for (var map in maps) {
      methods.add(await Method.fromMap(map));
    }
    return methods;
    // return List.generate(maps.length, (i) {
    //   return Method.fromMap(maps[i]);
    // });
  }

  static Future<List<AlgGroup>> getAlgGroupsByMethodId(int method_id) async {
    final db = await database;
    print("Getting alg groups for method ${method_id}");

    final List<Map<String, dynamic>> maps = await db.query(
      'alg_group',
      where: 'method_id = ?',
      whereArgs: [method_id],
      orderBy: 'my_order',
    );
    print("Got ${maps.length} alg groups for method ${method_id}");

    List<AlgGroup> groups = [];
    for (var map in maps) {
      print("Adding alg group ${map['id']}");
      AlgGroup group = await AlgGroup.fromMap(map);
      groups.add(group);
      print("Added alg group ${map['id']}");
    }
    return groups;
  }

  static Future<List<Alg>> getAlgsByGroupId(int group_id) async {
    final db = await database;
    print("Getting algs for group ${group_id}");
    final List<Map<String, dynamic>> maps = await db.query(
      'alg',
      where: 'alg_group_id = ?',
      whereArgs: [group_id],
    );
    print("Got ${maps.length} algs for group ${group_id}");

    List<Alg> algs = [];
    for (var map in maps) {
      algs.add(Alg.fromMap(map));
    }
    return algs;
  }
}
