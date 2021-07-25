package io.github.ragnaraven.eoarmors.client.render.gui;

import java.util.ArrayList;
import java.util.List;

import io.github.ragnaraven.eoarmors.EnderObsidianArmorsMod;
import io.github.ragnaraven.eoarmors.config.ConfigHolder;
import io.github.ragnaraven.eoarmors.config.ServerConfig;
import io.github.ragnaraven.eoarmors.core.essentials.Ability;
import io.github.ragnaraven.eoarmors.core.essentials.Experience;
import io.github.ragnaraven.eoarmors.core.essentials.Rarity;
import io.github.ragnaraven.eoarmors.network.PacketGuiAbility;
import io.github.ragnaraven.eoarmors.core.util.EAUtils;
import io.github.ragnaraven.eoarmors.core.util.NBTHelper;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;


public class GuiAbilitySelection extends Screen
{
	private Button[] weaponAbilities;
	private Button[] armorAbilities;

	public GuiAbilitySelection(ITextComponent textComponent)
	{
		super(textComponent);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void init(Minecraft minecraft, int i1, int i2)
	{
		super.init(minecraft, i1, i2);
		
		PlayerEntity player = minecraft.player;
	    
	    if (player != null)
	    {
	    	ItemStack stack = player.getMainHandItem();;
	    	
	    	if (stack != ItemStack.EMPTY)
	    	{
	    		if (EAUtils.canEnhanceWeapon(stack.getItem()))
		    	{
		    		weaponAbilities = new Button[Ability.WEAPON_ABILITIES_COUNT];
		    		CompoundNBT nbt = stack.getTag();
		    		
		    		if (nbt != null)
		    		{
		    			int j = 0;
		    			
		    			for (int i = 0; i < weaponAbilities.length; i++)
		    			{
		    				if (Ability.WEAPON_ABILITIES.get(i).getType().equals("active"))
			    			{
		    					weaponAbilities[i] = new ExtendedButton(/*i,*/width / 2 - 215, 100 + (i * 21), 110, 20, new StringTextComponent(I18n.get("eoarmors.ability." + Ability.WEAPON_ABILITIES.get(i).getName())), this::actionPerformed);
		    					j++;
			    			}
		    				else
		    					weaponAbilities[i] = new Button(/*i,*/ width / 2 - 100, 100 + ((i - j) * 21), 110, 20, new StringTextComponent(I18n.get("eoarmors.ability." + Ability.WEAPON_ABILITIES.get(i).getName())), this::actionPerformed);
		    				
		    				addButton(weaponAbilities[i]);
		    				weaponAbilities[i].active = false;
		    			}
		    		}
		    	}
		    	else if (EAUtils.canEnhanceArmor(stack.getItem()))
		    	{
		    		armorAbilities = new Button[Ability.ARMOR_ABILITIES_COUNT];
					CompoundNBT nbt = stack.getTag();

		    		if (nbt != null)
		    		{
		    			int j = 0;
		    			
		    			for (int i = 0; i < armorAbilities.length; i++)
		    			{
		    				if (Ability.ARMOR_ABILITIES.get(i).getType().equals("active"))
			    			{
		    					armorAbilities[i] = new ExtendedButton(/*i, */width / 2 - 215, 100 + (i * 21), 100, 20, new StringTextComponent(I18n.get("eoarmors.ability." + Ability.ARMOR_ABILITIES.get(i).getName())), this::actionPerformed);
		    					j++;
			    			}
		    				else
		    					armorAbilities[i] = new ExtendedButton(/*i, */width / 2 - 100, 100 + ((i - j) * 21), 105, 20, new StringTextComponent(I18n.get("eoarmors.ability." + Ability.ARMOR_ABILITIES.get(i).getName())), this::actionPerformed);
		    				
		    				addButton(armorAbilities[i]);
		    				armorAbilities[i].active = false;
		    			}
		    		}
		    	}
	    	}
	    }
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);

		PlayerEntity player = this.minecraft.player;

		if (player != null)
		{
			ItemStack stack = player.getMainHandItem();

			if (stack != ItemStack.EMPTY)
			{
				if (EAUtils.canEnhance(stack.getItem()))
				{
					CompoundNBT nbt = NBTHelper.loadStackNBT(stack);

					if (nbt != null)
					{
						if (EAUtils.canEnhanceWeapon(stack.getItem()))
						{
							drawStrings(matrixStack, stack, Ability.WEAPON_ABILITIES, nbt);
							displayButtons(weaponAbilities, Ability.WEAPON_ABILITIES, nbt, player);
							drawTooltips(matrixStack, weaponAbilities, Ability.WEAPON_ABILITIES, mouseX, mouseY);
						}
						else if (EAUtils.canEnhanceArmor(stack.getItem()))
						{
							drawStrings(matrixStack, stack, Ability.ARMOR_ABILITIES, nbt);
							displayButtons(armorAbilities, Ability.ARMOR_ABILITIES, nbt, player);
							drawTooltips(matrixStack, armorAbilities, Ability.ARMOR_ABILITIES, mouseX, mouseY);
						}
					}
				}
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	private void actionPerformed(Button button)
	{
		PlayerEntity player = minecraft.player;

		if (player != null)
		{
			ItemStack stack = player.getMainHandItem();
			
			if (stack != ItemStack.EMPTY)
			{
				CompoundNBT nbt = NBTHelper.loadStackNBT(stack);
				
				if (nbt != null)
				{
					if (Experience.getAbilityTokens(nbt) > 0 || player.experienceLevel > 1 || player.isCreative())
					{
						if (EAUtils.canEnhanceWeapon(stack.getItem()))
						{
							for (int i = 0; i < weaponAbilities.length; i++)
							{
								if (button == weaponAbilities[i])
								{
									EnderObsidianArmorsMod.network.sendToServer(new PacketGuiAbility(i));
								}
							}
						}
						else if (EAUtils.canEnhanceArmor(stack.getItem()))
						{
							for (int i = 0; i < armorAbilities.length; i++)
							{
								if (button == armorAbilities[i])
								{
									EnderObsidianArmorsMod.network.sendToServer(new PacketGuiAbility(i));
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Draws the strings for the ability selection screen.
	 * @param stack
	 * @param abilities
	 * @param nbt
	 */
	private void drawStrings(MatrixStack matrixStack, ItemStack stack, ArrayList<Ability> abilities, CompoundNBT nbt)
	{
		Rarity rarity = Rarity.getRarity(nbt);
		
		drawCenteredString(matrixStack, font, stack.getDisplayName().getString(), width / 2, 20, 0xFFFFFF);
		drawString(matrixStack, font, I18n.get("eoarmors.misc.rarity") + ": ", width / 2 - 50, 40, 0xFFFFFF);
		drawString(matrixStack, font, I18n.get("eoarmors.rarity." + rarity.getName()), width / 2 - 15, 40, rarity.getHex());
		drawCenteredString(matrixStack, font, TextFormatting.ITALIC + I18n.get("eoarmors.misc.abilities"), width / 2 - 100, 73, 0xFFFFFF);
		drawCenteredString(matrixStack, font, TextFormatting.GRAY + I18n.get("eoarmors.misc.abilities.tokens") + ": " + TextFormatting.DARK_GREEN + Experience.getAbilityTokens(nbt), width / 2 - 100, 86, 0xFFFFFF);
		drawCenteredString(matrixStack, font, TextFormatting.GOLD + I18n.get("eoarmors.misc.abilities.purchased"), width / 2 + 112, 100, 0xFFFFFF);
		drawCenteredString(matrixStack, font, TextFormatting.BOLD + I18n.get("eoarmors.ability.type.active"), width / 2 + 75, 120, 0xFFFFFF);
		drawCenteredString(matrixStack, font, TextFormatting.BOLD + I18n.get("eoarmors.ability.type.passive"), width / 2 + 150, 120, 0xFFFFFF);
		
		if (Experience.getLevel(nbt) == ConfigHolder.SERVER.maxLevel.get())
		{
			drawString(matrixStack, font, I18n.get("eoarmors.misc.level") + ": " + Experience.getLevel(nbt) + TextFormatting.DARK_RED +" (" + I18n.get("eoarmors.misc.max") + ")", width / 2 - 50, 50, 0xFFFFFF);
			drawString(matrixStack, font, I18n.get("eoarmors.misc.experience") + ": " + Experience.getExperience(nbt), width / 2 - 50, 60, 0xFFFFFF);
		}
		else
		{
			drawString(matrixStack, font, I18n.get("eoarmors.misc.level") + ": " + Experience.getLevel(nbt), width / 2 - 50, 50, 0xFFFFFF);
			drawString(matrixStack, font, I18n.get("eoarmors.misc.experience") + ": " + Experience.getExperience(nbt) + " / " + Experience.getMaxLevelExp(Experience.getLevel(nbt)), width / 2 - 50, 60, 0xFFFFFF);
		}
		
		int j = -1;
		int k = -1;
		
		for (int i = 0; i < abilities.size(); i++)
		{
			if (abilities.get(i).hasAbility(nbt))
			{
				if (abilities.get(i).getType().equals("active"))
				{
					j++;
					drawCenteredString(matrixStack, font, I18n.get(abilities.get(i).getName(nbt)), width / 2 + 75, 135 + (j * 12), abilities.get(i).getHex());
				}
				else if (abilities.get(i).getType().equals("passive"))
				{
					k++;
					drawCenteredString(matrixStack, font, abilities.get(i).getName(nbt), width / 2 + 150, 135 + (k * 12), abilities.get(i).getHex());
				}
			}
		}
	}
	
	/**
	 * Determines which buttons need to be enabled based on available ability tokens and if the
	 * weapon is of a high enough level to enable.
	 * @param buttons
	 * @param abilities
	 * @param nbt
	 */
	private void displayButtons(Button[] buttons, ArrayList<Ability> abilities, CompoundNBT nbt, PlayerEntity player)
	{
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].active = false;
		}
		
		for (int i = 0; i < buttons.length; i++)
		{
			if(!(abilities.get(i).hasAbility(nbt)))
			{
				if(abilities.get(i).hasEnoughExp(player, nbt))
					buttons[i].active = true;
			}
			else if (abilities.get(i).canUpgradeLevel(nbt) && Experience.getAbilityTokens(nbt) >= abilities.get(i).getTier())
				buttons[i].active = true;
			else
				buttons[i].active = false;
		}
	}
	
	private void drawTooltips(MatrixStack matrixStack, Button[] buttons, ArrayList<Ability> abilities, int mouseX, int mouseY)
	{
		PlayerEntity player = this.minecraft.player;
		ItemStack stack = player.getMainHandItem();;
		CompoundNBT nbt = stack.getTag();
		
		for (int i = 0; i < buttons.length; i++)
		{
			if (buttons[i].isHovered())//checker.checkHover(mouseX, mouseY))
			{
				List<StringTextComponent> list = new ArrayList<>();
				list.add(new StringTextComponent(abilities.get(i).getColor() + I18n.get("eoarmors.ability." + abilities.get(i).getName()) + " (" + abilities.get(i).getTypeName() + abilities.get(i).getColor() + ")"));
				list.add(new StringTextComponent(""));
				list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info." + abilities.get(i).getName())));
				list.add(new StringTextComponent(""));
				if (EAUtils.canEnhanceWeapon(stack.getItem()))
				{
					if (i == 0)//FIRE
					{
						float chance = (float) (1.0 / (ConfigHolder.SERVER.firechance.get()))*100;
						float currentduration = (Ability.FIRE.getLevel(nbt) + Ability.FIRE.getLevel(nbt)*4)/4;
						float nextlevelduration = (Ability.FIRE.getLevel(nbt)+1 + (Ability.FIRE.getLevel(nbt)+1)*4)/4;
						int c = (int) chance;
						
						if (!(Ability.FIRE.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
							list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
							list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + nextlevelduration));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration + " " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + (nextlevelduration-currentduration)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration +" "+ I18n.get("eoarmors.abilities.info.seconds")));
								if(!(Ability.FIRE.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 1)//FROST
					{
						float chance = (float) (1.0 / (ConfigHolder.SERVER.frostchance.get()))*100;
						float currentduration = (Ability.FROST.getLevel(nbt) + Ability.FROST.getLevel(nbt)*4)/3;
						float nextlevelduration = (Ability.FROST.getLevel(nbt)+1 + (Ability.FROST.getLevel(nbt)+1)*4)/3;
						int c = (int) chance;
						
						if (!(Ability.FROST.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + nextlevelduration));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration + " " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + (nextlevelduration-currentduration)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration +" "+ I18n.get("eoarmors.abilities.info.seconds")));
								if(!(Ability.FROST.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 2)//POISON
					{
						float chance = (float) (1.0 / (ConfigHolder.SERVER.poisonchance.get()))*100;
						float currentduration = (Ability.POISON.getLevel(nbt) + Ability.POISON.getLevel(nbt)*4)/2;
						float nextlevelduration = (Ability.POISON.getLevel(nbt)+1 + (Ability.POISON.getLevel(nbt)+1)*4)/2;
						int c = (int) chance;
						
						if (!(Ability.POISON.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + nextlevelduration));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration + " " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + (nextlevelduration-currentduration)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration +" "+ I18n.get("eoarmors.abilities.info.seconds")));
								if(!(Ability.POISON.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 3)//INNATE
					{
						float chance = (float) ((1.0 / (ConfigHolder.SERVER.innatechance.get()))*100);
						float currentduration = (Ability.INNATE.getLevel(nbt) + Ability.INNATE.getLevel(nbt)*4)/3;
						float nextlevelduration = (Ability.INNATE.getLevel(nbt)+1 + (Ability.INNATE.getLevel(nbt)+1)*4)/3;
						float currentbleedingspeed = (Ability.INNATE.getLevel(nbt));
						float nextlevelbleedingspeed = (Ability.INNATE.getLevel(nbt)+1);
						int c = (int) chance;
						
						if (!(Ability.INNATE.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + nextlevelduration));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.bleedingspeed")+": 0 "+ TextFormatting.GREEN + "+" + nextlevelbleedingspeed));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration + " " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + (nextlevelduration-currentduration)));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.bleedingspeed")+": "+ currentbleedingspeed + " " + TextFormatting.GREEN + "+" + (nextlevelbleedingspeed-currentbleedingspeed)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration +" "+ I18n.get("eoarmors.abilities.info.seconds")));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.bleedingspeed")+": " + currentbleedingspeed));
								if(!(Ability.INNATE.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 4)//BOMBASTIC
					{
						float chance = (float) ((1.0 / (ConfigHolder.SERVER.bombasticchance.get()))*100);
						float currentexplosionintensity = (Ability.BOMBASTIC.getLevel(nbt));
						float nextlevelexplosionintensity = (Ability.BOMBASTIC.getLevel(nbt)+1);
						int c = (int) chance;
						
						if (!(Ability.BOMBASTIC.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.explosionintensity")+": 0 "+ TextFormatting.GREEN + "+" + nextlevelexplosionintensity));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.explosionintensity")+": "+ currentexplosionintensity + " " + TextFormatting.GREEN + "+" + (nextlevelexplosionintensity-currentexplosionintensity)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.explosionintensity")+": "+ currentexplosionintensity));
								if(!(Ability.BOMBASTIC.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 5)//CRITICAL_POINT
					{
						float chance = (float) ((1.0 / (ConfigHolder.SERVER.criticalpointchance.get()))*100);
						float currentdamage = (Ability.CRITICAL_POINT.getLevel(nbt)*17);
						float nextleveldamage = ((Ability.CRITICAL_POINT.getLevel(nbt)+1)*17);
						int c = (int) chance;
						
						if (!(Ability.CRITICAL_POINT.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.healthpercentage")+": %0"+ TextFormatting.GREEN + " + %" + nextleveldamage));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.healthpercentage")+": %"+ currentdamage + " " + TextFormatting.GREEN + "+ %" + (nextleveldamage-currentdamage)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.healthpercentage")+": %"+ currentdamage));
								if(!(Ability.CRITICAL_POINT.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 6)//ILLUMINATION
					{
						if (!(Ability.ILLUMINATION.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration") + ": 0 " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + 5.0));
							}
						}
						else
						{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration") + ": " + 5.0 + " " + I18n.get("eoarmors.abilities.info.seconds")));
						}
						if(!(Ability.ILLUMINATION.canUpgradeLevel(nbt)) && (!(buttons[i].active)))
								list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max") + " " + I18n.get("eoarmors.misc.level")));
					}
					if (i == 7)//ETHEREAL
					{
						float currentrepair = (Ability.ETHEREAL.getLevel(nbt)*2);
						float nextlevelrepair = ((Ability.ETHEREAL.getLevel(nbt)+1)*2);
						
						if (!(Ability.ETHEREAL.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.durability")+": 0" + TextFormatting.GREEN + " +" + 2.0));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.durability")+": "+ currentrepair + " " + TextFormatting.GREEN + "+" + (nextlevelrepair-currentrepair)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.durability")+": "+ currentrepair));
								if(!(Ability.ETHEREAL.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 8)//BLOODTHIRST
					{
						float currentpercentage =(float) (Ability.BLOODTHIRST.getLevel(nbt) * 12);
						float nextlevelpercentage =(float) ((Ability.BLOODTHIRST.getLevel(nbt)+1) * 12);
						
						if (!(Ability.BLOODTHIRST.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
							list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.damagepercentage")+": %0"+ TextFormatting.GREEN + " + %" + nextlevelpercentage));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.damagepercentage")+": %"+ currentpercentage + " " + TextFormatting.GREEN + "+ %" + (nextlevelpercentage-currentpercentage)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.damagepercentage")+": %"+ currentpercentage));
								if(!(Ability.BLOODTHIRST.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
				}
				else if (EAUtils.canEnhanceArmor(stack.getItem()))
				{
					if (i == 0)//MOLTEN
					{
						float chance = (float) (1.0 / (ConfigHolder.SERVER.moltenchance.get()))*100;
						float currentduration = (Ability.MOLTEN.getLevel(nbt) + Ability.MOLTEN.getLevel(nbt)*5)/4;
						float nextlevelduration = (Ability.MOLTEN.getLevel(nbt)+1 + (Ability.MOLTEN.getLevel(nbt)+1)*5)/4;
						int c = (int) chance;
						
						if (!(Ability.MOLTEN.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + nextlevelduration));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration + " " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + (nextlevelduration-currentduration)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration +" "+ I18n.get("eoarmors.abilities.info.seconds")));
								if(!(Ability.MOLTEN.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 1)//FROZEN
					{
						float chance = (float) (1.0 / (ConfigHolder.SERVER.frozenchance.get()))*100;
						float currentduration = (Ability.FROZEN.getLevel(nbt) + Ability.FROZEN.getLevel(nbt)*5)/6;
						float nextlevelduration = (Ability.FROZEN.getLevel(nbt)+1 + (Ability.FROZEN.getLevel(nbt)+1)*5)/6;
						int c = (int) chance;
						
						if (!(Ability.FROZEN.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + nextlevelduration));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration + " " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + (nextlevelduration-currentduration)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration +" "+ I18n.get("eoarmors.abilities.info.seconds")));
								if(!(Ability.FROZEN.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 2)//TOXIC
					{
						float chance = (float) (1.0 / (ConfigHolder.SERVER.toxicchance.get()))*100;
						float currentduration = (Ability.TOXIC.getLevel(nbt) + Ability.TOXIC.getLevel(nbt)*4)/4;
						float nextlevelduration = (Ability.TOXIC.getLevel(nbt)+1 + (Ability.TOXIC.getLevel(nbt)+1)*4)/4;
						int c = (int) chance;
						
						if (!(Ability.TOXIC.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + nextlevelduration));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration + " " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + (nextlevelduration-currentduration)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration +" "+ I18n.get("eoarmors.abilities.info.seconds")));
								if(!(Ability.TOXIC.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
					if (i == 3)//BEASTIAL
					{
						if (!(Ability.BEASTIAL.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + 7.0));
							}
						}
						else
						{
							list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration") + ": " + 7.0 + " " + I18n.get("eoarmors.abilities.info.seconds")));
						}
						if(!(Ability.BEASTIAL.canUpgradeLevel(nbt)) && (!(buttons[i].active)))
							list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
					}
					if (i == 4)//REMEDIAL
					{
						float heal = (float) Ability.REMEDIAL.getLevel(nbt);
						if (!(Ability.REMEDIAL.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								heal=1f;
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.heal_amount") + ": 0 " + TextFormatting.GREEN + "+" + heal));
							}
						}
						else
						{
							if (buttons[i].active)
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.heal_amount") +": "+ heal + TextFormatting.GREEN + " +" + 1.0));
							else
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.heal_amount") +": "+ heal));
						}
						if(!(Ability.REMEDIAL.canUpgradeLevel(nbt)) && (!(buttons[i].active)))
								list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
					}
					if (i == 5)//HARDENED
					{
						float chance = (float) ((1.0 / (ConfigHolder.SERVER.hardenedchance.get()))*100);
						
						if (!(Ability.HARDENED.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + chance));
							}
						}
						else
						{
							list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+ ": %" + chance));
						}
						if(!(Ability.HARDENED.canUpgradeLevel(nbt)) && (!(buttons[i].active)))
								list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
					}
					if (i == 6)//ADRENALINE
					{
						float chance = (float) (1.0 / (ConfigHolder.SERVER.adrenalinechance.get()))*100;
						float currentduration = (Ability.ADRENALINE.getLevel(nbt) + Ability.ADRENALINE.getLevel(nbt)*5)/3;
						float nextlevelduration = (Ability.ADRENALINE.getLevel(nbt)+1 + (Ability.ADRENALINE.getLevel(nbt)+1)*5)/3;
						int c = (int) chance;
						
						if (!(Ability.ADRENALINE.hasAbility(nbt)))
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance")+": %0"+ TextFormatting.GREEN + " + %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": 0 " + I18n.get("eoarmors.abilities.info.seconds")+ TextFormatting.GREEN + " +" + nextlevelduration));
							}
						}
						else
						{
							if (buttons[i].active)
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration + " " + I18n.get("eoarmors.abilities.info.seconds") + TextFormatting.GREEN + " +" + (nextlevelduration-currentduration)));
							}
							else
							{
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.chance") + ": %" + c));
								list.add(new StringTextComponent(I18n.get("eoarmors.abilities.info.duration")+": " + currentduration +" "+ I18n.get("eoarmors.abilities.info.seconds")));
								if(!(Ability.ADRENALINE.canUpgradeLevel(nbt)))
									list.add(new StringTextComponent(TextFormatting.RED + I18n.get("eoarmors.misc.max")+" " + I18n.get("eoarmors.misc.level")));
							}
						}
					}
				}
				
				int explevel = abilities.get(i).getExpLevel(nbt);
				if(!abilities.get(i).hasAbility(nbt))
				{
					list.add(new StringTextComponent(""));
					if(abilities.get(i).hasEnoughExp(player, nbt))
						list.add(new StringTextComponent(TextFormatting.DARK_GREEN + I18n.get("eoarmors.abilities.info.required_exp") + ": " + explevel));
					else
						list.add(new StringTextComponent(TextFormatting.DARK_RED + I18n.get("eoarmors.abilities.info.required_exp") + ": " + explevel));
				}
				else if(abilities.get(i).canUpgradeLevel(nbt))
				{
					if(Experience.getAbilityTokens(nbt) >= abilities.get(i).getTier())
						list.add(new StringTextComponent(TextFormatting.DARK_GREEN + I18n.get("eoarmors.abilities.info.required_token") + ": " + abilities.get(i).getTier()));
					else
						list.add(new StringTextComponent(TextFormatting.DARK_RED + I18n.get("eoarmors.abilities.info.required_token") + ": " + abilities.get(i).getTier()));
				}
				
				//TODO: CUT OFF TEXT???
				GuiUtils.drawHoveringText(matrixStack, list, mouseX + 3, mouseY + 3, minecraft.screen.width, minecraft.screen.height, 300, font);
			}
		}
	}


	//TODO: PAUSING? CHECK HERE!
	/*@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}*/
}