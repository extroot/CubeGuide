# Project Agents.md Guide for Flutter CubeGuide

This Agents.md file provides comprehensive guidance for OpenAI Codex and other AI agents working with the CubeGuide Flutter codebase.

## Project Structure for AI Agents

* `/lib`: Dart source code

    * `/utils`: Helper classes, controllers, and constants

        * `db_helper.dart`, `app_controller.dart`, `constants.dart`, `models.dart`
    * `/pages`: UI screens for navigation and feature flows

        * `menu_page.dart`, `method_page.dart`, `settings_page.dart`, `intro_screen.dart`, `splash_screen.dart`
    * `/widgets`: Reusable UI components

        * `cards.dart`, `cube_svg.dart`
    * `main.dart`: App entry point
* `/test`: Unit and widget tests
* `/assets`: Resources

    * `/svg_templates`: Puzzle SVG templates (2x2x2, 3x3x3, 4x4x4, 5x5x5, etc.)
    * `/translations`: Localization JSON files (`en.json`)
    * `logo.png`
* `/fonts`: Custom font files (`Lato`, `Montserrat`)
* `/android` & `/ios`: Platform-specific project files (agents should not modify directly)
* CI & tooling

    * `analysis_options.yaml`: Dart analyzer rules
    * `pubspec.yaml` & `pubspec.lock`: Dependencies
    * `.gitignore`, `.metadata`, `.flutter-plugins*`

## Database Design

The app uses a local SQLite/NoSQL style storage managed via `db_helper.dart`. Schema modeled in DBML:

```dbml
Table menu_entry {
  id int [pk]
  prefix varchar(32) [not null]
  menu_picmode varchar(32) [not null, default: "3x3x3"]
  menu_state varchar(255) [not null]
  show_description bool [not null, default: false]
  is_method bool [not null, default: false]
  parent_menu_group int [null, ref: > menu_group.id]
  is_text_method bool [not null, default: false]
}

Table menu_group {
  id int [pk]
  parent_menu_entry int [not null, ref: > menu_entry.id]
  prefix varchar(32) [not null]
  show_title bool [default: true]
  show_grid bool [default: false]
  show_description bool [default: false]
}

Table item {
  id int [pk]
  my_order int [not null]
  pic_state varchar(255) [not null]
  picmode varchar(32) [not null, default: "3x3x3"]
  has_title bool [default: false]
  type varchar(32) [not null, default: "formula"]
  prefix varchar(32)
  menu_entry_id int [ref: > menu_entry.id, not null]
  selected_alg_order int [default: 0, not null, ref: > alg.my_order]
}

Table alg {
  id int [pk]
  alg_group_id int [ref: > item.id, not null]
  my_order int [not null]
  text text [not null]
  is_custom bool [default: false]
}
```

Agents should map Dart model classes to these tables and run migrations in `db_helper.dart`.

## Coding Conventions for AI Agents

### General Dart & Flutter Guidelines

* Use **null safety** and follow `dart format` style.
* Prefer **stateless** widgets where possible; use **stateful** only when managing UI state.
* Adhere to BLoC or Provider patterns as seen in `app_controller.dart`.
* Name files and classes in **snake\_case** for files and **PascalCase** for classes.
* Keep functions and methods **small** (max \~50 LOC) with clear Javadoc comments for complex logic.

### UI Component Guidelines

* Use **Material** design widgets and theming.
* Encapsulate repeated UI patterns in `lib/widgets`.
* Follow the existing color and typography specs defined in `constants.dart`.
* Write **widget tests** for any new UI component under `/test/widgets`.

## Testing Requirements for AI Agents

Before testing or merging code, ensure:
Correct environment setup with Flutter SDK.
Environment could be set up using:

```bash
$ ./codex_setup.sh
```

Agents should run tests using:

```bash
# Run all tests with coverage
flutter test --coverage

# Run a specific test file
flutter test test/pages/menu_page_test.dart
```

Generated code must pass without errors and maintain ≥80% coverage.

## Pull Request Guidelines for AI Agents

When AI agents generate or modify code, ensure the PR:

1. Has a **clear description** of changes.
2. **References** relevant issues or tickets.
3. **Passes all tests** and analysis checks.
4. **Includes screenshots** if UI changes are made.
5. **Keeps scope small**: one feature or fix per PR.

## Programmatic Checks for AI Agents

Before merging, run:

```bash
# Static analysis
flutter analyze
\ n# Format check (exit if unformatted)
flutter format --set-exit-if-changed .

# Build checks
flutter build apk --release --no-tree-shake-icons
flutter build ios --no-codesign
```

All checks must pass to ensure code quality and consistency.

---

*Agents.md empowers AI agents to navigate, maintain, and extend the CubeGuide Flutter application with confidence and consistency.*
