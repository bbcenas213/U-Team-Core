package info.u_team.u_team_test.test_multiloader.fabric.init;

import info.u_team.u_team_test.test_multiloader.loot_item_condition.TestEnchantmentLootItemCondition;
import info.u_team.u_team_test.test_multiloader.loot_item_function.TestLootItemFunction;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class FabricTestMultiLoaderLootTableModifications {
	
	private static void modifyLootTable(ResourceKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source) {
		tableBuilder.apply(TestLootItemFunction.builder().when(TestEnchantmentLootItemCondition.create()));
	}
	
	static void register() {
		LootTableEvents.MODIFY.register(FabricTestMultiLoaderLootTableModifications::modifyLootTable);
	}
	
}
