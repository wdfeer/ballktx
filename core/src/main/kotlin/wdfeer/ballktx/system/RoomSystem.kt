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

class Room(val engine: Engine, private val world: World, val center: Vector2, val size: Vector2, openLeftWall: Boolean = false) {
    private fun Engine.addWall(world: World, position: Vector2, size: Vector2): Wall =
        Wall(world, position, size).also { this.addEntity(it) }
    private fun removeWall(wall: Wall) {
        walls.remove(wall)
        engine.removeEntity(wall)
        world.destroyBody(wall.body)
    }

    private val walls: MutableList<Wall> = spawnWalls(engine, world, center, size, openLeftWall).toMutableList()

    private val wallThickness = 1f
    private fun spawnWalls(engine: Engine, world: World, center: Vector2, size: Vector2, openLeftWall: Boolean): List<Wall> {
        val width = size.x
        val height = size.y
        val wallList = mutableListOf<Wall>()

        // Left wall
        if (openLeftWall) {
            val wallHeight = height / 3
            wallList.add(engine.addWall(
                world,
                Vector2(center.x - width / 2 - wallThickness / 2, center.y - height / 3),
                Vector2(wallThickness, wallHeight)
            ))
            wallList.add(engine.addWall(
                world,
                Vector2(center.x - width / 2 - wallThickness / 2, center.y + height / 3),
                Vector2(wallThickness, wallHeight)
            ))
        } else {
            wallList.add(engine.addWall(
                world,
                Vector2(center.x - width / 2 - wallThickness / 2, center.y),
                Vector2(wallThickness, height)
            ))
        }

        // Right wall
        val rightWallHeight = height / 3
        wallList.add(engine.addWall(
            world,
            Vector2(center.x + width / 2 + wallThickness / 2, center.y - height / 3),
            Vector2(wallThickness, rightWallHeight)
        ))
        wallList.add(engine.addWall(
            world,
            Vector2(center.x + width / 2 + wallThickness / 2, center.y),
            Vector2(wallThickness, rightWallHeight)
        ))
        wallList.add(engine.addWall(
            world,
            Vector2(center.x + width / 2 + wallThickness / 2, center.y + height / 3),
            Vector2(wallThickness, rightWallHeight)
        ))

        // Bottom wall
        wallList.add(engine.addWall(
            world,
            Vector2(center.x, center.y - height / 2 - wallThickness / 2),
            Vector2(width, wallThickness)
        ))

        // Top wall
        wallList.add(engine.addWall(
            world,
            Vector2(center.x, center.y + height / 2 + wallThickness / 2),
            Vector2(width, wallThickness)
        ))

        return wallList
    }

    fun connect(other: Room) {
        // Remove middle right wall of this room
        val middleRightWall = walls.find { it.position.x > center.x && it.position.y == center.y }
        if (middleRightWall != null) {
            removeWall(middleRightWall)
        }

        val corridorHeight = size.y / 3
        val corridorCenter = Vector2(
            (center.x + other.center.x) / 2,
            center.y
        )

        val corridorWalls = listOf(
            engine.addWall(
                world,
                Vector2(corridorCenter.x, corridorCenter.y + corridorHeight / 2 + wallThickness / 2),
                Vector2(other.center.x - center.x - size.x, wallThickness)
            ),
            engine.addWall(
                world,
                Vector2(corridorCenter.x, corridorCenter.y - corridorHeight / 2 - wallThickness / 2),
                Vector2(other.center.x - center.x - size.x, wallThickness)
            )
        )

        walls.addAll(corridorWalls)
    }

    fun getRandomPosition() = center + size * Vector2(Random.nextFloat() - 0.5f, Random.nextFloat() - 0.5f)
}
