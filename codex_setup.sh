#!/usr/bin/env bash
set -euo pipefail

# -----------------------------------------------------------------------------
# 1. Shallow-clone Flutter SDK once
# -----------------------------------------------------------------------------
FLUTTER_VERSION="3.32.2"
FLUTTER_DIR="$HOME/.cache/flutter/$FLUTTER_VERSION"

if [ ! -d "$FLUTTER_DIR/bin" ]; then
  echo "Cloning Flutter $FLUTTER_VERSION..."
  git clone --depth 1 --branch "$FLUTTER_VERSION" \
    https://github.com/flutter/flutter.git \
    "$FLUTTER_DIR"
else
  echo "Flutter $FLUTTER_VERSION already cloned."
fi

export PATH="$FLUTTER_DIR/bin:$PATH"

# -----------------------------------------------------------------------------
# 2. Persist pub cache
# -----------------------------------------------------------------------------
export PUB_CACHE="$HOME/.cache/pub-cache"
mkdir -p "$PUB_CACHE"
echo "Using pub cache at $PUB_CACHE"

# -----------------------------------------------------------------------------
# 3. Precache Android artifacts once
# -----------------------------------------------------------------------------
ANDROID_PRECACHE_MARK="$FLUTTER_DIR/.android_precache_done"

if [ ! -f "$ANDROID_PRECACHE_MARK" ]; then
  echo "Running flutter precache --android..."
  flutter precache --android
  touch "$ANDROID_PRECACHE_MARK"
else
  echo "Android artifacts already precached."
fi

# -----------------------------------------------------------------------------
# 4. Project setup & checkout flutter branch
# -----------------------------------------------------------------------------
PROJECT_DIR="${PROJECT_DIR:-/workspace/CubeGuide}"
echo "Switching to project dir: $PROJECT_DIR"
cd "$PROJECT_DIR"
echo "cd project dir"

# -----------------------------------------------------------------------------
# 5. Fast pub get (offline first)
# -----------------------------------------------------------------------------
echo "Running flutter pub get..."
flutter pub get --offline || flutter pub get

echo "Setup complete!"