import 'package:cube_guide/pages/settings_page.dart';
import 'package:cube_guide/utils/app_controller.dart';
import 'package:cube_guide/widgets/cards.dart';
import 'package:cube_guide/widgets/cube_svg.dart';
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
  List<Item> items = [];


  void initItems() async {
    print("Init items for entry ${widget.menuEntry.id}");
    List<Item> it = await DBHelper.getItemsByEntryId(widget.menuEntry.id);
    setState(() {
      items = it;
    });
  }

  @override
  void initState() {
    // setState(() async {
    //   items = await DBHelper.getItemsByEntryId(widget.menuEntry.id);
    // });
    initItems();
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
    return GetX<AppController>(
      builder: (controller) {
        return Scaffold(
          appBar: AppBar(
            title: Text(context.tr("${widget.menuEntry.prefix}.small_title")),
            actions: <Widget>[
              IconButton(
                icon: const Icon(Icons.settings),
                tooltip: 'Open settings',
                onPressed: () {
                  Get.to(() => SettingsPage());
                },
              ),
              IconButton(
                icon: controller.isGrid.value ? const Icon(Icons.table_rows) : const Icon(Icons.grid_view),
                tooltip: 'Change layout',
                onPressed:
                    (!widget.menuEntry.is_text_method)
                        ? () {
                          controller.toggleGrid();
                        }
                        : null,
              ),
            ],
            // actionsIconTheme: (widget.menuEntry.is_text_method) ? IconThemeData(opacity: 0) : null,
          ),
          body: _body(controller),
        );
      },
    );
  }

  Widget _body(AppController controller) {
    if (items.isEmpty) {
      return const Center(child: CircularProgressIndicator());
    }
    return Obx(() {
      return (controller.isGrid.value)
          ? _grid()
          : _list();
    });
  }

  Widget _grid() {
    List<Widget> children = [];
    for (Item item in items) {
      children.add(
        CubeCard(
          image: CubeSvg.cubeSvg(item.picmode, item.pic_state, height: 150),
          onTap: () => _dialogBuilder(context, item),
        ),
      );
    }

    return GridView.count(
      crossAxisCount: 2,
      primary: false,
      scrollDirection: Axis.vertical,
      shrinkWrap: true,
      // children: menuGroup.menu_entries.map((menuEntry) => menuEntryCard(menuEntry, menuGroup)).toList(),
      children: children,
    );
  }

  Widget _list() {
    return ListView.builder(
      controller: _scrollController,
      itemCount: items.length,
      itemBuilder: (context, index) {
        Item item = items[index];
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
          Container(
            child: Container(margin: const EdgeInsets.all(5), child: Text(item.algs[item.selected_alg_order].text)),
          ),
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
            Expanded(
              child: Container(margin: const EdgeInsets.all(5), child: Text(item.algs[item.selected_alg_order].text)),
            ),
          ],
        ),
        onTap: () => (!widget.menuEntry.is_text_method) ? _dialogBuilder(context, item) : null,
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
                Padding(
                  padding: const EdgeInsets.only(bottom: 4),
                  child: Stack(
                    alignment: Alignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 40),
                        child: Text(
                          "${widget.menuEntry.prefix}${item.my_order}",
                          textAlign: TextAlign.center,
                          style: const TextStyle(fontSize: 24),
                        ),
                      ),
                      Positioned(
                        right: 0,
                        child: IconButton(
                          icon: const Icon(Icons.close),
                          onPressed: () => Get.back(),
                        ),
                      ),
                    ],
                  ),
                ),
                const Divider(),
                Container(
                  margin: const EdgeInsets.all(10),
                  child: Stack(
                    children: [
                      Center(
                        child: CubeSvg.cubeSvg(item.picmode, item.pic_state, height: 200),
                      ),
                      Positioned(
                        right: 0,
                        bottom: 0,
                        child: IconButton(
                          icon: const Icon(Icons.rotate_right),
                          onPressed: () {
                            Get.find<AppController>().rotateFrontSide();
                          },
                        ),
                      ),
                    ],
                  ),
                ),
                ObxValue<RxInt>((RxInt selectedAlg) {
                  return Column(
                    children: [
                      for (Alg alg in item.algs)
                        Container(
                          margin: const EdgeInsets.symmetric(vertical: 2),
                          padding: const EdgeInsets.all(5),
                          decoration: BoxDecoration(
                            color: Colors.black.withOpacity(0.05),
                            borderRadius: BorderRadius.circular(4),
                          ),
                          child: Row(
                            children: [
                              Expanded(
                                child: Text(alg.text, style: const TextStyle(fontSize: 18)),
                              ),
                              IconButton(
                                icon: Icon(
                                  selectedAlg.value == alg.my_order
                                      ? Icons.star
                                      : Icons.star_border,
                                  color: Colors.amber,
                                ),
                                onPressed: () {
                                  DBHelper.updateSelectedAlgItem(item, alg.my_order);
                                  selectedAlg.value = alg.my_order;
                                  initItems();
                                },
                              ),
                            ],
                          ),
                        ),
                    ],
                  );
                }, item.selected_alg_order.obs),
              ],
            ),
          ),
        );
      },
    );
  }
}
