package ganymedes01.etfuturum.api.mappings;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class RegistryMapping<T> {

	private final T object;
	private final transient int meta;

	/**
	 * Used by certain areas of Et Futurum to store a block and meta mapping that can be matched with a new instance.
	 * Used by raw ores and deepslate generation.
	 */
	public RegistryMapping(T obj, int meta) {
		if (!(obj instanceof Block) && !(obj instanceof Item)) {
			throw new IllegalArgumentException("RegistryMapping must be either an item or a block!");
		}
		object = obj;
		if (object instanceof Item || meta == OreDictionary.WILDCARD_VALUE) {
			this.meta = meta;
		} else {
			this.meta = meta & 15;
		}
	}

	public T getObject() {
		return object;
	}

	public int getMeta() {
		return meta;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof RegistryMapping<?> && object == ((RegistryMapping<?>) obj).object && (meta == OreDictionary.WILDCARD_VALUE || ((RegistryMapping<?>) obj).meta == OreDictionary.WILDCARD_VALUE || meta == ((RegistryMapping<?>) obj).meta);
	}

	@Override
	public int hashCode() {
		return object.hashCode();
	}
}
