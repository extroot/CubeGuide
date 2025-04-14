import 'package:cube_guide/utils/app_controller.dart';
import 'package:cube_guide/utils/cube_svg.dart';
import 'package:cube_guide/utils/db_helper.dart';
import 'package:cube_guide/utils/models.dart';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';


class MethodPage extends StatefulWidget {
  final MenuEntry menuEntry;

  const MethodPage({Key? key, required this.menuEntry}) : super(key: key);

  @override
  _MethodPageState createState() => _MethodPageState();
}

class _MethodPageState extends State<MethodPage> {
  final ScrollController _scrollController = ScrollController(initialScrollOffset: 0.0);
  final bool showSingleFormula = true; // TODO: settings
  late final bool isTextMethod;

  @override
  void initState() {
    setState(() {
      isTextMethod = widget.menuEntry.is_method;
    });
    super.initState();
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  void resetScrollPosition() {
    if (_scrollController.hasClients) {
      _scrollController.animateTo(0.0, duration: const Duration(milliseconds: 500), curve: Curves.easeOut);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            title: Text(context.tr("${widget.menuEntry.prefix}.title"))
        ),
        body: GetX<AppController>(
          builder: (controller) {
            if (controller.isGrid.value) return Text("Grid");
            return _list();
          }
        )
    );
  }

  Widget _grid() {
    return Text("Grid");
  }

  Widget _list() {
    return FutureBuilder(
      future: DBHelper.getItemsByEntryId(widget.menuEntry.id),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text('Error: ${snapshot.error}'));
        } else if (snapshot.hasData) {
          return ListView.builder(
            controller: _scrollController,
            itemCount: snapshot.data!.length,
            itemBuilder: (context, index) {
              Item item = snapshot.data![index];
              print("Item ${index} type: ${item.type}");

              if (item.type == 'formula') return formulaRow(item);
              if (item.type == 'text') return textRow(item);
              if (item.type == 'header') return headerRow(item);
              if (item.type == 'single_cube') return singleCubeRow(item);
              if (item.type == 'single_cube_alg') return formulaColumn(item);
              if (item.type == 'double_cube_alg') return Text("double_cube_alg");
              print("Wrong type of item: " + item.type);
              return Text("Wrong type of item: " + item.type);
            },
          );
        } else {
          return const Center(child: CircularProgressIndicator());
        }
      },
    );
  }

  Widget singleCubeRow(Item item) {
    return Container(
      margin: const EdgeInsets.all(10),
      child: CubeSvg.cubeSvg(item.picmode, item.pic_state, height: 125),
    );
  }

  Widget headerRow(Item item) {
    int item_s = int.parse(item.pic_state);
    double size = 18 + 5 - item_s.toDouble();
    return Container(
      margin: const EdgeInsets.all(10),
      child: Center(
        child: Text(context.tr("${widget.menuEntry.prefix}.items.${item.prefix}"), style: TextStyle(fontSize: size)),
      ),
    );
  }

  Widget textRow(Item item) {
    return Container(
      margin: const EdgeInsets.all(10),
      child: Text(context.tr("${widget.menuEntry.prefix}.items.${item.prefix}"), style: const TextStyle(fontSize: 18)),
    );
  }

  Widget formulaColumn(Item item) {
    print("Vertical formula for item: ${item.type} picmode ${item.picmode} pic_state ${item.pic_state}");
    return Container(
      margin: const EdgeInsets.only(left: 10, top: 15),
      child: Column(
        children: <Widget>[
          Container(
            margin: const EdgeInsets.only(right: 10),
            child: CubeSvg.cubeSvg(item.picmode, item.pic_state, height: 125),
          ),
          Container(child: Container(margin: const EdgeInsets.all(5), child: Text(getAlg(item.algs)))),
        ],
      ),
    );
  }

  Widget formulaRow(Item item) {
    return Container(
      margin: const EdgeInsets.only(left: 10, top: 15),
      child: InkWell(
        child: Row(
          children: <Widget>[
            Container(
              margin: const EdgeInsets.only(right: 10),
              child: CubeSvg.cubeSvg(item.picmode, item.pic_state, height: 125),
            ),
            Expanded(child: Container(margin: const EdgeInsets.all(5), child: Text(getAlg(item.algs)))),
          ],
        ),
        onTap: () => _dialogBuilder(context, item),
      ),
    );
  }

  Future<void> _dialogBuilder(BuildContext context, Item item) {
    return showDialog<void>(
      context: context,
      builder: (BuildContext context) {
        return Dialog(
          child: Container(
            margin: const EdgeInsets.all(10),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                // Top row, like appbar with title and small close button at the right.
                // Title should be centered.
                Stack(
                  children: [
                    Column(
                      children: [
                        Center(
                          child: Text(
                            "${widget.menuEntry.prefix}${item.my_order.toString()}",
                            textAlign: TextAlign.center,
                            style: TextStyle(fontSize: 20),
                          ),
                        ),
                      ],
                    ),
                    Positioned(
                      right: 0,
                      top: 0,
                      child: IconButton(icon: const Icon(Icons.close), onPressed: () => Get.back()),
                    ),
                  ],
                ),
                const Divider(),
                Container(
                  margin: const EdgeInsets.all(10),
                  child: CubeSvg.cubeSvg(item.picmode, item.pic_state, height: 200),
                ),
                for (Alg alg in item.algs) Container(margin: const EdgeInsets.all(5), child: Text(alg.text)),
              ],
            ),
          ),
        );
      },
    );
  }

  String getAlg(List<Alg> algs) {
    if (showSingleFormula && algs.isNotEmpty) {
      return algs[0].text;
    }

    String alg = "";
    for (int i = 0; i < algs.length; i++) {
      alg += algs[i].text;
      if (i < algs.length - 1) {
        alg += "\n";
      }
    }

    return alg;
  }
}
