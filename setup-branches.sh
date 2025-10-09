#!/bin/bash

# Setup script for Cookiegram branch structure
# Run this script to create the dev and feature branches

set -e

echo "Setting up Cookiegram branch structure..."

# Get the current branch
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

# Ensure we're up to date with remote
echo "Fetching latest changes..."
git fetch origin

# Create dev branch from main
echo "Creating dev branch from main..."
if git show-ref --verify --quiet refs/heads/dev; then
    echo "dev branch already exists locally"
else
    git checkout -b dev origin/main
    git push -u origin dev
fi

# Create feature branch from dev
echo "Creating feature branch from dev..."
if git show-ref --verify --quiet refs/heads/feature; then
    echo "feature branch already exists locally"
else
    git checkout -b feature dev
    git push -u origin feature
fi

# Return to original branch
echo "Returning to $CURRENT_BRANCH..."
git checkout $CURRENT_BRANCH

echo "Branch setup complete!"
echo "Branches created:"
echo "  - main (already existed)"
echo "  - dev (created from main)"
echo "  - feature (created from dev)"
