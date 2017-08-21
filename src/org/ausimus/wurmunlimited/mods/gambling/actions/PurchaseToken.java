package org.ausimus.wurmunlimited.mods.gambling.actions;

/*
     ___          ___          ___                     ___          ___          ___
    /\  \        /\__\        /\  \         ___       /\__\        /\__\        /\  \
   /::\  \      /:/  /       /::\  \       /\  \     /::|  |      /:/  /       /::\  \
  /:/\:\  \    /:/  /       /:/\ \  \      \:\  \   /:|:|  |     /:/  /       /:/\ \  \
 /::\~\:\  \  /:/  /  ___  _\:\~\ \  \     /::\__\ /:/|:|__|__  /:/  /  ___  _\:\~\ \  \
/:/\:\ \:\__\/:/__/  /\__\/\ \:\ \ \__\ __/:/\/__//:/ |::::\__\/:/__/  /\__\/\ \:\ \ \__\
\/__\:\/:/  /\:\  \ /:/  /\:\ \:\ \/__//\/:/  /   \/__/~~/:/  /\:\  \ /:/  /\:\ \:\ \/__/
     \::/  /  \:\  /:/  /  \:\ \:\__\  \::/__/          /:/  /  \:\  /:/  /  \:\ \:\__\
     /:/  /    \:\/:/  /    \:\/:/  /   \:\__\         /:/  /    \:\/:/  /    \:\/:/  /
    /:/  /      \::/  /      \::/  /     \/__/        /:/  /      \::/  /      \::/  /
    \/__/        \/__/        \/__/                   \/__/        \/__/        \/__/

*/

import com.wurmonline.server.FailedException;
import com.wurmonline.server.Items;
import com.wurmonline.server.economy.MonetaryConstants;
import com.wurmonline.server.items.*;
import org.ausimus.wurmunlimited.mods.gambling.Initiator;
import org.ausimus.wurmunlimited.mods.gambling.config.AusConstants;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;

import java.util.Collections;
import java.util.List;

public class PurchaseToken implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
    private static short actionID;
    private static ActionEntry actionEntry;

    PurchaseToken() {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Purchase Token", "Purchasing", new int[]{});
        ModActions.registerAction(actionEntry);
    }

    @Override
    public BehaviourProvider getBehaviourProvider() {
        return this;
    }

    @Override
    public ActionPerformer getActionPerformer() {
        return this;
    }

    @Override
    public short getActionId() {
        return actionID;
    }

    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source == target && source.isCoin()) {
            return Collections.singletonList(actionEntry);
        } else {
            return null;
        }
    }

    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
        int value = 0;
        if (source == target && source.isCoin()) {
            try {
                // Designate coin values
                if (source.getTemplateId() == ItemList.coinIron) {
                    value = MonetaryConstants.COIN_IRON;
                }
                if (source.getTemplateId() == ItemList.coinIronFive) {
                    value = MonetaryConstants.COIN_IRON * 5;
                }
                if (source.getTemplateId() == ItemList.coinIronTwenty) {
                    value = MonetaryConstants.COIN_IRON * 20;
                }
                if (source.getTemplateId() == ItemList.coinCopper) {
                    value = MonetaryConstants.COIN_COPPER;
                }
                if (source.getTemplateId() == ItemList.coinCopperFive) {
                    value = MonetaryConstants.COIN_COPPER * 5;
                }
                if (source.getTemplateId() == ItemList.coinCopperTwenty) {
                    value = MonetaryConstants.COIN_COPPER * 20;
                }
                if (source.getTemplateId() == ItemList.coinSilver) {
                    value = MonetaryConstants.COIN_SILVER;
                }
                if (source.getTemplateId() == ItemList.coinSilverFive) {
                    value = MonetaryConstants.COIN_SILVER * 5;
                }
                if (source.getTemplateId() == ItemList.coinSilverTwenty) {
                    value = MonetaryConstants.COIN_SILVER * 20;
                }
                if (source.getTemplateId() == ItemList.coinGold) {
                    value = MonetaryConstants.COIN_GOLD;
                }
                if (source.getTemplateId() == ItemList.coinGoldFive) {
                    value = MonetaryConstants.COIN_GOLD * 5;
                }
                if (source.getTemplateId() == ItemList.coinGoldTwenty) {
                    value = MonetaryConstants.COIN_GOLD * 20;
                }

                // Create the item and set its data for value.
                Item x = ItemFactory.createItem(AusConstants.GamblingTokenTemplateID, source.getQualityLevel(), performer.getName());
                performer.getInventory().insertItem(x);
                x.setData1(value);
                // Update the name and set the material.
                x.setName(x.getName() + "[" + value + "]");
                x.setMaterial(source.getMaterial());
                Items.destroyItem(target.getWurmId());
                performer.getCommunicator().sendNormalServerMessage("Purchased a token with a value of " + value + ".");
            } catch (FailedException | NoSuchTemplateException ex) {
                ex.printStackTrace();
                if (AusConstants.InDepthLogging) {
                    Initiator.WriteLog(String.valueOf(ex));
                }
            }
        } else {
            performer.getCommunicator().sendNormalServerMessage("Can't do that.");
        }
        return true;
    }
}