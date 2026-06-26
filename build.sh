#!/bin/bash
# ==============================================================================
# Build script for Particle Simulator
#
# Creates a native Windows application using jpackage.
#
# Usage:
#   ./build.sh              Build app-image (portable directory)
#   ./build.sh --installer  Build Windows installer (.exe) — requires WiX 3+
#
# Output:
#   build/particle-simulator/   App image directory
#   build/particle-simulator-1.0.exe   Installer (if --installer)
#
# Requirements:
#   - JDK 25+ with jpackage
#   - WiX Toolset 3.x (only for --installer mode)
# ==============================================================================

set -e

PROJECT_ROOT="$(cd "$(dirname "$0")" && pwd)"
cd "$PROJECT_ROOT"

BUILD_DIR="build"
CLASSES_DIR="$BUILD_DIR/classes"
INPUT_DIR="$BUILD_DIR/jpackage-input"
JAR_NAME="ParticleSimulator.jar"
APP_NAME="particle-simulator"
APP_VERSION="1.0"
MAIN_CLASS="rohin.gameengine.application.Main"

PACKAGE_TYPE="app-image"
if [ "$1" = "--installer" ]; then
    PACKAGE_TYPE="exe"
fi

# ==============================================================================
# Step 1: Clean
# ==============================================================================

echo "=== Cleaning build directory ==="
rm -rf "$BUILD_DIR/classes" "$BUILD_DIR/staging" "$BUILD_DIR/jpackage-input"
rm -f "$BUILD_DIR/$JAR_NAME"
rm -rf "$BUILD_DIR/$APP_NAME"

for dir in Library/UtilityClassLibrary Library/CommandManager Library/ResourceManager Library/AWTJavaPlatform Library/EventManager Library/ECSGameEngine Library/GlobalCache Demo/ParticleDemo; do
    rm -rf "$dir/bin"
    mkdir -p "$dir/bin"
done

# ==============================================================================
# Step 2: Compile all modules
# ==============================================================================

echo "=== Compiling UtilityClassLibrary ==="
javac -d Library/UtilityClassLibrary/bin \
    $(find Library/UtilityClassLibrary/src -name "*.java")

echo "=== Compiling CommandManager ==="
javac -d Library/CommandManager/bin \
    -cp Library/UtilityClassLibrary/bin \
    $(find Library/CommandManager/src -name "*.java")

echo "=== Compiling ResourceManager ==="
javac -d Library/ResourceManager/bin \
    $(find Library/ResourceManager/src -name "*.java")

echo "=== Compiling AWTJavaPlatform ==="
javac -d Library/AWTJavaPlatform/bin \
    -cp Library/UtilityClassLibrary/bin \
    $(find Library/AWTJavaPlatform/src -name "*.java")

echo "=== Compiling EventManager ==="
javac -d Library/EventManager/bin \
    $(find Library/EventManager/src -name "*.java")

echo "=== Compiling ECSGameEngine ==="
javac -d Library/ECSGameEngine/bin \
    -cp "Library/CommandManager/bin;Library/ResourceManager/bin;Library/UtilityClassLibrary/bin" \
    $(find Library/ECSGameEngine/src -name "*.java")

echo "=== Compiling GlobalCache ==="
javac -d Library/GlobalCache/bin \
    $(find Library/GlobalCache/src -name "*.java")

echo "=== Compiling ParticleDemo ==="
CP="Library/ECSGameEngine/bin;Library/CommandManager/bin;Library/ResourceManager/bin;Library/UtilityClassLibrary/bin;Library/AWTJavaPlatform/bin;Library/EventManager/bin;Library/GlobalCache/bin"
javac -d Demo/ParticleDemo/bin \
    -cp "$CP" \
    $(find Demo/ParticleDemo/src -name "*.java")

# ==============================================================================
# Step 3: Create fat JAR
# ==============================================================================

echo "=== Creating fat JAR ==="
mkdir -p "$BUILD_DIR/staging" "$CLASSES_DIR"

cat > "$BUILD_DIR/staging/MANIFEST.MF" << EOF
Main-Class: $MAIN_CLASS
EOF

for dir in Library/UtilityClassLibrary/bin Library/CommandManager/bin Library/ResourceManager/bin Library/AWTJavaPlatform/bin Library/EventManager/bin Library/ECSGameEngine/bin Library/GlobalCache/bin Demo/ParticleDemo/bin; do
    cp -r "$dir"/* "$CLASSES_DIR/" 2>/dev/null
done

jar cfm "$BUILD_DIR/$JAR_NAME" "$BUILD_DIR/staging/MANIFEST.MF" -C "$CLASSES_DIR" .

echo "  JAR: $BUILD_DIR/$JAR_NAME ($(wc -c < "$BUILD_DIR/$JAR_NAME") bytes)"

# ==============================================================================
# Step 4: Stage resources for jpackage
# ==============================================================================

echo "=== Staging resources ==="
mkdir -p "$INPUT_DIR/Demo/ParticleDemo/Resources"

cp "$BUILD_DIR/$JAR_NAME" "$INPUT_DIR/"
cp Demo/ParticleDemo/settings.properties "$INPUT_DIR/Demo/ParticleDemo/"

for subdir in Fonts Images Text Sounds StringTable; do
    if [ -d "Demo/ParticleDemo/Resources/$subdir" ]; then
        cp -r "Demo/ParticleDemo/Resources/$subdir" "$INPUT_DIR/Demo/ParticleDemo/Resources/"
    fi
done

# Remove non-runtime files (GIMP projects, screenshots)
find "$INPUT_DIR" -name "*.xcf" -delete 2>/dev/null
rm -rf "$INPUT_DIR/Demo/ParticleDemo/Resources/ScreenShots" 2>/dev/null

FILE_COUNT=$(find "$INPUT_DIR" -type f | wc -l)
echo "  Staged $FILE_COUNT files"

# ==============================================================================
# Step 5: Run jpackage
# ==============================================================================

echo "=== Running jpackage (type: $PACKAGE_TYPE) ==="

jpackage \
    --type "$PACKAGE_TYPE" \
    --input "$INPUT_DIR" \
    --main-jar "$JAR_NAME" \
    --main-class "$MAIN_CLASS" \
    --name "$APP_NAME" \
    --app-version "$APP_VERSION" \
    --dest "$BUILD_DIR" \
    --java-options '-Dapp.dir=$APPDIR' \
    --java-options '--enable-native-access=ALL-UNNAMED'

# ==============================================================================
# Done
# ==============================================================================

echo ""
echo "=== Build complete ==="

if [ "$PACKAGE_TYPE" = "app-image" ]; then
    SIZE=$(du -sh "$BUILD_DIR/$APP_NAME" | cut -f1)
    echo "  App image: $BUILD_DIR/$APP_NAME/"
    echo "  Launcher:  $BUILD_DIR/$APP_NAME/$APP_NAME.exe"
    echo "  Size:      $SIZE"
else
    INSTALLER=$(ls "$BUILD_DIR"/*.exe 2>/dev/null | head -1)
    echo "  Installer: $INSTALLER"
fi
