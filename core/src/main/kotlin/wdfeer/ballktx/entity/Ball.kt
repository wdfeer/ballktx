package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.World
import ktx.assets.disposeSafely
import wdfeer.ballktx.component.DynamicBodyComponent

class Ball(world: World, pos: Vector2) : Entity() {
    init {
        CircleShape().apply { radius = 20f }.run {
            add(DynamicBodyComponent(world, pos, this))
            disposeSafely()
        }
    }
}
