#!/usr/bin/env bash
# ==============================================================================
# run-tests.sh
#
# Compiles the production modules and all JUnit 5 tests, then runs the full test
# suite for the ECS Game Engine project. Self-contained: it compiles the
# production source itself, so it works on a clean checkout without first
# running build.sh.
#
# Designed for MinGW/Git Bash on Windows (uses ';' classpath separator).
#
# Usage:  cd <project-root> && bash run-tests.sh
# ==============================================================================

set -e

# Change to the project root directory (where this script lives)
cd "$(dirname "$0")"

# --------------------------------------------------------------------------
# JUnit 5 classpath components
# --------------------------------------------------------------------------

JUNIT_DIR="lib/junit5"
JUNIT_CP="$JUNIT_DIR/junit-jupiter-api-5.11.4.jar"
JUNIT_CP="$JUNIT_CP;$JUNIT_DIR/junit-jupiter-engine-5.11.4.jar"
JUNIT_CP="$JUNIT_CP;$JUNIT_DIR/junit-platform-commons-1.11.4.jar"
JUNIT_CP="$JUNIT_CP;$JUNIT_DIR/junit-platform-engine-1.11.4.jar"
JUNIT_CP="$JUNIT_CP;$JUNIT_DIR/opentest4j-1.3.0.jar"
JUNIT_CP="$JUNIT_CP;$JUNIT_DIR/apiguardian-api-1.1.2.jar"

STANDALONE_JAR="$JUNIT_DIR/junit-platform-console-standalone-1.11.4.jar"

# --------------------------------------------------------------------------
# Compile the production modules (dependency order, into each module's bin/)
# --------------------------------------------------------------------------

echo "========================================"
echo "  Compiling production modules..."
echo "========================================"

for dir in \
    Library/UtilityClassLibrary \
    Library/CommandManager \
    Library/ResourceManager \
    Library/AWTJavaPlatform \
    Library/EventManager \
    Library/ECSGameEngine \
    Library/GlobalCache \
    Demo/ParticleDemo; do

    rm -rf "$dir/bin"
    mkdir -p "$dir/bin"
done

javac -d Library/UtilityClassLibrary/bin \
    $(find Library/UtilityClassLibrary/src -name "*.java")

javac -d Library/CommandManager/bin \
    -cp Library/UtilityClassLibrary/bin \
    $(find Library/CommandManager/src -name "*.java")

javac -d Library/ResourceManager/bin \
    $(find Library/ResourceManager/src -name "*.java")

javac -d Library/AWTJavaPlatform/bin \
    -cp Library/UtilityClassLibrary/bin \
    $(find Library/AWTJavaPlatform/src -name "*.java")

javac -d Library/EventManager/bin \
    $(find Library/EventManager/src -name "*.java")

javac -d Library/ECSGameEngine/bin \
    -cp "Library/CommandManager/bin;Library/ResourceManager/bin;Library/UtilityClassLibrary/bin" \
    $(find Library/ECSGameEngine/src -name "*.java")

javac -d Library/GlobalCache/bin \
    $(find Library/GlobalCache/src -name "*.java")

javac -d Demo/ParticleDemo/bin \
    -cp "Library/ECSGameEngine/bin;Library/CommandManager/bin;Library/ResourceManager/bin;Library/UtilityClassLibrary/bin;Library/AWTJavaPlatform/bin;Library/EventManager/bin;Library/GlobalCache/bin" \
    $(find Demo/ParticleDemo/src -name "*.java")

echo "  Production compilation successful."
echo ""

# --------------------------------------------------------------------------
# Production bin directories (classpath for tests)
# --------------------------------------------------------------------------

PROD_CP="Library/UtilityClassLibrary/bin"
PROD_CP="$PROD_CP;Library/CommandManager/bin"
PROD_CP="$PROD_CP;Library/ResourceManager/bin"
PROD_CP="$PROD_CP;Library/AWTJavaPlatform/bin"
PROD_CP="$PROD_CP;Library/EventManager/bin"
PROD_CP="$PROD_CP;Library/ECSGameEngine/bin"
PROD_CP="$PROD_CP;Library/GlobalCache/bin"
PROD_CP="$PROD_CP;Demo/ParticleDemo/bin"

# Test output directory
TEST_BIN="out/test-classes"
rm -rf "$TEST_BIN"
mkdir -p "$TEST_BIN"

# --------------------------------------------------------------------------
# Collect all test source files
# --------------------------------------------------------------------------

TEST_SOURCES=()
for MODULE_DIR in \
    Library/UtilityClassLibrary \
    Library/CommandManager \
    Library/ResourceManager \
    Library/EventManager \
    Library/ECSGameEngine \
    Library/GlobalCache \
    Demo/ParticleDemo; do

    if [ -d "$MODULE_DIR/test" ]; then
        while IFS= read -r -d '' f; do
            TEST_SOURCES+=("$f")
        done < <(find "$MODULE_DIR/test" -name "*.java" -print0)
    fi
done

if [ ${#TEST_SOURCES[@]} -eq 0 ]; then
    echo "ERROR: No test sources found."
    exit 1
fi

echo "========================================"
echo "  Compiling ${#TEST_SOURCES[@]} test files..."
echo "========================================"

COMPILE_CP="$PROD_CP;$JUNIT_CP"

javac -d "$TEST_BIN" -cp "$COMPILE_CP" "${TEST_SOURCES[@]}"

echo "  Compilation successful."
echo ""

# --------------------------------------------------------------------------
# Run tests
# --------------------------------------------------------------------------

echo "========================================"
echo "  Running tests..."
echo "========================================"

RUN_CP="$TEST_BIN;$PROD_CP"

java -jar "$STANDALONE_JAR" \
    --class-path "$RUN_CP" \
    --scan-class-path "$TEST_BIN" \
    --details verbose

echo ""
echo "========================================"
echo "  Done."
echo "========================================"
