package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import ktx.assets.disposeSafely
import wdfeer.ballktx.component.BodyComponent

class Wall(world: World, pos: Vector2, size: Vector2) : Entity() {
    init {
        val shape = PolygonShape().apply { setAsBox(size.x / 2f, size.y / 2f) }

        add(BodyComponent(world, pos, BodyDef.BodyType.StaticBody) {
            createFixture(shape, 0f)
        })

        shape.disposeSafely()
    }
}

