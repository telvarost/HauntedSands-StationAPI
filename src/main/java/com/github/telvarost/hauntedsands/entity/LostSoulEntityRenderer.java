package com.github.telvarost.hauntedsands.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class LostSoulEntityRenderer extends LivingEntityRenderer {
	private BipedEntityModel bipedModel = (BipedEntityModel)this.model;
	private BipedEntityModel armor1 = new BipedEntityModel(1.0F);
	private BipedEntityModel armor2 = new BipedEntityModel(0.5F);
	private static final String[] armorTextureNames = new String[]{"cloth", "chain", "iron", "diamond", "gold"};

	public LostSoulEntityRenderer() {
		super(new BipedEntityModel(0.0F), 0.5F);
	}

	@Override
	protected boolean bindTexture(LivingEntity livingEntity, int i, float f) {
		LostSoulEntity lostSoulEntity = (LostSoulEntity)livingEntity;
		ItemStack var4 = lostSoulEntity.armor[3 - i];
		if (var4 != null) {
			Item var5 = var4.getItem();
			if (var5 instanceof ArmorItem) {
				ArmorItem var6 = (ArmorItem)var5;
				this.bindTexture("/armor/" + armorTextureNames[var6.textureIndex] + "_" + (i == 2 ? 2 : 1) + ".png");
				BipedEntityModel var7 = i == 2 ? this.armor2 : this.armor1;
				var7.head.visible = i == 0;
				var7.hat.visible = i == 0;
				var7.body.visible = i == 1 || i == 2;
				var7.rightArm.visible = i == 1;
				var7.leftArm.visible = i == 1;
				var7.rightLeg.visible = i == 2 || i == 3;
				var7.leftLeg.visible = i == 2 || i == 3;
				this.setDecorationModel(var7);
				return true;
			}
		}

		return false;
	}

	@Override
	public void render(LivingEntity livingEntity, double d, double e, double f, float g, float h) {
		LostSoulEntity lostSoulEntity = (LostSoulEntity)livingEntity;
		ItemStack var10 = null; //lostSoulEntity.inventory.getSelectedItem();
		this.armor1.rightArmPose = this.armor2.rightArmPose = this.bipedModel.rightArmPose = var10 != null;
		this.armor1.sneaking = this.armor2.sneaking = this.bipedModel.sneaking = lostSoulEntity.isSneaking();
		double var11 = e - (double)lostSoulEntity.standingEyeHeight;
		renderInternal((LivingEntity)lostSoulEntity, d, var11, f, g, h);
		this.armor1.sneaking = this.armor2.sneaking = this.bipedModel.sneaking = false;
		this.armor1.rightArmPose = this.armor2.rightArmPose = this.bipedModel.rightArmPose = false;
	}

	//@Override
	public void renderInternal(LivingEntity livingEntity, double d, double e, double f, float g, float h) {
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		this.model.handSwingProgress = this.getHandSwingProgress(livingEntity, h);
		if (this.decorationModel != null) {
			this.decorationModel.handSwingProgress = this.model.handSwingProgress;
		}

		this.model.riding = livingEntity.hasVehicle();
		if (this.decorationModel != null) {
			this.decorationModel.riding = this.model.riding;
		}

		try {
			float var10 = livingEntity.lastBodyYaw + (livingEntity.bodyYaw - livingEntity.lastBodyYaw) * h;
			float var11 = livingEntity.prevYaw + (livingEntity.yaw - livingEntity.prevYaw) * h;
			float var12 = livingEntity.prevPitch + (livingEntity.pitch - livingEntity.prevPitch) * h;
			this.applyTranslation(livingEntity, d, e, f);
			float var13 = this.getHeadBob(livingEntity, h);
			this.applyHandSwingRotation(livingEntity, var13, var10, h);
			float var14 = 0.0625F;
			GL11.glEnable(32826);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			this.applyScale(livingEntity, h);

			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);

			float var15 = livingEntity.lastWalkAnimationSpeed + (livingEntity.walkAnimationSpeed - livingEntity.lastWalkAnimationSpeed) * h;
			float var16 = livingEntity.walkAnimationProgress - livingEntity.walkAnimationSpeed * (1.0F - h);
			if (var15 > 1.0F) {
				var15 = 1.0F;
			}

			this.bindDownloadedTexture(livingEntity.skinUrl, livingEntity.getTexture());
			GL11.glEnable(3008);
			this.model.animateModel(livingEntity, var16, var15, h);
			this.model.render(var16, var15, var13, var11 - var10, var12, var14);

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			for (int var17 = 0; var17 < 4; var17++) {
				if (this.bindTexture(livingEntity, var17, h)) {
					this.decorationModel.render(var16, var15, var13, var11 - var10, var12, var14);
					GL11.glDisable(3042);
					GL11.glEnable(3008);
				}
			}

			renderMore((LostSoulEntity) livingEntity, h);
			float var25 = livingEntity.getBrightnessAtEyes(h);
			int var18 = this.getOverlayColor(livingEntity, var25, h);
			if ((var18 >> 24 & 0xFF) > 0 || livingEntity.hurtTime > 0 || livingEntity.deathTime > 0) {
				GL11.glDisable(3553);
				GL11.glDisable(3008);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glDepthFunc(514);
				if (livingEntity.hurtTime > 0 || livingEntity.deathTime > 0) {
					GL11.glColor4f(var25, 0.0F, 0.0F, 0.4F);
					this.model.render(var16, var15, var13, var11 - var10, var12, var14);

					for (int var19 = 0; var19 < 4; var19++) {
						if (this.bindDecorationTexture(livingEntity, var19, h)) {
							GL11.glColor4f(var25, 0.0F, 0.0F, 0.4F);
							this.decorationModel.render(var16, var15, var13, var11 - var10, var12, var14);
						}
					}
				}

				if ((var18 >> 24 & 0xFF) > 0) {
					float var26 = (float)(var18 >> 16 & 0xFF) / 255.0F;
					float var20 = (float)(var18 >> 8 & 0xFF) / 255.0F;
					float var21 = (float)(var18 & 0xFF) / 255.0F;
					float var22 = (float)(var18 >> 24 & 0xFF) / 255.0F;
					GL11.glColor4f(var26, var20, var21, var22);
					this.model.render(var16, var15, var13, var11 - var10, var12, var14);

					for (int var23 = 0; var23 < 4; var23++) {
						if (this.bindDecorationTexture(livingEntity, var23, h)) {
							GL11.glColor4f(var26, var20, var21, var22);
							this.decorationModel.render(var16, var15, var13, var11 - var10, var12, var14);
						}
					}
				}

				GL11.glDepthFunc(515);
				GL11.glDisable(3042);
				GL11.glEnable(3008);
				GL11.glEnable(3553);
			}

			GL11.glDisable(32826);
		} catch (Exception var24) {
			var24.printStackTrace();
		}

		GL11.glEnable(2884);
		GL11.glPopMatrix();
		this.renderNameTag(livingEntity, d, e, f);
	}

	@Override
	protected void renderMore(LivingEntity livingEntity, float f) {
		LostSoulEntity lostSoulEntity = (LostSoulEntity)livingEntity;
		ItemStack var3 = lostSoulEntity.armor[3];
		if (var3 != null && var3.getItem().id < 256) {
			GL11.glPushMatrix();
			this.bipedModel.head.transform(0.0625F);
			if (BlockRenderManager.isSideLit(Block.BLOCKS[var3.itemId].getRenderType())) {
				float var4 = 0.625F;
				GL11.glTranslatef(0.0F, -0.25F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(var4, -var4, var4);
			}

			this.dispatcher.heldItemRenderer.renderItem(lostSoulEntity, var3);
			GL11.glPopMatrix();
		}

//		ItemStack var21 = lostSoulEntity.inventory.getSelectedItem();
//		if (var21 != null) {
//			GL11.glPushMatrix();
//			this.bipedModel.rightArm.transform(0.0625F);
//			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
//			if (lostSoulEntity.fishHook != null) {
//				var21 = new ItemStack(Item.STICK);
//			}
//
//			if (var21.itemId < 256 && BlockRenderManager.isSideLit(Block.BLOCKS[var21.itemId].getRenderType())) {
//				float var24 = 0.5F;
//				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
//				var24 *= 0.75F;
//				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
//				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
//				GL11.glScalef(var24, -var24, var24);
//			} else if (Item.ITEMS[var21.itemId].isHandheld()) {
//				float var22 = 0.625F;
//				if (Item.ITEMS[var21.itemId].isHandheldRod()) {
//					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
//					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
//				}
//
//				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
//				GL11.glScalef(var22, -var22, var22);
//				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
//				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
//			} else {
//				float var23 = 0.375F;
//				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
//				GL11.glScalef(var23, var23, var23);
//				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
//				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
//				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
//			}
//
//			this.dispatcher.heldItemRenderer.renderItem(lostSoulEntity, var21);
//			GL11.glPopMatrix();
//		}
	}

	protected void applyScale(LostSoulEntity lostSoulEntity, float f) {
		float var3 = 0.9375F;
		GL11.glScalef(var3, var3, var3);
	}

	public void renderHand() {
		this.bipedModel.handSwingProgress = 0.0F;
		this.bipedModel.setAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		this.bipedModel.rightArm.render(0.0625F);
	}
}
