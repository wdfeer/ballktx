package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import ktx.math.plus
import ktx.math.times
import wdfeer.ballktx.entity.Wall
import wdfeer.ballktx.extension.body
import kotlin.random.Random

class RoomSystem(eng: Engine) : EntitySystem() {
    private val Engine.world get() = getSystem(PhysicsSystem::class.java).world

    private val rooms: MutableList<Room> = mutableListOf(Room(eng, eng.world, Vector2.Zero, roomSize))
    val currentRoom: Room get() = rooms.last()

    fun createNextRoom() {
        val room = Room(engine, engine.world, currentRoom.center + Vector2.X * ROOM_WIDTH * 2f, roomSize, true)
        currentRoom.connect(room)
        rooms.add(room)
    }

    companion object {
        const val ROOM_WIDTH = 160f
        const val ROOM_HEIGHT = 100f

        private val roomSize: Vector2 get() = Vector2(ROOM_WIDTH, ROOM_HEIGHT)
    }
}

class Room(val engine: Engine, val world: World, val center: Vector2, val size: Vector2, open: Boolean = false) { // TODO: use the open argument to determine whether the left wall should be split open
    private fun Engine.addWall(world: World, position: Vector2, size: Vector2): Wall =
        Wall(world, position, size).also { this.addEntity(it) }
    private fun removeWall(wall: Wall) {
        walls.remove(wall)
        engine.removeEntity(wall)
        world.destroyBody(wall.body)
    }

    val walls: MutableList<Wall> = spawnWalls(engine, world, center, size, open).toMutableList()

    private val wallThickness = 1f
    private fun spawnWalls(engine: Engine, world: World, center: Vector2, size: Vector2, open: Boolean): List<Wall> {
        val width = size.x
        val height = size.y
        return listOf(
            // TODO: Split left wall into 3 equal walls without the middle one if 'open' is true
            engine.addWall(
                world,
                Vector2(center.x - width / 2 - wallThickness / 2, center.y),
                Vector2(wallThickness, height)
            ),
            // TODO: Split this right wall into 3 equal walls
            engine.addWall(
                world,
                Vector2(center.x + width / 2 + wallThickness / 2, center.y),
                Vector2(wallThickness, height)
            ),
            engine.addWall( // Bottom wall
                world,
                Vector2(center.x, center.y - height / 2 - wallThickness / 2),
                Vector2(width, wallThickness)
            ),
            engine.addWall( // Top wall
                world,
                Vector2(center.x, center.y + height / 2 + wallThickness / 2),
                Vector2(width, wallThickness)
            )
        )
    }

    fun connect(other: Room) {
        TODO("Remove middle right wall of this, then create a corridor connecting the rooms")
    }

    fun getRandomPosition() = center + size * Vector2(Random.nextFloat() - 0.5f, Random.nextFloat() - 0.5f)
}
