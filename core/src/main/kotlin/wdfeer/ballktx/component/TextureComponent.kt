package wdfeer.ballktx.component

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.gdx.graphics.Texture

class TextureComponent(val texture: Texture) : Component {
    companion object {
        val MAPPER: ComponentMapper<TextureComponent> = ComponentMapper.getFor(TextureComponent::class.java)
    }
}
