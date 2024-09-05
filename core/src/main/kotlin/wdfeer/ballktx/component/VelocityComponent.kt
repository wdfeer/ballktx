package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector2

class VelocityComponent : Component {
    var velocity = Vector2()

    companion object {
        val MAPPER: ComponentMapper<VelocityComponent> = ComponentMapper.getFor(VelocityComponent::class.java)
    }
}
