import 'package:easy_localization/easy_localization.dart';
import 'package:flutter/material.dart';

class CubeCard extends StatelessWidget {
  final String? title;
  final bool tr;
  final Widget image;
  final VoidCallback onTap;

  const CubeCard({Key? key, required this.onTap, required this.image, this.title, this.tr = true}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 3,
      child: InkWell(
        splashColor: Colors.blue.withAlpha(30),
        onTap: onTap,
        child: _body(context)
            // Center(child: image),
        // child: Column(
        //   children: <Widget>[
        //     if (title != null)
        //       Container(
        //         margin: const EdgeInsets.only(top: 7, bottom: 3),
        //         child: Text(context.tr(title!), style: const TextStyle(fontSize: 22, fontWeight: FontWeight.w200)),
        //       ),
        //     Center(
        //       child: image
        //     )
        //     // image,
        //   ],
        // ),
      ),
    );
  }

  Widget _body(BuildContext context) {
    if (title != null) {
      return Container(
        padding: const EdgeInsets.all(10),
        child: Column(
          children: <Widget>[
            Container(
              margin: const EdgeInsets.only(top: 7, bottom: 3),
              child: Text(context.tr(title!), style: const TextStyle(fontSize: 22, fontWeight: FontWeight.w200)),
            ),
            Center(child: image),
          ],
        ),
      );
    }
    return Center(
      child: image,
    );
  }
}

class CubeRowCard extends StatelessWidget {
  final String title;
  final String? description;
  final Widget image;
  final VoidCallback onTap;
  final bool isImageLeft;

  const CubeRowCard({
    Key? key,
    required this.onTap,
    required this.image,
    required this.title,
    this.description,
    this.isImageLeft = true,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    List<Widget> rowChildren = [image, _column(context)];

    if (!isImageLeft) {
      rowChildren = rowChildren.reversed.toList();
    }

    return Container(
      margin: const EdgeInsets.all(10),
      child: Card(
        child: InkWell(
          splashColor: Colors.blue.withAlpha(30),
          child: Container(padding: const EdgeInsets.all(10), child: Row(children: rowChildren)),
          onTap: onTap,
        ),
      ),
    );
  }

  Widget _column(BuildContext context) {
    return Expanded(
      child: Column(
        children: <Widget>[
          Container(
            margin: const EdgeInsets.only(left: 10, right: 10, top: 10, bottom: 5),
            width: double.infinity,
            child: Text(
              context.tr(title),
              style: const TextStyle(fontSize: 20),
              textAlign: TextAlign.left,
            ),
          ),
          if (description != null)
            Container(
              margin: const EdgeInsets.only(left: 10, right: 10, bottom: 10),
              width: double.infinity,
              child: Text(
                context.tr(description!),
                maxLines: 3,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(fontSize: 16),
              ),
            ),
        ],
      ),
    );
  }
}
