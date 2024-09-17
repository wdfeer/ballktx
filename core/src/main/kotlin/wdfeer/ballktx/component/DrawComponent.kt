package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.ashley.get

class DrawComponent(val draw: (SpriteBatch) -> Unit) : Component {
    companion object {
        private val MAPPER = ComponentMapper.getFor(DrawComponent::class.java)
        val Entity.draw: ((SpriteBatch) -> Unit)?
            get() = this[MAPPER]?.draw
    }
}
