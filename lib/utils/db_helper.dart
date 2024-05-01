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

  static Future<List<Method>> getMethodsByCube(Cube cube) async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'methods',
      where: 'cube_id = ?',
      whereArgs: [cube.id],
    );

    return List.generate(maps.length, (i) {
      return Method.fromMap(maps[i]);
    });
  }

  static Future<List<Cube>> getCubes() async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query('cubes');

    return List.generate(maps.length, (i) {
      return Cube.fromMap(maps[i]);
    });
  }

  static Future<Method> getMethodById(int id) async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'methods',
      where: 'id = ?',
      whereArgs: [id],
    );

    return Method.fromMap(maps[0]);
  }

  static Future<List<Alg>> getAlgsByMethod(Method method) async {
    return getAlgs(method.id);
  }

  static Future<List<Alg>> getAlgs(int method_id) async {
    final db = await database;
    final List<Map<String, dynamic>> maps = await db.query(
      'algs',
      where: 'method_id = ?',
      whereArgs: [method_id],
    );

    return List.generate(maps.length, (i) {
      return Alg.fromMap(maps[i]);
    });
  }

  static Future<void> updateMethodAlg(int id, String newFormula) async {
    final db = await database;
    await db.update(
      'algs',
      {'alg': newFormula},
      where: 'id = ?',
      whereArgs: [id],
    );
  }
}
