@file:JvmName("Lwjgl3Launcher")

package wdfeer.ballktx.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import wdfeer.ballktx.Main

/** Launches the desktop (LWJGL3) application. */
fun main() {
    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
      return
    Lwjgl3Application(Main(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("ballktx")
        setWindowedMode(1600, 900)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
