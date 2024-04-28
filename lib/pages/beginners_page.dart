import 'package:flutter/material.dart';

class BeginnersPage extends StatelessWidget {
  const BeginnersPage({super.key});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        children: <Widget>[
          // Add your static text and images here
          Text('Welcome to Rubik\'s Cube Tutorials for Beginners!')
          // Image.asset('assets/beginners_image_1.jpg'),
          // create a standard button

          // Repeat Text and Image widgets as needed
        ],
      ),
    );
  }
}
