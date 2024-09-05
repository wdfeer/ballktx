package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import ktx.assets.toInternalFile
import wdfeer.ballktx.component.AccelerationComponent
import wdfeer.ballktx.component.PositionComponent
import wdfeer.ballktx.component.TextureComponent
import wdfeer.ballktx.component.VelocityComponent

class Ball : Entity() {
    private val image = Texture("ball.png".toInternalFile(), true).apply { setFilter(
        Texture.TextureFilter.Linear,
        Texture.TextureFilter.Linear
    ) }

    init {
        add(TextureComponent(image)) // TODO: Fetch a texture
        add(PositionComponent(Vector2(320f, 240f)))
        add(VelocityComponent())
        add(AccelerationComponent())
    }
}
