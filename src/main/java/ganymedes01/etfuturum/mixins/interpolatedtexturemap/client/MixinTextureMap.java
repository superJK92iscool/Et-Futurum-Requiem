package ganymedes01.etfuturum.mixins.interpolatedtexturemap.client;

import com.google.gson.JsonObject;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.SimpleResource;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(TextureMap.class)
public abstract class MixinTextureMap extends AbstractTexture implements ITickableTextureObject, IIconRegister {

	@Shadow
	@Final
	private int textureType;

	@Shadow
	@Final
	private Map mapRegisteredSprites;

	@Inject(method = "registerIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;<init>(Ljava/lang/String;)V"), cancellable = true)
	private void registerInterpolatedIcon(String textureName, CallbackInfoReturnable<IIcon> cir) {
		try {
			String prefixedTextureName;
			String unprefixedName;
			String domain;
			if (textureName.contains(":")) {
				domain = textureName.split(":")[0];
				unprefixedName = textureName.substring(textureName.indexOf(":") + 1);
			} else {
				domain = "minecraft";
				unprefixedName = textureName;
			}
			prefixedTextureName = domain + ":textures/" + (textureType == 0 ? "blocks" : "items") + "/" + unprefixedName + ".png";
			IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(prefixedTextureName));
			if (resource instanceof SimpleResource) {
				//This returns IMetadataSections, which seems to already remove unused mcmeta fields in 1.7.10
				//I'm just running this to poplate the mcmetaJson field more easily; this does it for us
				resource.getMetadata("");
				JsonObject mcmetaJson = ((SimpleResource) resource).mcmetaJson;
				JsonObject animationJson;
				if (mcmetaJson != null && (animationJson = mcmetaJson.getAsJsonObject("animation")) != null && animationJson.getAsJsonPrimitive("interpolate").getAsBoolean()) {
					InterpolatedIcon interpolatedIcon = new InterpolatedIcon(textureName);
					mapRegisteredSprites.put(textureName, interpolatedIcon);
					cir.setReturnValue(interpolatedIcon);
				}
			}
		} catch (Exception ignored) {/*Should quietly fail, no need to failhard*/}
	}
}
