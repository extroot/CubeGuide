import 'package:cube_guide/utils/cube_svg.dart';
import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';

import '../utils/db_helper.dart';
import '../utils/models.dart';

class MethodPage extends StatefulWidget {
  final MenuEntry menuEntry;

  const MethodPage({super.key, required this.menuEntry});

  @override
  _MethodPageState createState() => _MethodPageState();
}

class _MethodPageState extends State<MethodPage> {
  final ScrollController _scrollController = ScrollController(initialScrollOffset: 0.0);

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
        title: Text("${widget.menuEntry.prefix}.title".tr()),
      ),
      body: _list(),
    );
  }

  Widget _list() {
    return FutureBuilder(
      future: DBHelper.getItemsByEntryId(widget.menuEntry.id),
      builder: (context, snapshot) {
        print(snapshot.hasData);
        if (snapshot.hasData) {
          return ListView.builder(
            controller: _scrollController,
            itemCount: snapshot.data!.length,
            itemBuilder: (context, index) {
              Item item = snapshot.data![index];
              if (item.type == 'formula') return formulaRow(item);
              if (item.type == 'text') return textRow(item);
              if (item.type == 'header') return headerRow(item);
              if (item.type == 'single_cube') return singleCubeRow(item);
              if (item.type == 'single_cube_alg') return verticalFormula(item);
              // print("Wrong type of item: " + item.type);
              return null;
            },
          );
        } else {
          return const Center(child: CircularProgressIndicator());
        }
      },
    );
  }

  Widget verticalFormula(Item item) {
    return Container(
        margin: const EdgeInsets.only(left: 10, top: 15),
        child: Column(children: <Widget>[
          Container(
              margin: const EdgeInsets.only(right: 10),
              child: CubeSvg.cubeSvg(item.picmode, item.pic_state, height: 125)),
          Expanded(
            child: Container(
              margin: const EdgeInsets.all(5),
              child: Text(
                getAlg(item.algs),
              ),
            ),
          ),
        ]));
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
          child: Text(
            "${widget.menuEntry.prefix}.items.${item.prefix}".tr(),
            style: TextStyle(fontSize: size),
          ),
        ));
  }

  Widget textRow(Item item) {
    return Container(
      margin: const EdgeInsets.all(10),
      child: Text(
        "${widget.menuEntry.prefix}.items.${item.prefix}".tr(),
        style: const TextStyle(fontSize: 18),
      ),
    );
  }

  Widget formulaRow(Item item) {
    return Container(
        margin: const EdgeInsets.only(left: 10, top: 15),
        child: Row(children: <Widget>[
          Container(
              margin: const EdgeInsets.only(right: 10),
              child: CubeSvg.cubeSvg(item.picmode, item.pic_state, height: 125)),
          Expanded(
            child: Container(
              margin: const EdgeInsets.all(5),
              child: Text(
                getAlg(item.algs),
              ),
            ),
          ),
        ]));
  }

  String getAlg(List<Alg> algs) {
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

//
// class TutorialPage extends StatefulWidget {
//   final String title;
//   final List<Alg> algs;
//   final Method method;
//
//   TutorialPage({required this.title, required this.algs, required this.method});
//
//   @override
//   _TutorialPageState createState() => _TutorialPageState();
// }
//
// class _TutorialPageState extends State<TutorialPage> {
//   final ScrollController _scrollController = ScrollController(initialScrollOffset: 0.0);
//
//
//   @override
//   void dispose() {
//     _scrollController.dispose();
//     super.dispose();
//   }
//
//   void resetScrollPosition() {
//     if (_scrollController.hasClients) {
//       _scrollController.animateTo(0.0, duration: Duration(milliseconds: 500), curve: Curves.easeOut);
//     }
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       primary: true,
//       body: _list(),
//     );
//   }
//
//   Widget _list() {
//     const String assetName = "assets/methods/";
//     const double height = 125;
//     return ListView.builder(
//       controller: _scrollController,
//       itemCount: widget.algs.length,
//       itemBuilder: (context, index) {
//         if (index == 0) {
//           return Container(
//             margin: EdgeInsets.all(10),
//             child: Text("Test",
//               style: TextStyle(fontSize: 20),
//             ),
//           );
//         }
//         return Container(
//             margin: EdgeInsets.only(left:10, top: 15),
//             child: Row(
//                 children: <Widget>[
//                   // Flexible containers with svg image and a text. Margin on the every side of an image is 10.
//                   Container(
//                     margin: EdgeInsets.only(right: 10),
//                     child: SvgPicture.asset(
//                       assetName + widget.method.picmode + "/" + widget.method.picmode + index.toString() + ".svg",
//                       height: height,
//                       placeholderBuilder: (BuildContext context) => Container(
//                           padding: const EdgeInsets.all(30.0),
//                           child: const CircularProgressIndicator()),
//                     ),
//                   ),
//                   Expanded(
//                     child: Container(
//                       margin: EdgeInsets.all(5),
//                       child: Text(
//                         widget.algs[index].alg,
//                       ),
//                     ),
//                   ),
//                 ]
//             )
//         );
//       },
//     );
//   }
//
//   void _showEditDialog(BuildContext context, Alg tutorial) {
//     TextEditingController _controller =
//         TextEditingController(text: tutorial.alg);
//     showDialog(
//       context: context,
//       builder: (context) {
//         return AlertDialog(
//           title: Text('Edit Formula'),
//           content: TextField(
//             controller: _controller,
//           ),
//           actions: <Widget>[
//             TextButton(
//               child: Text('Save'),
//               onPressed: () {
//                 DBHelper.updateMethodAlg(tutorial.id, _controller.text);
//                 setState(() {});
//                 Navigator.of(context).pop();
//               },
//             ),
//           ],
//         );
//       },
//     );
//   }
// }
