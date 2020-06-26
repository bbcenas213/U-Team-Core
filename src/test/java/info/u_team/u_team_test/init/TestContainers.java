package info.u_team.u_team_test.init;

import info.u_team.u_team_core.containertype.UContainerType;
import info.u_team.u_team_test.TestMod;
import info.u_team.u_team_test.container.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class TestContainers {
	
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, TestMod.MODID);
	
	public static final RegistryObject<UContainerType<BasicTileEntityContainer>> BASIC = CONTAINER_TYPES.register("basic", () -> new UContainerType<>(BasicTileEntityContainer::new));
	
	public static final RegistryObject<ContainerType<BasicEnergyCreatorContainer>> BASIC_ENERGY_CREATOR = CONTAINER_TYPES.register("energy_creator", () -> new UContainerType<>(BasicEnergyCreatorContainer::new));
	
	public static final RegistryObject<ContainerType<BasicFluidInventoryContainer>> BASIC_FLUID_INVENTORY = CONTAINER_TYPES.register("fluid_inventory", () -> new UContainerType<>(BasicFluidInventoryContainer::new));
	
	public static void register(IEventBus bus) {
		CONTAINER_TYPES.register(bus);
	}
}
