package org.thinkingstudio.ryoamiclights.pride;

import com.google.gson.Gson;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thinkingstudio.ryoamiclights.ModPlatform;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class PrideLoader extends SinglePreparationResourceReloader<List<PrideFlag>> {
	private static final Identifier ID = Identifier.of("pride", "flags");
	private static final Logger LOGGER = LogManager.getLogger("pride");
	private static final Gson GSON = new Gson();
	private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#[0-9a-fA-F]{6}$");

	public static void loadAndApply(ResourceManager manager) {
		applyFlags(loadFlags(manager));
	}

	static class Config {
		String[] flags;
	}

	@Override
	protected List<PrideFlag> prepare(ResourceManager manager, Profiler profiler) {
		return loadFlags(manager);
	}

	@Override
	public void apply(List<PrideFlag> list, ResourceManager manager, Profiler profiler) {
		applyFlags(list);
	}

	public static List<PrideFlag> loadFlags(ResourceManager manager) {
		var flags = new ArrayList<PrideFlag>();

		outer:
		for (var entry : manager.findResources("flags", path -> path.getPath().endsWith(".json")).entrySet()) {
			Identifier id = entry.getKey();
			String[] parts = id.getPath().split("/");
			String name = parts[parts.length - 1];
			name = name.substring(0, name.length() - 5);

			try (var reader = new InputStreamReader(entry.getValue().getInputStream())) {
				PrideFlag.Properties builder = GSON.fromJson(reader, PrideFlag.Properties.class);

				for (String color : builder.colors) {
					if (!HEX_COLOR_PATTERN.matcher(color).matches()) {
						LOGGER.warn("[pride] Malformed flag data for flag " + name + ", " + color
								+ " is not a valid color, must be a six-digit hex color like #FF00FF");
						continue outer;
					}
				}

				var flag = new PrideFlag(name, builder);
				flags.add(flag);
			} catch (Exception e) {
				LOGGER.warn("[pride] Malformed flag data for flag " + name, e);
			}
		}

		var prideFile = new File(ModPlatform.getConfigDir().toFile(), "pride.json");
		if (prideFile.exists()) {
			try (var reader = new FileReader(prideFile)) {
				Config config = GSON.fromJson(reader, Config.class);

				if (config.flags != null) {
					List<String> list = Arrays.asList(config.flags);
					flags.removeIf(flag -> !list.contains(flag.getId()));
				}
			} catch (Exception e) {
				LOGGER.warn("[pride] Malformed flag data for pride.json config");
			}
		} else {
			var id = Identifier.of("pride", "flags.json");

			Optional<Resource> resource = manager.getResource(id);
			if (resource.isPresent()) {
				try (var reader = new InputStreamReader(resource.get().getInputStream())) {
					Config config = GSON.fromJson(reader, Config.class);

					if (config.flags != null) {
						List<String> list = Arrays.asList(config.flags);
						flags.removeIf(flag -> !list.contains(flag.getId()));
					}
				} catch (Exception e) {
					LOGGER.warn("[pride] Malformed flag data for flags.json", e);
				}
			}
		}

		return flags;
	}

	private static void applyFlags(List<PrideFlag> flags) {
		PrideFlags.setFlags(flags);
	}
}
