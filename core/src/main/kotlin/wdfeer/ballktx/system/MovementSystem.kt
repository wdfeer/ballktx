package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.get
import ktx.math.plus
import wdfeer.ballktx.component.AccelerationComponent
import wdfeer.ballktx.component.PositionComponent
import wdfeer.ballktx.component.VelocityComponent

class MovementSystem : IteratingSystem(Family.all(
    PositionComponent::class.java,
    VelocityComponent::class.java,
    AccelerationComponent::class.java
).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val positionComponent = entity[PositionComponent.MAPPER]!!
        val velocityComponent = entity[VelocityComponent.MAPPER]!!
        val accelerationComponent = entity[AccelerationComponent.MAPPER]!!

        velocityComponent.velocity += accelerationComponent.acceleration
        positionComponent.pos += velocityComponent.velocity
    }
}
