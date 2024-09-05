package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector2

class AccelerationComponent : Component {
    var acceleration = Vector2()

    companion object {
        val MAPPER: ComponentMapper<AccelerationComponent> = ComponentMapper.getFor(AccelerationComponent::class.java)
    }
}
