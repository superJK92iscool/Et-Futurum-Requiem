package ganymedes01.etfuturum.client.skins;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.minecraft.entity.player.EntityPlayer;

public class ThreadCheckAlex extends Thread {
	EntityPlayer player;
	public void startWithArgs(EntityPlayer mcplayer) {
		player = mcplayer;
		start();
	}
	
	@Override
	public void run() {
		boolean isAlex = false;
		if(player.getUniqueID() == null) {
			isAlex = false;
		} else {
			try {
				System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
				JsonObject json = new Gson().fromJson(new InputStreamReader(new BufferedInputStream(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + player.getUniqueID().toString().replaceAll("-", "")).openStream())), 
						JsonObject.class);
				
				JsonArray jsonArray = json.getAsJsonArray("properties");
				
				  if(!jsonArray.get(0).getAsJsonObject().has("value")) {
					  isAlex = false;
				  } else {
					  JsonObject props = new Gson().fromJson(new String(Base64.getDecoder().decode(jsonArray.get(0).getAsJsonObject().get("value").getAsString()), StandardCharsets.UTF_8),
							  JsonObject.class);
					  isAlex = props.getAsJsonObject("textures").getAsJsonObject("SKIN").getAsJsonObject("metadata").get("model").getAsString().equals("slim");
				  }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		PlayerModelManager.alexCache.put(player, isAlex);
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
