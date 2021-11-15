package ganymedes01.etfuturum.client.skins;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayer;

public class ThreadCheckAlex extends Thread {
	
	UUID uuid;
	private boolean connectFailedWarning;
	
	public void startWithArgs(UUID uuid) {
		this.uuid = uuid;
		start();
	}
	
	@Override
	public void run() {
		boolean isAlex = false;
		try {
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			JsonObject json = new Gson().fromJson(new InputStreamReader(new BufferedInputStream(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replaceAll("-", "")).openStream())), 
					JsonObject.class);
			JsonArray jsonArray = json.getAsJsonArray("properties");
			
			  if(!jsonArray.get(0).getAsJsonObject().has("value")) {
				  isAlex = false;
			  } else {
				  JsonObject props = new Gson().fromJson(new String(Base64.getDecoder().decode(jsonArray.get(0).getAsJsonObject().get("value").getAsString()), StandardCharsets.UTF_8),
						  JsonObject.class);
				  isAlex = props.getAsJsonObject("textures").getAsJsonObject("SKIN").getAsJsonObject("metadata").get("model").getAsString().equals("slim");
			  }

			  PlayerModelManager.alexCache.put(uuid, isAlex);
		} catch (Exception e) {
			if(!connectFailedWarning && e instanceof SocketException) {
				System.out.println("Failed to connect to the Mojang API while checking if a skin was alex! Are you connected to the internet? Is something blocking the connection to it?");
				e.printStackTrace();
				connectFailedWarning = true;
			}
		}
		
		PlayerModelManager.alexCache.put(uuid, isAlex);
//      nbt.setBoolean(SetPlayerModelCommand.MODEL_KEY, isAlex);
	}

	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
