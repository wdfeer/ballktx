package wdfeer.ballktx.extension

import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.math.Vector2

val GlyphLayout.size: Vector2 get() = Vector2(width, height)
