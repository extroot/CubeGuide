class Alg {
  final int id;
  final String alg;
  final int mode_id;
  final String? custom_alg;
  final bool has_title;

  Alg({required this.id, required this.alg, required this.mode_id, required this.custom_alg, required this.has_title});

  static Alg fromMap(Map<String, dynamic> map) {
    return Alg(
      id: map['id'],
      alg: map['alg'],
      mode_id: map['mode_id'],
      custom_alg: map['custom_alg'],
      has_title: map['has_title'] == 1,
    );
  }
}

class Method {
  final int id;
  final String prefix;
  final int count;
  final String picmode;

  Method({required this.id, required this.prefix, required this.count, required this.picmode});

  static Method fromMap(Map<String, dynamic> map) {
    return Method(
      id: map['id'],
      prefix: map['prefix'],
      count: map['count'],
      picmode: map['picmode'],
    );
  }
}