package com.demoxin.minecraft.moreenchants;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class Enchantment_Agility extends Enchantment {
	public Enchantment_Agility(int fId, int fWeight)
	{
		super(fId, fWeight, EnumEnchantmentType.armor_legs);
		this.setName("agility");
		addToBookList(this);
	}
	
	@Override
	public int getMaxLevel()
    {
        return 1;
    }
	
	@Override
    public int getMinEnchantability(int par1)
    {
		return 10;
    }

    @Override
    public int getMaxEnchantability(int par1)
    {
    	return this.getMinEnchantability(par1) + 40;
    }
    
    @Override
    public boolean canApplyTogether(Enchantment fTest)
    {
    	if(fTest instanceof Enchantment_Agility)
    		return false;
    	return true;
    }
    
    public boolean canApply(ItemStack fTest)
    {
    	if(fTest.getItem() instanceof ItemArmor)
    	{
    		ItemArmor itemArmor = (ItemArmor)fTest.getItem();
    		if(itemArmor.armorType == 2)
    		{
    			return true;
    		}
    	}
        return false;
    }
    
    @ForgeSubscribe
    public void HandleEnchant(LivingAttackEvent fEvent)
    {
    	// We need mobs, players, and arrows.
    	if(fEvent.source.damageType != "player" && fEvent.source.damageType != "mob" && fEvent.source.damageType != "arrow")
			return;
    	
    	if(!(fEvent.entity instanceof EntityLivingBase))
    		return;
    	
    	EntityLivingBase victim = (EntityLivingBase)fEvent.entity;
		ItemStack armorLegs = victim.getCurrentItemOrArmor(2);
				
		if(armorLegs == null)
			return;
				
		// See if we have agility.  Only should be 1 level
		if(EnchantmentHelper.getEnchantmentLevel(MoreEnchants.enchantAgility.effectId, armorLegs) <= 0)
			return;
		
		if(fEvent.entity.worldObj.rand.nextInt(100) < 25)
			fEvent.setCanceled(true);
    }
}