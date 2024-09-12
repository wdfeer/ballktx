package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ktx.math.plus
import ktx.math.times
import wdfeer.ballktx.entity.Wall
import kotlin.random.Random

class RoomSystem(engine: Engine) : EntitySystem() {
    var room: Room = Room(engine, engine.getSystem(PhysicsSystem::class.java).world, roomCenter, roomSize)

    companion object {
        const val ROOM_WIDTH = 160f
        const val ROOM_HEIGHT = 100f

        val roomCenter: Vector2 get() = Vector2(0f, 0f)
        private val roomSize: Vector2 get() = Vector2(ROOM_WIDTH, ROOM_HEIGHT)
    }
}

class Room(engine: Engine, world: World, private val position: Vector2, private val size: Vector2) {
    private fun Engine.addWall(world: World, position: Vector2, size: Vector2): Wall =
        Wall(world, position, size).also { this.addEntity(it) }

    val walls: List<Wall> = spawnWalls(engine, world, position, size)

    private val wallThickness = 1f
    private fun spawnWalls(engine: Engine, world: World, position: Vector2, size: Vector2): List<Wall> {
        val width = size.x
        val height = size.y
        return listOf(
            engine.addWall( // Left wall
                world,
                Vector2(position.x - width / 2 - wallThickness / 2, position.y),
                Vector2(wallThickness, height)
            ),
            engine.addWall( // Right wall
                world,
                Vector2(position.x + width / 2 + wallThickness / 2, position.y),
                Vector2(wallThickness, height)
            ),
            engine.addWall( // Bottom wall
                world,
                Vector2(position.x, position.y - height / 2 - wallThickness / 2),
                Vector2(width, wallThickness)
            ),
            engine.addWall( // Top wall
                world,
                Vector2(position.x, position.y + height / 2 + wallThickness / 2),
                Vector2(width, wallThickness)
            )
        )
    }

    fun getRandomPosition() = position + size * Vector2(Random.nextFloat() - 0.5f, Random.nextFloat() - 0.5f)
}
