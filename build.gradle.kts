import com.soywiz.korge.gradle.GameCategory
import com.soywiz.korge.gradle.KorgeGradlePlugin
import com.soywiz.korge.gradle.Orientation.LANDSCAPE
import com.soywiz.korge.gradle.korge
import com.soywiz.korio.file.std.get

buildscript {
    val korgePluginVersion: String by project

    repositories {
        mavenLocal()
        maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
    }
}

apply<KorgeGradlePlugin>()

korge {
    id = "tfr.korge.game.islemaze"
    name = "Isle Maze"
    exeBaseName = "Isle Maze"
    description = "Cooperative multiplayer browser game, based on the Magic Maze board game."
    gameCategory = GameCategory.BOARD

    icon = project.projectDir["appicon.png"]
    orientation = LANDSCAPE

    authorName = "Tobse"
    authorHref = "https://github.com/TobseF/"
}
