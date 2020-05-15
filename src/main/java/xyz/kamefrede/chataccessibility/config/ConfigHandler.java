package xyz.kamefrede.chataccessibility.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Formatting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigHandler {
	private static final transient Path configFile = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("chataccessibility.json");
	private static final transient Logger LOGGER = LogManager.getLogger(ConfigHandler.class);

	private ConfigHandler() {}

	private static transient ConfigHandler INSTANCE = null;

	public static ConfigHandler getInstance() {
		if (INSTANCE == null) {
			Gson gson = new Gson();
			try (FileReader reader = new FileReader(configFile.toFile())) {
				INSTANCE = gson.fromJson(reader, ConfigHandler.class);
			} catch (FileNotFoundException ignored) {
				// Do nothing!
			} catch (IOException e) {
				LOGGER.error("Failed to read configuration", e);
			}
			if (INSTANCE == null) {
				INSTANCE = new ConfigHandler();
				INSTANCE.save();
			}
		}
		return INSTANCE;
	}

	private String playerNameColor  = Formatting.BLUE.getName();
	private String chatColor = Formatting.WHITE.getName();

	public void cycleNameColor(){
		playerNameColor = cycleTextFormatting(playerNameColor);
		save();
	}

	public void cycleChatColor(){
		chatColor = cycleTextFormatting(chatColor);
		save();
	}

	public Formatting getChatColor() {
		return Formatting.byName(chatColor) == null ? Formatting.BLUE : Formatting.byName(chatColor);
	}

	public Formatting getPlayerNameColor() {
		return Formatting.byName(playerNameColor) == null ? Formatting.BLUE : Formatting.byName(playerNameColor);
	}

	private String cycleTextFormatting(String formattingName){
		Formatting formatting = Formatting.byName(formattingName) == null ? Formatting.BLUE : Formatting.byName(formattingName);
		if(formatting.ordinal() + 1 == Formatting.values().length){
			return Formatting.values()[0].getName();
		}
		return Formatting.values()[formatting.ordinal() + 1].getName();
	}

	public void save(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter(configFile.toFile())) {
			gson.toJson(this, ConfigHandler.class, writer);
		} catch (IOException e) {
			LOGGER.error("Failed to save configuration", e);
		}
	}
}
