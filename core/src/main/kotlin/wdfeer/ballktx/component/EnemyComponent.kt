package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.math.Vector2

class EnemyComponent : Component {
    var moveTarget: Vector2 = Vector2.Zero

    companion object {
        val MAPPER: ComponentMapper<EnemyComponent> = ComponentMapper.getFor(EnemyComponent::class.java)
    }
}
