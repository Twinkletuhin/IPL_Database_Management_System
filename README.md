# JavaFX Project

Welcome to the **JavaFX Project**! This repository contains a JavaFX-based application that provides a modern and interactive user interface. This guide includes everything you need to know to set up, configure, and run the project.

---

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
  - [Installing JavaFX](#installing-javafx)
  - [Setup in VS Code](#setup-in-vs-code)
    - [launch.json Configuration](#launchjson-configuration)
    - [settings.json Configuration](#settingsjson-configuration)
  - [Setup in IntelliJ IDEA](#setup-in-intellij-idea)
- [Platform-Specific Notes](#platform-specific-notes)
  - [Linux Users](#linux-users)
  - [Windows Users](#windows-users)
- [Running the Project](#running-the-project)

---

## Features

- Beautiful and responsive UI built with JavaFX.
- Platform-independent functionality.
- Modularized project structure for easy scalability.

---

## Prerequisites

Make sure you have the following installed on your system:

1. **Java Development Kit (JDK)**: Version 21 or later.
2. **JavaFX SDK**: Version 21.0.1 (or compatible).
3. **VS Code** or **IntelliJ IDEA** as your preferred IDE.

---

## Installation

### Installing JavaFX

Follow these steps to install JavaFX:

1. **Download JavaFX SDK**

   - Go to the [JavaFX Downloads Page](https://openjfx.io/).
   - Download the appropriate SDK for your platform (Windows/Linux).

2. **Extract the SDK**

   - Extract the downloaded `.zip` file to a known location, e.g., `C:\javafx-sdk-21.0.3` (Windows) or `/opt/javafx-sdk-21.0.3` (Linux).

---

### Setup in VS Code

#### launch.json Configuration

To run the JavaFX application in VS Code, configure the `launch.json` file as follows:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Launch JavaFX Application",
      "request": "launch",
      "mainClass": "your.main.ClassName",
      "vmArgs": "--module-path <path-to-javafx-sdk>/lib --add-modules javafx.controls,javafx.fxml"
    }
  ]
}
```

Replace `<path-to-javafx-sdk>` with the actual path where you extracted the JavaFX SDK.

#### settings.json Configuration

To ensure VS Code recognizes JavaFX libraries, add the following to `settings.json`:

```json
{
  "java.project.referencedLibraries": [
    "<path-to-javafx-sdk>/lib/*.jar"
  ]
}
```

---

### Setup in IntelliJ IDEA

1. Open the project in IntelliJ IDEA.
2. Go to `File > Project Structure > Libraries`.
3. Click `+` and select `Java`.
4. Add the `lib` folder from the JavaFX SDK directory.
5. Add VM options:
   - Go to `Run > Edit Configurations`.
   - Under VM options, add:
     ```
     --module-path <path-to-javafx-sdk>/lib --add-modules javafx.controls,javafx.fxml
     ```

---

## Platform-Specific Notes

### Linux Users

- Ensure you have `libglfw` and `libgtk-3` installed on your system for JavaFX to work correctly.
- Use your package manager to install dependencies. For example:
  ```bash
  sudo apt install libglfw3 libgtk-3-dev
  ```
- When specifying the JavaFX module path, ensure proper permissions are set for the SDK directory.

### Windows Users

- Use an absolute path to the JavaFX SDK in configurations (e.g., `C:\javafx-sdk-21.0.3\lib`).
- Ensure your system has `JAVA_HOME` set correctly to the JDK installation directory.

---

## Running the Project

After completing the setup:

### VS Code

1. Open the project folder.
2. Press `F5` or go to the Run and Debug tab and select "Launch JavaFX Application."

### IntelliJ IDEA

1. Click the green Run button or press `Shift + F10` to run the application.

---


