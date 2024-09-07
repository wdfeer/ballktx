package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import ktx.assets.disposeSafely
import wdfeer.ballktx.component.BodyComponent
import wdfeer.ballktx.component.EnemyComponent

class Enemy(world: World, pos: Vector2) : Entity() {
    init {
        val shape = PolygonShape().apply { setAsBox(1f, 2.5f) }

        add(BodyComponent(world, pos, BodyDef.BodyType.KinematicBody) {
            createFixture(shape, 0.5f)
            userData = this@Enemy
        })

        shape.disposeSafely()

        add(EnemyComponent())
    }
}
