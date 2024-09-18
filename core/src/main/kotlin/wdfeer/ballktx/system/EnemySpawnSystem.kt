package wdfeer.ballktx.system

import com.badlogic.ashley.systems.IntervalSystem
import wdfeer.ballktx.entity.Enemy
import wdfeer.ballktx.system.PhysicsSystem.Companion.world

class EnemySpawnSystem : IntervalSystem(0.2f) {
    override fun updateInterval() {
        val roomSystem = engine.getSystem(RoomSystem::class.java)

        if (!roomsComplete.contains(roomSystem.lastRoom)) {
            spawnEnemies()
            roomsComplete.add(roomSystem.lastRoom)
        }
        else if (engine.entities.none { it is Enemy }) {
            roomSystem.createNextRoom()
        }
    }

    private val roomsComplete: MutableList<Room> = mutableListOf()

    private fun spawnEnemies() {
        repeat(enemyCount) {
            val pos = engine.getSystem(RoomSystem::class.java).lastRoom.getRandomPosition()
            engine.addEntity(Enemy(engine.world, pos))
        }
    }

    val enemyCount get() = 3 + 5 * roomsComplete.count()
}
