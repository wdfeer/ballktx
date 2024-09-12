package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.World

class BodyComponent(world: World, pos: Vector2, type: BodyType, bodyBuilder: Body.() -> Unit) : Component {
    private val bodyDef = BodyDef().apply {
        this.type = type
        position.set(pos)
    }

    val body: Body = world.createBody(bodyDef).also { bodyBuilder.invoke(it) }
}
