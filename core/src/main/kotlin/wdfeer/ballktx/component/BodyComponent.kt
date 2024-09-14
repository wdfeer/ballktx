package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.get

class BodyComponent(world: World, pos: Vector2, type: BodyType, bodyBuilder: Body.() -> Unit) : Component {
    private val bodyDef = BodyDef().apply {
        this.type = type
        position.set(pos)
    }

    val body: Body = world.createBody(bodyDef).also { bodyBuilder.invoke(it) }

    companion object {
        private val MAPPER: ComponentMapper<BodyComponent> = ComponentMapper.getFor(BodyComponent::class.java)
        val Entity.body: Body? get() = this[MAPPER]?.body
    }
}
