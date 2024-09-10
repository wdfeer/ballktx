package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import ktx.math.minus
import ktx.math.plus
import ktx.math.times
import wdfeer.ballktx.component.BodyComponent
import wdfeer.ballktx.component.EnemyComponent
import kotlin.random.Random

class EnemySystem : IteratingSystem(Family.all(EnemyComponent::class.java, BodyComponent::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val body = entity.getComponent(BodyComponent::class.java).body
        val enemyComponent = entity.getComponent(EnemyComponent::class.java)

        if (body.position.minus(enemyComponent.moveTarget).len() < 1f || enemyComponent.moveTarget == Vector2.Zero) {
            if (body.linearVelocity.len() > 0.2f) body.linearVelocity *= 0.8f

            enemyComponent.moveTarget = CameraManager.camera.position.run {
                Vector2(x, y)
            } + Vector2(Random.nextFloat() * 100f - 50f, Random.nextFloat() * 50f - 25f)
        }

        val direction = (enemyComponent.moveTarget - body.position).nor()
        if ((body.linearVelocity.nor() - direction).len() != 0f) {
            body.linearVelocity = direction * 10f
        }
    }
}
