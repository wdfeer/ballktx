package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.Shape
import com.badlogic.gdx.physics.box2d.World

class DynamicBodyComponent(world: World, pos: Vector2, shape: Shape) : Component {
    val bodyDef = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
        position.set(pos)
    }

    val body = world.createBody(bodyDef).apply {
        createFixture(FixtureDef().apply {
            this.shape = shape
            density = 0.5f
            friction = 0.1f
            restitution = 1f
        })
    }
}
