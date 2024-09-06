package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import ktx.assets.disposeSafely
import wdfeer.ballktx.component.BodyComponent

class Ball(world: World, pos: Vector2) : Entity() {
    init {
        val circle = CircleShape().apply {
            radius = 0.5f
        }

        add(BodyComponent(world, pos, BodyDef.BodyType.DynamicBody) {
            createFixture(FixtureDef().apply {
                this.shape = circle
                density = 0.5f
                friction = 0.1f
                restitution = 1f
            })
        })

        circle.disposeSafely()
    }
}
