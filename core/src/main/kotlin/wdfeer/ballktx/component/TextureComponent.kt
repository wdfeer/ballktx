package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TextureComponent(val texture: TextureRegion?) : Component {
    companion object {
        val MAPPER: ComponentMapper<TextureComponent> = ComponentMapper.getFor(TextureComponent::class.java)
    }
}
