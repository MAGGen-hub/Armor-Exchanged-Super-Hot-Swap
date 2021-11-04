package com.maggen.armorexchangedsuperhotswap;

import java.util.Map;

import com.maggen.armorexchangedsuperhotswap.config.ServerConfig;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventSubscriber {

	@SubscribeEvent
	public static void SwapArmor(final PlayerInteractEvent.RightClickItem event) {
			PlayerEntity player = event.getPlayer();
			if (event.getHand() == Hand.MAIN_HAND) {

				ItemStack stack = event.getItemStack();

				if (isBlacklisted(stack)) {
					return;
				}

				if (ServerConfig.preventCurses.get() && hasCurse(stack)) {
					return;
				}

				EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(stack);

				//if that can't be equiped in armor slot (all non equipable items)
				if (equipmentSlotType.getSlotType() == EquipmentSlotType.Group.HAND) {
					return;
				}

				ItemStack old_worn = player.getItemStackFromSlot(equipmentSlotType);

				if (hasCurse(old_worn)) {
					return; //sorry but now you can't unequip cursed items.
				}
				
				ItemStack new_worn = stack.copy();
				new_worn.setCount(1);

				player.setItemStackToSlot(equipmentSlotType, new_worn);
				stack.setCount(stack.getCount()-1);
				
				//if stack count is not 0 for some reason
				ItemStack old_items = stack.copy();
				
				if (!old_worn.isEmpty()) {						
					stack.setCount(0); //old stack is not valuable any more (we don't want to get 64 helmets (even if it happens only in creative mode))
					player.setHeldItem(event.getHand(), old_worn);

					if(old_items.getCount() > 0) { //if player held a stack of mob heads or something
						if(!player.addItemStackToInventory(old_items)) {

							player.dropItem(old_items,false,false); //drop items if has no place for it in inventory
						}
					}
				}
			}
	}

	private static boolean hasCurse(ItemStack stack) {
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		if (enchantments.containsKey(Enchantments.BINDING_CURSE)
				|| enchantments.containsKey(Enchantments.VANISHING_CURSE)) {
			return true;
		}
		return false;
	}


	private static boolean isBlacklisted(ItemStack stack) {
		for (String registryName : ServerConfig.itemBlacklist.get()) {
			if (stack.getItem().getRegistryName().toString().equals(registryName)) {
				return true;
			}
		}
		return false;
	}

}
