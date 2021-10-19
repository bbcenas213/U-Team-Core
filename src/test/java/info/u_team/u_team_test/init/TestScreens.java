package info.u_team.u_team_test.init;

import info.u_team.u_team_core.event.RegisterMenuScreensEvent;
import info.u_team.u_team_test.screen.BasicEnergyCreatorScreen;
import info.u_team.u_team_test.screen.BasicFluidInventoryScreen;
import info.u_team.u_team_test.screen.BasicTileEntityScreen;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestScreens {
	
	private static void register(RegisterMenuScreensEvent event) {
		event.registerScreen(TestMenus.BASIC_BLOCK_ENTITY, BasicTileEntityScreen::new);
		event.registerScreen(TestMenus.BASIC_ENERGY_CREATOR, BasicEnergyCreatorScreen::new);
		event.registerScreen(TestMenus.BASIC_FLUID_INVENTORY, BasicFluidInventoryScreen::new);
	}
	
	public static void registerMod(IEventBus bus) {
		bus.addListener(TestScreens::register);
	}
}
