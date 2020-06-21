package info.u_team.u_team_core.util.registry;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.*;

public class CommonDeferedRegister<T extends IForgeRegistryEntry<T>> {
	
	public static <B extends IForgeRegistryEntry<B>> CommonDeferedRegister<B> create(Class<B> base, String modid) {
		return new CommonDeferedRegister<B>(base, modid);
	}
	
	public static <B extends IForgeRegistryEntry<B>> CommonDeferedRegister<B> create(IForgeRegistry<B> registry, String modid) {
		return new CommonDeferedRegister<B>(registry, modid);
	}
	
	private final Class<T> superType;
	private final String modid;
	private final Map<RegistryObject<T>, Supplier<? extends T>> entries = new LinkedHashMap<>();
	private final Set<RegistryObject<T>> entriesView = Collections.unmodifiableSet(entries.keySet());
	
	private IForgeRegistry<T> type;
	private Supplier<RegistryBuilder<T>> registryFactory;
	
	protected CommonDeferedRegister(IForgeRegistry<T> registry, String modid) {
		this(registry.getRegistrySuperType(), modid);
		this.type = registry;
	}
	
	protected CommonDeferedRegister(Class<T> base, String modid) {
		this.superType = base;
		this.modid = modid;
	}
	
	/**
	 * Adds a new supplier to the list of entries to be registered, and returns a RegistryObject that will be populated with
	 * the created entry automatically.
	 *
	 * @param name The new entry's name, it will automatically have the modid prefixed.
	 * @param sup A factory for the new entry, it should return a new instance every time it is called.
	 * @return A RegistryObject that will be updated with when the entries in the registry change.
	 */
	@SuppressWarnings("unchecked")
	public <I extends T> RegistryObject<I> register(final String name, final Supplier<? extends I> sup) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(sup);
		final ResourceLocation key = new ResourceLocation(modid, name);
		
		RegistryObject<I> ret;
		if (this.type != null)
			ret = RegistryObject.of(key, this.type);
		else if (this.superType != null)
			ret = RegistryObject.of(key, this.superType, this.modid);
		else
			throw new IllegalStateException("Could not create RegistryObject in DeferredRegister");
		
		if (entries.putIfAbsent((RegistryObject<T>) ret, () -> sup.get().setRegistryName(key)) != null) {
			throw new IllegalArgumentException("Duplicate registration " + name);
		}
		
		return ret;
	}
	
	/**
	 * For custom registries only, fills the {@link #registryFactory} to be called later see {@link #register(IEventBus)}
	 * Calls {@link RegistryBuilder#setName} and {@link RegistryBuilder#setType} automatically.
	 *
	 * @param name Path of the registry's {@link ResourceLocation}
	 * @param sup Supplier of the RegistryBuilder that is called to fill {@link #type} during the NewRegistry event
	 * @return A supplier of the {@link IForgeRegistry} created by the builder.
	 */
	public Supplier<IForgeRegistry<T>> makeRegistry(final String name, final Supplier<RegistryBuilder<T>> sup) {
		if (this.superType == null)
			throw new IllegalStateException("Cannot create a registry without specifying a base type");
		if (this.type != null || this.registryFactory != null)
			throw new IllegalStateException("Cannot create a registry for a type that already exists");
		
		this.registryFactory = () -> sup.get().setName(new ResourceLocation(modid, name)).setType(this.superType);
		return () -> this.type;
	}
	
	/**
	 * Adds our event handler to the specified event bus, this MUST be called in order for this class to function. See the
	 * example usage.
	 *
	 * @param bus The Mod Specific event bus.
	 */
	public void register(IEventBus bus) {
		bus.addListener(this::addEntries);
		if (this.type == null) {
			if (this.registryFactory != null)
				bus.addListener(this::createRegistry);
			else
				bus.addListener(EventPriority.LOWEST, this::captureRegistry);
		}
	}
	
	/**
	 * @return The unmodifiable view of registered entries. Useful for bulk operations on all values.
	 */
	public Collection<RegistryObject<T>> getEntries() {
		return entriesView;
	}
	
	private void addEntries(RegistryEvent.Register<?> event) {
		if (this.type != null && event.getGenericType() == this.type.getRegistrySuperType()) {
			@SuppressWarnings("unchecked")
			IForgeRegistry<T> reg = (IForgeRegistry<T>) event.getRegistry();
			for (Entry<RegistryObject<T>, Supplier<? extends T>> e : entries.entrySet()) {
				reg.register(e.getValue().get());
				e.getKey().updateReference(reg);
			}
		}
	}
	
	private void createRegistry(RegistryEvent.NewRegistry event) {
		this.type = this.registryFactory.get().create();
	}
	
	private void captureRegistry(RegistryEvent.NewRegistry event) {
		if (this.superType != null) {
			this.type = RegistryManager.ACTIVE.getRegistry(this.superType);
			if (this.type == null)
				throw new IllegalStateException("Unable to find registry for type " + this.superType.getName() + " for modid \"" + modid + "\" after NewRegistry event");
		} else
			throw new IllegalStateException("Unable to find registry for mod \"" + modid + "\" No lookup criteria specified.");
	}
}