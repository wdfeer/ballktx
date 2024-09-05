package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector2

class PositionComponent(var pos: Vector2) : Component {
    companion object {
        val MAPPER: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
    }
}
