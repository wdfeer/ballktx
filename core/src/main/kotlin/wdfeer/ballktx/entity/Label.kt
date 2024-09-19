package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import wdfeer.ballktx.component.DrawComponent

class Label(private val text: String, private val position: Vector2) : Entity() {
    init {
        add(DrawComponent {
            font.draw(it, text, position.x, position.y)
        })
    }

    companion object {
        val font = BitmapFont().apply { data.setScale(0.1f) }
    }
}
