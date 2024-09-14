package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import ktx.ashley.get

class DrawComponent(val draw: () -> Unit) : Component {
    companion object {
        private val MAPPER = ComponentMapper.getFor(DrawComponent::class.java)
        val Entity.draw: (() -> Unit)?
            get() = this[MAPPER]?.draw
    }
}
