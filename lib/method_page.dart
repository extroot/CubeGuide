import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';

import 'db_helper.dart';
import 'method.dart';

class TutorialPage extends StatefulWidget {
  final String title;
  final List<Alg> algs;
  final Method method;

  TutorialPage({required this.title, required this.algs, required this.method});

  @override
  _TutorialPageState createState() => _TutorialPageState();
}

class _TutorialPageState extends State<TutorialPage> {
  final ScrollController _scrollController = ScrollController(initialScrollOffset: 0.0);


  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  void resetScrollPosition() {
    if (_scrollController.hasClients) {
      _scrollController.animateTo(0.0, duration: Duration(milliseconds: 500), curve: Curves.easeOut);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      primary: true,
      body: _list(),
    );
  }

  Widget _list() {
    const String assetName = "assets/methods/";
    const double height = 125;
    return ListView.builder(
      controller: _scrollController,
      itemCount: widget.algs.length,
      itemBuilder: (context, index) {
        if (index == 0) {
          return Container(
            margin: EdgeInsets.all(10),
            child: Text("Test",
              style: TextStyle(fontSize: 20),
            ),
          );
        }
        return Container(
            margin: EdgeInsets.only(left:10, top: 15),
            child: Row(
                children: <Widget>[
                  // Flexible containers with svg image and a text. Margin on the every side of an image is 10.
                  Container(
                    margin: EdgeInsets.only(right: 10),
                    child: SvgPicture.asset(
                      assetName + widget.method.picmode + "/" + widget.method.picmode + index.toString() + ".svg",
                      height: height,
                      placeholderBuilder: (BuildContext context) => Container(
                          padding: const EdgeInsets.all(30.0),
                          child: const CircularProgressIndicator()),
                    ),
                  ),
                  Expanded(
                    child: Container(
                      margin: EdgeInsets.all(5),
                      child: Text(
                        widget.algs[index].alg,
                      ),
                    ),
                  ),
                ]
            )
        );
      },
    );
  }

  void _showEditDialog(BuildContext context, Alg tutorial) {
    TextEditingController _controller =
        TextEditingController(text: tutorial.alg);
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: Text('Edit Formula'),
          content: TextField(
            controller: _controller,
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Save'),
              onPressed: () {
                DBHelper.updateMethodAlg(tutorial.id, _controller.text);
                setState(() {});
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}