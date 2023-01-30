# flappy-bird 
A `JavaFX` implementation of Flabby Bird hacked together as part of a game jam.

#### Disclaimer
Reading of code may cause your eyes to bleed. Quality tested by pressing spacebar alot. Setup instructions below may or may not be accurate and complete.

## Setup Development Environment (IntelliJ)
- Ensure you have a `Java SDK` distriubtion installed that supports version 11.
- For `MacOS X64`, download and extract [openjfx-19.0.2.1_osx-x64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_osx-x64_bin-sdk.zip)
- For `MacOS M Series`, download and extract [openjfx-19.0.2.1_osx-aarch64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_osx-aarch64_bin-sdk.zip)
- For `Linux X64`, download and extract [openjfx-19.0.2.1_linux-x64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_linux-x64_bin-sdk.zip)
- For `Linux aaarch64`, download and extract [openjfx-19.0.2.1_linux-aarch64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_linux-aarch64_bin-sdk.zip)
- For `Windows X64`, download and extract [openjfx-19.0.2.1_windows-x64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_windows-x64_bin-sdk.zip)
- In `IntelliJ`, open `build.gradle` as a new project.
- Set your `Java SDK` to the distribution that supports `Java 11` in your project settings.
- Edit the runtime configuration for the `Main` class and ensure you have `java 11` and `-cp flappy-bird.main` selected in the `Build and run` section.
- Add `--module-path **/javafx-sdk-19.0.2.1/lib --add-modules=javafx.controls,javafx.media` to the `VM options` of the `Main` runtime configuration, replacing `**` with the path you extracted the `JavaFX SDK` to.
- Refresh the gradle dependencies

## Build and run (IntelliJ)
- Execute the `build` gradle task.
- Run the `Main` runtime configuration task from the IDE or execute the `java --module-path **/javafx-sdk-19.0.2.1/lib --add-modules=javafx.controls,javafx.media -jar flappy-bird-1.0-SNAPSHOT.jar` terminal command from the `build/lib` directory, replacing `**` with the path you extracted the `JavaFX SDK` to. Ensure you are using the `Java 11` runtime.

## Setup Development Environment (No IDE)
- Ensure you have a `Java SDK` distriubtion installed that supports version 11 and that it is your default distribution.
- For `MacOS X64`, download and extract [openjfx-19.0.2.1_osx-x64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_osx-x64_bin-sdk.zip)
- For `MacOS M Series`, download and extract [openjfx-19.0.2.1_osx-aarch64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_osx-aarch64_bin-sdk.zip)
- For `Linux X64`, download and extract [openjfx-19.0.2.1_linux-x64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_linux-x64_bin-sdk.zip)
- For `Linux aaarch64`, download and extract [openjfx-19.0.2.1_linux-aarch64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_linux-aarch64_bin-sdk.zip)
- For `Windows X64`, download and extract [openjfx-19.0.2.1_windows-x64_bin-sdk.zip](https://download2.gluonhq.com/openjfx/19.0.2.1/openjfx-19.0.2.1_windows-x64_bin-sdk.zip)

## Build and run (No IDE)
- In the project root directory, execute `./gradlew build --refresh-dependencies`
- Execute the `java --module-path **/javafx-sdk-19.0.2.1/lib --add-modules=javafx.controls,javafx.media -jar flappy-bird-1.0-SNAPSHOT.jar` terminal command from the `build/lib` directory, replacing `**` with the path you extracted the `JavaFX SDK` to.

## Controls
- `spacebar` for flapping and game start.
- `enter` for 2 second no-clipping mode.
