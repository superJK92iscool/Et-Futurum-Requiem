package ganymedes01.etfuturum.configuration.configs;

import com.google.common.collect.Lists;
import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.List;

public class ConfigExperiments extends ConfigBase {

	private static final String catExperiments = "EXPERIMENTAL FEATURES -- TREAD CAREFULLY";
	private List<String> enabledFeatures = Lists.newArrayList();

	public ConfigExperiments(File file) {
		super(file);
		setCategoryComment(catExperiments,
				"Unfinished features. Handle with care! To automatically enable all of these at once, use \"-Detfuturum.testing=true\" in your program arguments" +
						"\nFor the safety of people playing any packs that include these features, a chat message will be issued when any of them are enabled." +
						"\nNote that when a config option has no comment at all, not even saying what the default value is, that means the option was removed");

		configCats.add(getCategory(catExperiments));
	}

	@Override
	protected void syncConfigOptions() {

	}


	public Property get(String category, String key, String defaultValue, String comment, Property.Type type) {
		Property prop = super.get(category, key, defaultValue, comment, type);
		return prop;
	}
}
