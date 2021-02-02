package ganymedes01.etfuturum.client.skins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import net.minecraft.entity.player.EntityPlayer;

public class ThreadCheckAlex extends Thread {
	EntityPlayer player;
	public void startWithArgs(EntityPlayer mcplayer) {
		player = mcplayer;
		start();
	}
	
	public void run() {
		boolean isAlex = false;
		if(player.getUniqueID() == null) {
			isAlex = false;
		} else {
			InputStream is = null;
			try {
				is = new URL("https://sessionserver.mojang.com/session/minecraft/profile/"
			+ player.getUniqueID().toString().replaceAll("-", "")).openStream();
			      BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			      String jsonText = readAll(rd);
//			      System.out.println(jsonText);
			      String base64 = jsonText.substring(jsonText.indexOf("\"value\" : \""));
			      base64 = base64.replace("\"value\" : \"", "");
			      base64 = base64.substring(0, base64.indexOf('"'));
//			      System.out.println(Base64.base64Decode(base64));
			      if(new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8).contains("\"model\" : \"slim\"")) {
			    	  isAlex = true;
			      }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		PlayerModelManager.alexCache.put(player, isAlex);
//		nbt.setBoolean(SetPlayerModelCommand.MODEL_KEY, isAlex);
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
