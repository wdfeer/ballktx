package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

class Corridor(val engine: Engine, private val world: World, center: Vector2, size: Vector2) : Interior(center, size) {
    init {
        walls.apply {
            add(engine.addWall(
                world,
                Vector2(center.x, center.y + size.y / 2 + WALL_THICKNESS / 2),
                Vector2(size.x, WALL_THICKNESS)
            ))

            add(engine.addWall(
                world,
                Vector2(center.x, center.y - size.y / 2 - WALL_THICKNESS / 2),
                Vector2(size.x, WALL_THICKNESS)
            ))
        }
    }
}
