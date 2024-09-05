package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import wdfeer.ballktx.component.AccelerationComponent
import wdfeer.ballktx.component.PositionComponent
import wdfeer.ballktx.component.TextureComponent
import wdfeer.ballktx.component.VelocityComponent

class Ball : Entity() {
    init {
        add(TextureComponent(null)) // TODO: Fetch a texture
        add(PositionComponent(Vector2(320f, 240f)))
        add(VelocityComponent())
        add(AccelerationComponent())
    }
}
