class Alg {
  final int id;
  final String alg;
  final int method_id;
  final String? custom_alg;
  final bool has_title;

  Alg({required this.id, required this.alg, required this.method_id, required this.custom_alg, required this.has_title});

  static Alg fromMap(Map<String, dynamic> map) {
    return Alg(
      id: map['id'],
      alg: map['alg'],
      method_id: map['method_id'],
      custom_alg: map['custom_alg'],
      has_title: map['has_title'] == 1,
    );
  }
}


class Cube {
  final int id;
  final String title;
  final String solved_state;

  Cube({required this.id, required this.title, required this.solved_state});

  static Cube fromMap(Map<String, dynamic> map) {
    return Cube(
      id: map['id'],
      title: map['title'],
      solved_state: map['solved_state'],
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