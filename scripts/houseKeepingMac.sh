#!/bin/bash

# Colors for decorations
YELLOW='\033[1;33m'
CYAN='\033[1;36m'
NC='\033[0m' # No Color

echo -e "${YELLOW}*********************************************${NC}"
echo -e "${YELLOW}*${NC}       Deleting Orphan Git Branches        ${YELLOW}*${NC}"
echo -e "${YELLOW}*********************************************${NC}"

# Fetch remote branches and prune
git fetch --prune

# Get a list of local branches
local_branches=$(git branch --format "%(refname:short)")

# Check if there are any local branches
if [ -z "$local_branches" ]; then
    echo -e "${CYAN}No local branches found.${NC}"
    exit 0
fi

echo -e "${CYAN}Local Branches:${NC}"
echo -e "${YELLOW}-----------------${NC}"

# List of living branches
living_branches=()

# Iterate through local branches
for branch in $local_branches; do
    # Check if the branch exists remotely
    remote_branch=$(git ls-remote --heads origin $branch)

    # If the branch doesn't exist remotely, delete it locally
    if [ -z "$remote_branch" ]; then
        echo -e "${YELLOW}Deleting orphan branch: ${NC}${branch}"
        git branch -d $branch
    else
        living_branches+=("$branch")
    fi
done

echo -e "${CYAN}Living Branches:${NC}"
echo -e "${YELLOW}-----------------${NC}"

# Display living branches
for living_branch in "${living_branches[@]}"; do
    echo -e "${CYAN}${living_branch}${NC}"
done

echo -e "${YELLOW}*********************************************${NC}"
