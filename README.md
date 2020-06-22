# 🎮 🏝 RoguyIsleMaze
[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.70-blue.svg?style=flat&logo=kotlin&logoColor=white)](http://kotlinlang.org)
[![Korge](https://img.shields.io/badge/Korge-1.13.8.3-836DAC.svg)](https://korge.soywiz.com/)

My contribution for the first [KorGE](https://korge.org/) - [GameJam](https://itch.io/jam/korge-gamejam-1)

# [🎮 Start RoguyIsleMaze WebApp](http://tobsefritz.de/java/roguy-isle-maze/)

## 📖 Story
Our four heroes wake up after a drunken night and have to find their way home. 
Unfortunately they have lost their masks without which they are not allowed to enter their house anymore. 
Your task:
  1. Search for all masks - matching every character.
  2. Find the right house for each player.
  
There are always four figures in the game: yellow, red, purple, black.
Join a group of up to five players and complete the task. 
But it is not as easy as it looks. Each player can move any piece at the same time! 
However, only a limited number of movement options are available to each player! 
You can only complete the task as a team.
Before you can reach the next part of the map, you have to use your magnifier to discover it:
Move onto a way marker with a question mark with the same color. Then click the magnifier or press the shortcut space.
You win the game if all heroes have collected their mask and have reached their home before the time ends.
To start a new game, simply reload the browser tab on all clients.

## 👉 Before Start
 1. Team up with 2-4 friends. Everybody opens the game in the browser on their own:  [🎮 RoguyIsleMaze](http://tobsefritz.de/java/roguy-isle-maze/)
 2. Open the **help** dialog by clicking the question mark icon on the right side. It will also explain the controls!
 3. Open the  **settings** dialog by clicking the gear icon.  
    Adjust _Number of Players_ to the number of total players of the game.  
    Adjust _Player_ to your player number. Every teammate needs to choose a different one.  
    Choose a _Network Game Channel_ and share it with your teammates, so everyone is in the same channel.  
 4. Check your available actions. Depending on the number of players, and your chosen player you will have different
    actions available (green buttons next to the hero selection).
   
> To get updates, you have to set up another player for everyone (every Browser).

## ⌨ Controls
* <kbd>1</kbd><kbd>2</kbd><kbd>3</kbd><kbd>4</kbd> Switch between your four heros.
* <kbd>W</kbd><kbd>A</kbd><kbd>S</kbd><kbd>D</kbd> Move the selected hero.
* <kbd>🡰</kbd><kbd>🡲</kbd><kbd>🡱</kbd><kbd>🡳</kbd> Move the map.
* <kbd>SPACE</kbd> Discover the next room (only available if you can see the magnifier action icon).
* <kbd>+</kbd><kbd>-</kbd> Zoom the map in or out.

### 🐞 Troubleshooting
* All players need a different _player-number_, the correct _number of players_, and the same _game channel_.
  Otherwise it's not possible for the game to sync the game states. Choose the channel wisely, because only
  your team should use it. If other players have chosen this channel, they will interact with your game. 
  Unfortunately, for now there is no possibility to see if a channel is already taken by others.


#### 🖼 Used Assets
* [Greenland - Top-Down Game Tileset | GameArt2D](https://sellfy.com/pzuh/p/QvrD/)
* [Free Jungle Cartoon GUI](https://free-game-assets.itch.io/free-jungle-cartoon-gui)
* [Board Game Pack by Kenney | OpenGameArt](https://opengameart.org/content/boardgame-pack)
* [Free icons designed by dDara \| Flaticon](https://www.flaticon.com/authors/ddara)

--- 
### 🛠 Dev Info

## Manual Multiplayer Setup

## 🌍 Multiplayer - On private host
> To run the game by your own on a local network with a private server, you have to start the game server.
It's a separate project available here:  
[RoguyIsleMaze-Server](https://github.com/TobseF/RoguyIsleMaze-Server)  
> Only works on the Javascript build 
Start the game and choose a player by pressing F1-F5. To get updates, you have to set up another player for everyone (every Browser).  

For Windows, change all the `./gradlew` for `gradlew.bat`.

You should use Gradle 5.5 or greater and Java 8 or greater.

## Compiling for the JVM (Desktop)

Inside IntelliJ you can go to the `src/commonMain/kotlin/main.kt` file and press the green ▶️ icon
that appears to the left of the `suspend fun main()` line.

Using gradle tasks on the terminal:

```bash
./gradlew runJvm                    # Runs the program
./gradlew packageJvmFatJar          # Creates a FAT Jar with the program
./gradlew packageJvmFatJarProguard  # Creates a FAT Jar with the program and applies Proguard to reduce the size
```

Fat JARs are stored in the `/build/libs` folder.

## Compiling for the Web

Using gradle tasks on the terminal:

```bash
./gradlew jsWeb                     # Outputs to /build/web
./gradlew jsWebMin                  # Outputs to /build/web-min (applying Dead Code Elimination)
./gradlew jsWebMinWebpack           # Outputs to /build/web-min-webpack (minimizing and grouping into a single bundle.js file)
./gradlew runJs                     # Outputs to /build/web, creates a small http server and opens a browser
```

You can use any HTTP server to serve the files in your browser.
For example using: `npm -g install http-server` and then executing `hs build/web`.

You can also use `./gradlew -t jsWeb` to continuously building the JS sources and running `hs build/web` in another terminal.
Here you can find a `testJs.sh` script doing exactly this for convenience.

You can run your tests using Node.JS by calling `jsTest` or in a headless chrome with `jsTestChrome`.

## Compiling for Native Desktop (Windows, Linux and macOS)

Using gradle tasks on the terminal:

```bash
./gradlew linkMainDebugExecutableMacosX64         # Outputs to /build/bin/macosX64/mainDebugExecutable/main.kexe
./gradlew linkMainDebugExecutableLinuxX64         # Outputs to /build/bin/linuxX64/mainDebugExecutable/main.kexe
./gradlew linkMainDebugExecutableMingwX64         # Outputs to /build/bin/mingwX64/mainDebugExecutable/main.exe
```

Note that windows executables doesn't have icons bundled.
You can use [ResourceHacker](http://www.angusj.com/resourcehacker/) to add an icon to the executable for the moment.
Later this will be done automatically.

### Cross-Compiling for Linux/Windows

If you have docker installed, you can generate native executables for linux and windows
using the cross-compiling gradle wrappers:

```bash
./gradlew_linux linkMainDebugExecutableLinuxX64   # Outputs to /build/web
./gradlew_win   linkMainDebugExecutableMingwX64   # Outputs to /build/web
```

### Generating MacOS `.app`

```bash
./gradlew packageMacosX64AppDebug             # Outputs to /build/unnamed-debug.app
```

You can change `Debug` for `Release` in all the tasks to generate Release executables.

You can use the `strip` tool from your toolchain (or in the case of windows found in the ``~/.konan` toolchain)
to further reduce Debug and Release executables size by removing debug information (in some cases this will shrink the EXE size by 50%).

In windows this exe is at: `%USERPROFILE%\.konan\dependencies\msys2-mingw-w64-x86_64-gcc-7.3.0-clang-llvm-lld-6.0.1\bin\strip.exe`.

### Linux notes

Since linux doesn't provide standard multimedia libraries out of the box,
you will need to have installed the following packages: `freeglut3-dev` and `libopenal-dev`.

In ubuntu you can use `apt-get`: `sudo apt-get -y install freeglut3-dev libopenal-dev`.

## Compiling for Android

You will need to have installed the Android SDK in the default path for your operating system
or to provide the `ANDROID_SDK` environment variable. The easiest way is to install Android Studio.

Using gradle tasks on the terminal:

### Native Android (JVM)

```bash
./gradlew installAndroidDebug             # Installs an APK in all the connected devices
./gradlew runAndroidEmulatorDebug         # Runs the application in an emulator
```

Triggering these tasks, it generates a separate android project into `build/platforms/android`.
You can open it in `Android Studio` for debugging and additional tasks. The KorGE plugin just
delegates gradle tasks to that gradle project.

### Apache Cordova (JS)

```bash
./gradlew compileCordovaAndroid           # Just compiles cordova from Android
./gradlew runCordovaAndroid               # Runs the application (dce'd, minimized and webpacked) in an Android device
./gradlew runCordovaAndroidNoMinimized    # Runs the application in Android without minimizing (so you can use `chrome://inspect` to debug the application easier)
```



## Compiling for iOS

You will need XCode and to download the iOS SDKs using Xcode.

Using gradle tasks on the terminal:

### Native iOS (Kotlin/Native) + Objective-C

Note that the necessary bridges are built using Objective-C instead of Swift, so the application
won't include Swift's runtime.

```bash
./gradlew iosBuildSimulatorDebug          # Creates an APP file
./gradlew iosInstallSimulatorDebug        # Installs an APP file in the simulator
./gradlew iosRunSimulatorDebug            # Runs the APP in the simulator

```

These tasks generate a xcode project in `build/platforms/ios`, so you can also open the project
with XCode and do additional tasks there.

It uses [XCodeGen](https://github.com/yonaskolb/XcodeGen) for the project generation
and [ios-deploy](https://github.com/ios-control/ios-deploy) for deploying to real devices.

### Apache Cordova (JS)

```bash
./gradlew compileCordovaIos               # Just compiles cordova from iOS
./gradlew runCordovaIos                   # Runs the application (dce'd, minimized and webpacked) in an iOS device
./gradlew runCordovaIosNoMinimized        # Runs the application in iOS without minimizing (so you can use Safari on macOS to debug the application easier)
```

