package info.u_team.u_team_core.item.tool;

import info.u_team.u_team_core.api.IToolMaterial;
import info.u_team.u_team_core.api.IToolMaterial.Tools;
import info.u_team.u_team_core.api.registry.IUItem;
import net.minecraft.item.*;
import net.minecraftforge.common.ToolType;

public class UItemPickaxe extends ItemPickaxe implements IUItem {
	
	protected final String name;
	
	public UItemPickaxe(String name, Properties properties, IToolMaterial material) {
		this(name, null, properties, material);
	}
	
	public UItemPickaxe(String name, ItemGroup group, Properties properties, IToolMaterial material) {
		super(material, material.getAdditionalDamage(Tools.PICKAXE), material.getAttackSpeed(Tools.PICKAXE), applyToolType(group == null ? properties : properties.group(group), material));
		this.name = name;
	}
	
	@Override
	public String getEntryName() {
		return name;
	}
	
	private static Properties applyToolType(Properties properties, IToolMaterial material) {
		return properties.addToolType(ToolType.PICKAXE, material.getHarvestLevel());
	}
}
