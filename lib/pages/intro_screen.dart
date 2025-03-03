import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:easy_localization/easy_localization.dart';
import 'package:introduction_screen/introduction_screen.dart';

class IntroScreen extends StatelessWidget {
  const IntroScreen({required this.page});

  final Widget page;

  Widget _buildImage(String assetName) {
    return Align(
      child: Image.asset('assets/logo.png', width: 350.0),
      alignment: Alignment.bottomCenter,
    );
  }

  @override
  Widget build(BuildContext context) {
    const bodyStyle = TextStyle(fontSize: 19.0);

    const pageDecoration = PageDecoration(
      titleTextStyle: TextStyle(fontSize: 28.0, fontWeight: FontWeight.w700),
      bodyTextStyle: bodyStyle,
      bodyPadding: EdgeInsets.fromLTRB(16.0, 0.0, 16.0, 16.0),
      pageColor: Colors.white,
      imagePadding: EdgeInsets.zero,
    );

    return Scaffold(
      body: IntroductionScreen(
        pages: [
          PageViewModel(
            title: context.tr("intro.page1.title"),
            body: context.tr("intro.page1.body"),
            image: _buildImage('img1.jpg'),
            decoration: pageDecoration,
          ),
          PageViewModel(
            title: "Learn as you go",
            body:
            "Download the Stockpile app and master the market with our mini-lesson.",
            image: _buildImage('img2.jpg'),
            decoration: pageDecoration,
          ),
          PageViewModel(
            title: "Kids and teens",
            body:
            "Kids and teens can track their stocks 24/7 and place trades that you approve.",
            image: _buildImage('img3.jpg'),
            decoration: pageDecoration,
          ),
          PageViewModel(
            title: "Full Screen Page",
            body:
            "Pages can be full screen as well.\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc id euismod lectus, non tempor felis. Nam rutrum rhoncus est ac venenatis.",
            // backgroundImage: backgroundImage,
            decoration: pageDecoration.copyWith(
              contentMargin: const EdgeInsets.symmetric(horizontal: 16),
              bodyFlex: 2,
              imageFlex: 3,
              safeArea: 100,
            ),
          ),
          PageViewModel(
            title: "Another title page",
            body: "Another beautiful body text for this example onboarding",
            image: _buildImage('img2.jpg'),
            footer: ElevatedButton(
              onPressed: () {
                print('FooButton is pressed');
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.lightBlue,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(8.0),
                ),
              ),
              child: const Text(
                'FooButton',
                style: TextStyle(color: Colors.white),
              ),
            ),
            decoration: pageDecoration.copyWith(
              bodyFlex: 6,
              imageFlex: 6,
              safeArea: 80,
            ),
          ),
          PageViewModel(
            title: "Title of last page - reversed",
            bodyWidget: const Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text("Click on ", style: bodyStyle),
                Icon(Icons.edit),
                Text(" to edit a post", style: bodyStyle),
              ],
            ),
            decoration: pageDecoration.copyWith(
              bodyFlex: 2,
              imageFlex: 4,
              bodyAlignment: Alignment.bottomCenter,
              imageAlignment: Alignment.topCenter,
            ),
            image: _buildImage('img1.jpg'),
            reverse: true,
          ),
        ],
        showSkipButton: true,
        skip: const Text("Skip"),
        next: const Icon(
          Icons.navigate_next,
          color: Colors.blueAccent,
          size: 32,
        ),
        onSkip: () {
          Get.off(() => page);
        },
        done: const Text("Done",
            style: TextStyle(fontWeight: FontWeight.w600)),
        onDone: () {
          Get.off(() => page);
        },
        dotsDecorator: DotsDecorator(
          size: const Size.square(10.0),
          activeSize: const Size(20.0, 10.0),
          activeColor: Theme.of(context).colorScheme.secondary,
          color: Colors.black26,
          spacing: const EdgeInsets.symmetric(horizontal: 3.0),
          activeShape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(25.0)
          ),
        ),
      ),
    );
  }
}