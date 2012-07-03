package net.aufdemrand.sentry;

import java.util.Random;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.exception.NPCLoadException;
import net.citizensnpcs.api.npc.character.Character;
import net.citizensnpcs.api.npc.character.CharacterFactory;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Owner;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.npc.NPC;

import net.aufdemrand.sentry.Sentry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
// import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
// import org.bukkit.craftbukkit.*;


public class SentryCharacter extends Character {

	static Sentry plugin = (Sentry) Bukkit.getPluginManager().getPlugin("Sentry");

	@Override
	public void load(DataKey arg0) throws NPCLoadException {
		// TODO Auto-generated method stub
		
		
		
	
		
	}

	@Override
	public void save(DataKey arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onRightClick(NPC npc, Player player) {
		if(npc.getCharacter() == CitizensAPI.getCharacterManager().getCharacter("sentry")) {

			
		}
	}


	
	
	
	@Override
	public void onLeftClick(NPC npc, Player player) {
		
				
		if(npc.getCharacter() == CitizensAPI.getCharacterManager().getCharacter("sentry")) {

			ItemStack iteminhand = player.getItemInHand();
			
			double damagemodifer = 1;
			
			if (iteminhand.getType().equals(Material.WOOD_SWORD)) damagemodifer =  1.25;
			if (iteminhand.getType().equals(Material.IRON_SWORD)) damagemodifer =  1.50;
			if (iteminhand.getType().equals(Material.GOLD_SWORD)) damagemodifer =  1.75;
			if (iteminhand.getType().equals(Material.DIAMOND_SWORD)) damagemodifer =  2.0;
			
			Random r = new Random();
			int luckeyhit = r.nextInt(100);
			
			if (luckeyhit < 5) {
				
				damagemodifer =  damagemodifer * 2.00;
				player.sendMessage(ChatColor.RED + "*** You maim the Sentry!");
				npc.getBukkitEntity().playEffect(EntityEffect.HURT);
			}
			else if (luckeyhit < 15) {
		
				damagemodifer =  damagemodifer * 1.50;
				player.sendMessage(ChatColor.GOLD + "*** You dismember the Sentry!");
				npc.getBukkitEntity().playEffect(EntityEffect.HURT);
			}
			else if (luckeyhit < 25) {

				damagemodifer =  damagemodifer * 1.50;
				player.sendMessage(ChatColor.YELLOW + "*** You injure the Sentry!");
				npc.getBukkitEntity().playEffect(EntityEffect.HURT);
			}
			else if (luckeyhit > 95) {

				damagemodifer =  0;
				player.sendMessage(ChatColor.GRAY + "*** You miss the Sentry!");
				
			}

			else npc.getBukkitEntity().playEffect(EntityEffect.HURT);

			Vector newVec = player.getLocation().getDirection().multiply(1.75);
			newVec.setY(newVec.getY()/1.1);
			npc.getBukkitEntity().setVelocity(newVec);
			
			
			int finaldamage = (int) Math.round(damagemodifer);
			

			
			if 	(npc.getBukkitEntity().getHealth() - finaldamage <= 0)  {
				npc.getBukkitEntity().getWorld().playEffect(npc.getBukkitEntity().getLocation(), Effect.POTION_BREAK, 3);
				npc.getBukkitEntity().getWorld().playEffect(npc.getBukkitEntity().getLocation(), Effect.POTION_BREAK, 3);
				npc.getBukkitEntity().getWorld().playEffect(npc.getBukkitEntity().getLocation(), Effect.POTION_BREAK, 3);
				player.sendMessage(ChatColor.GREEN + "*** You lay a mortal blow to the Sentry!");
				npc.getBukkitEntity().getLocation().getWorld().spawn(npc.getBukkitEntity().getLocation(), ExperienceOrb.class).setExperience(5);
				plugin.RespawnSentry.put(npc, System.currentTimeMillis() + 20000);
			
				finaldamage = npc.getBukkitEntity().getHealth();
				npc.getBukkitEntity().damage(finaldamage);
			
			}

			else npc.getBukkitEntity().damage(finaldamage);
		 // npc.getBukkitEntity().damage(finaldamage, player);
			
		}
	}



}

