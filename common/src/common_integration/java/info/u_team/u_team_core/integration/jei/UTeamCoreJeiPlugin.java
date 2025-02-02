package info.u_team.u_team_core.integration.jei;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import info.u_team.u_team_core.UCoreReference;
import info.u_team.u_team_core.util.RegistryUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;

@JeiPlugin
public class UTeamCoreJeiPlugin implements IModPlugin {
	
	private final ResourceLocation id = ResourceLocation.fromNamespaceAndPath(UCoreReference.MODID, "jei");
	
	@Override
	public ResourceLocation getPluginUid() {
		return id;
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		final ClientLevel level = Minecraft.getInstance().level;
		
		final List<ItemStack> items = StreamSupport.stream(RegistryUtil.getBuiltInRegistry(Registries.ITEM).getTagOrEmpty(ItemTags.DYEABLE).spliterator(), false) //
				.map(ItemStack::new) //
				.filter(stack -> {
					final CraftingInput input = CraftingInput.of(2, 1, List.of(new ItemStack(Items.WHITE_DYE), stack));
					return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level).isPresent();
				}).collect(Collectors.toList());
		
		if (!items.isEmpty()) {
			registration.addIngredientInfo(items, VanillaTypes.ITEM_STACK, Component.translatable("jei.uteamcore.dyeable.info"));
		}
	}
	
}
