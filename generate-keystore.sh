#!/bin/bash

# Generate Android keystore for signing APKs
# Run this script once to create your signing key

echo "🔐 Generating Android Keystore..."
echo ""

# Generate keystore
keytool -genkey -v -keystore velotravel-release.keystore \
  -alias velotravel \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -storepass android123 \
  -keypass android123 \
  -dname "CN=VeloTravel, OU=Development, O=VeloTravel, L=Sofia, ST=Sofia, C=BG"

echo ""
echo "✅ Keystore created: velotravel-release.keystore"
echo ""
echo "📋 Now add these secrets to GitHub:"
echo "   Go to: https://github.com/fizzexual/VeloTravel/settings/secrets/actions"
echo ""
echo "   1. SIGNING_KEY (base64 of keystore):"
echo "      Run: base64 -w 0 velotravel-release.keystore"
echo ""
echo "   2. KEY_ALIAS: velotravel"
echo "   3. KEY_STORE_PASSWORD: android123"
echo "   4. KEY_PASSWORD: android123"
echo ""
echo "⚠️  IMPORTANT: Keep velotravel-release.keystore safe!"
echo "   You need it to sign future updates!"
