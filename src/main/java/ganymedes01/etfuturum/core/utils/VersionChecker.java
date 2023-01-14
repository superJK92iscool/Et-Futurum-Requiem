package ganymedes01.etfuturum.core.utils;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ForgeHooks;
/**
 * Adapted from Jabelar's tutorials
 * Taken from VillageNames with permission
 * http://jabelarminecraft.blogspot.com/p/minecraft-forge-1721710-making-mod.html
 * Parallel threading provided by Roadhog360
 * @author AstroTibs
 */

public class VersionChecker extends Thread
{
	public static VersionChecker instance = new VersionChecker();
	
	private static boolean isLatestVersion = false;
	private static boolean warnaboutfailure = false;
    private static String latestVersion = "";
    private static boolean isUpdateCheckFinished = false;
    private static boolean quitChecking = false;
    private static boolean hasThreadStarted = false;
    private static Set<String> downloadURLs = new HashSet<String>();
    
	@Override
	public void run()
	{
        InputStream in = null;
        
        try
        {
        	URL url = new URL(Reference.VERSION_URL);
            in = url.openStream();
        } 
        catch (Exception e)
        {
        	if (!warnaboutfailure)
        	{
            	Logger.error("Could not connect with server to compare " + Reference.MOD_NAME + " version");
        		Logger.error("Check for new versions at the following websites:");
        		Logger.error(Reference.MODRINTH_URL);
        		Logger.error(Reference.CURSEFORGE_URL);
        		Logger.error(Reference.GITHUB_URL);
            	warnaboutfailure=true;
        	}
        }
        
        try
        {
        	List<String> lines = IOUtils.readLines(in, Charset.defaultCharset());
            latestVersion = lines.get(0);
            for(int i = 1; i < lines.size(); i++) {
            	if(!lines.get(i).startsWith("#")) {
                	downloadURLs.add(lines.get(i));
            	}
            }
        }
        catch (Exception e)
        {
        	if (!warnaboutfailure)
        	{
        		Logger.error("Failed to compare " + Reference.MOD_NAME + " version");
        		Logger.error(Reference.MODRINTH_URL);
        		Logger.error(Reference.CURSEFORGE_URL);
        		Logger.error(Reference.GITHUB_URL);
        		warnaboutfailure=true;
        	}
        }
        finally
        {
            IOUtils.closeQuietly(in);
        }
        
        isLatestVersion = Reference.VERSION_NUMBER.equals(latestVersion);
        
        if (!this.isLatestVersion() && !latestVersion.equals("") && !latestVersion.equals(null))
        {
        	Logger.info("This version of "+Reference.MOD_NAME+" (" + Reference.VERSION_NUMBER + ") differs from the latest version: " + latestVersion);
        }
        
        isUpdateCheckFinished = true;
    }
	
    public boolean isLatestVersion()
    {
    	return isLatestVersion;
    }
    
    public String getLatestVersion()
    {
    	return latestVersion;
    }
	
    /**
     * PlayerTickEvent is going to be used for version checking.
     * @param event
     */
    
    @SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
    public void onPlayerTickEvent(PlayerTickEvent event)
    {
    	// Used to repeat the version check
    	if (
    			(latestVersion.equals(null) || latestVersion.equals(""))
    			&& !warnaboutfailure // Skip the "run" if a failure was detected
    			&& !hasThreadStarted
    			)
    	{
    		start();
    		hasThreadStarted=true;
    	}
    	
    	if (
    			event.player.ticksExisted>=200
    			&& !quitChecking
    			&& isUpdateCheckFinished) 
    	{
    		Logger.error(Reference.MOD_NAME + " version check failed.");
    		Logger.error("Check for new versions at the following websites:");
    		Logger.error(Reference.MODRINTH_URL);
    		Logger.error(Reference.CURSEFORGE_URL);
    		Logger.error(Reference.GITHUB_URL);
    		quitChecking=true;
    	}
    	
        if (
        		event.player.worldObj.isRemote
        		&& event.phase == Phase.END // Stops doubling the checks unnecessarily
        		&& event.player.ticksExisted>=40
        		&& isUpdateCheckFinished
        		&& !quitChecking
        		)
        {
        	// Ordinary version checker
        	if (
            		!instance.isLatestVersion()
            		&& !latestVersion.equals(null)
            		&& !latestVersion.equals("")
        			)
        	{
                quitChecking=true;
                
                event.player.addChatComponentMessage(
                		new ChatComponentText(
                				EnumChatFormatting.RESET + " version " + EnumChatFormatting.YELLOW + this.getLatestVersion() + EnumChatFormatting.RESET +
                				" is available! Get it at the following links:"
                		 ));
                for(String url : downloadURLs) {
                    event.player.addChatComponentMessage(ForgeHooks.newChatWithLinks(url));
                }
        	}
        }
        
        if (quitChecking)
        {
    		FMLCommonHandler.instance().bus().unregister(instance);
    		return;
        }
    }
}
