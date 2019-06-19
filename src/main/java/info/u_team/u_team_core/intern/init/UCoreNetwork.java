package info.u_team.u_team_core.intern.init;

import info.u_team.u_team_core.UCoreMain;
import info.u_team.u_team_core.intern.network.SyncedContainerMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class UCoreNetwork {
	
	public static final String PROTOCOL = "1.14.2-1";
	
	public static final SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(new ResourceLocation(UCoreMain.modid, "network"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);
	
	public static void construct() {
		NETWORK.registerMessage(0, SyncedContainerMessage.class, SyncedContainerMessage::encode, SyncedContainerMessage::decode, SyncedContainerMessage.Handler::handle);
	}
	
}
