package wdfeer.ballktx.system

import com.badlogic.ashley.systems.IntervalSystem
import wdfeer.ballktx.entity.Enemy

class EnemySpawnSystem : IntervalSystem(0.2f) {
    override fun updateInterval() {
        val roomSystem = engine.getSystem(RoomSystem::class.java)

        if (!roomsComplete.contains(roomSystem.currentRoom)) {
            spawnEnemies()
            roomsComplete.add(roomSystem.currentRoom)
        }
        else if (engine.entities.none { it is Enemy }) {
            roomSystem.createNextRoom()
        }
    }

    private val roomsComplete: MutableList<Room> = mutableListOf()

    private fun spawnEnemies() {
        repeat(ENEMY_COUNT) {
            val pos = engine.getSystem(RoomSystem::class.java).currentRoom.getRandomPosition()
            engine.addEntity(Enemy(engine.getSystem(PhysicsSystem::class.java).world, pos))
        }
    }

    companion object {
        const val ENEMY_COUNT = 5
    }
}
