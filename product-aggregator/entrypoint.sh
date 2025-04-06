#!/bin/sh

set -e

# Check required environment variables
for var in $CHECK_VARS; do
  if [ -z "$(eval echo \$$var)" ]; then
    echo "❌ ERROR: Required environment variable '$var' is not set."
    exit 1
  fi
done
exec java -jar app.jar