# Cookiegram Project Setup

## Quick Start: Creating Required Branches

This project requires three branches: `main`, `dev`, and `feature`.

### Current Status
- ✅ **main** branch - Already exists (default branch)
- ⏳ **dev** branch - Needs to be created
- ⏳ **feature** branch - Needs to be created

### How to Create the Branches

You have **two options** to create the `dev` and `feature` branches:

---

#### Option 1: GitHub Actions (Easiest - No Local Setup Required)

1. **Navigate to the Actions tab** in this GitHub repository
2. **Click on "Setup Project Branches"** workflow in the left sidebar
3. **Click the "Run workflow" button** (on the right side)
4. **Select the branch** to run from (use `main`)
5. **Click the green "Run workflow" button** to confirm

The workflow will automatically:
- Create the `dev` branch from `main`
- Create the `feature` branch from `dev`
- Push both branches to the repository

**Done!** Your branches are now ready for the team to use.

---

#### Option 2: Local Script (Requires Git)

If you prefer to create branches locally:

1. **Clone the repository** (if not already cloned):
   ```bash
   git clone https://github.com/theashisharora/Cookiegram.git
   cd Cookiegram
   ```

2. **Run the setup script**:
   ```bash
   chmod +x setup-branches.sh
   ./setup-branches.sh
   ```

The script will:
- Create the `dev` branch from `main`
- Create the `feature` branch from `dev`
- Push both branches to the remote repository

---

#### Option 3: Manual Creation (Advanced)

If you prefer to create branches manually:

```bash
# Ensure you have the latest main branch
git checkout main
git pull origin main

# Create and push dev branch
git checkout -b dev
git push -u origin dev

# Create and push feature branch
git checkout -b feature
git push -u origin feature

# Return to main
git checkout main
```

---

## Verification

After creating the branches, verify they exist:

**On GitHub:**
- Go to your repository
- Click the branch dropdown (shows current branch name)
- You should see: `main`, `dev`, and `feature`

**Locally (using Git):**
```bash
git fetch --all
git branch -a
```

You should see:
```
* main
  remotes/origin/main
  remotes/origin/dev
  remotes/origin/feature
```

---

## Next Steps for Team Members

Once branches are created, each team member should:

1. **Clone the repository** (if not already done)
2. **Fetch all branches**:
   ```bash
   git fetch --all
   ```
3. **Create local tracking branches**:
   ```bash
   git checkout -b dev origin/dev
   git checkout -b feature origin/feature
   ```

Now you're ready to collaborate! See [CONTRIBUTING.md](CONTRIBUTING.md) for workflow details.
