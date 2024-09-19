package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.math.Vector2
import ktx.math.*
import wdfeer.ballktx.component.DrawComponent
import wdfeer.ballktx.extension.size

class Label(text: String, private val center: Vector2) : Entity() {
    private val layout = GlyphLayout(font, text)
    init {
        add(DrawComponent {
            val pos = center - layout.size / 2f
            font.draw(it, layout, pos.x, pos.y)
        })
    }

    companion object {
        val font = BitmapFont().apply {
            setUseIntegerPositions(false)
            data.setScale(0.2f)
        }
    }
}
