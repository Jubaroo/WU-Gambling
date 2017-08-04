package org.ausimus.wurmunlimited.mods.gambling.actions.slots;

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

import com.wurmonline.server.*;
import com.wurmonline.server.economy.MonetaryConstants;
import com.wurmonline.server.items.*;
import org.ausimus.wurmunlimited.mods.gambling.config.AusConstants;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;

import java.util.*;

public class SlotRoll implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {

    private static short actionID;
    private static ActionEntry actionEntry;

    public SlotRoll() {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Roll Slots", "Rolling", new int[]{});
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

    /**
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @return Fuck Warnings.
     **/
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source == target
                && source.getTemplateId() == AusConstants.GamblingTokenTemplateID
                && target.getAuxData() == AusConstants.GameModeSlots) {
            return Collections.singletonList(actionEntry);
        } else {
            return null;
        }
    }

    /**
     * @param act       the act.
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @param action    Action ID number.
     * @param counter   Timer shit.
     * @return Fuck Warnings.
     **/
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
        int value = 0;
        Random random = new Random();
        if (source == target
                && source.getTemplateId() == AusConstants.GamblingTokenTemplateID
                && target.getAuxData() == AusConstants.GameModeSlots) {
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
            performer.getCommunicator().sendNormalServerMessage("You bet a coin with a value of " + value + " Irons.");

            // Beginning of random number generation
            int val1 = random.nextInt(6) + 1;
            int val2 = random.nextInt(6) + 1;
            int val3 = random.nextInt(6) + 1;
            String valName1, valName2, valName3;
            valName1 = getValName(val1);
            valName2 = getValName(val2);
            valName3 = getValName(val3);
            performer.getCommunicator().sendNormalServerMessage(valName1 + ", " + valName2 + ", " + valName3);
            int userBet1 = value;
            if (val1 == val2 && val2 == val3) {
                int doubleReward = (userBet1 * 2);
                performer.getCommunicator().sendNormalServerMessage("Number of matches: 1");
                performer.getCommunicator().sendNormalServerMessage("You have won: " + doubleReward + ".");
            } else if (val1 == val2 || val2 == val3 || val1 == val3) {
                int tripleReward = (userBet1 * 3);
                performer.getCommunicator().sendNormalServerMessage("Number of matches: 3");
                performer.getCommunicator().sendNormalServerMessage("You have won: " + tripleReward + ".");
            } else {
                performer.getCommunicator().sendNormalServerMessage("Number of matches: 0");
                performer.getCommunicator().sendNormalServerMessage("You have won: 0");
            }
        } else {
            performer.getCommunicator().sendNormalServerMessage("Cant do that.");
        }
        return true;
    }

    private static String getValName(int val) {
        String valName = "leNull";
        switch (val) {
            case 1:
                valName = "Cherries";
                break;
            case 2:
                valName = "Oranges";
                break;
            case 3:
                valName = "Plums";
                break;
            case 4:
                valName = "Bells";
                break;
            case 5:
                valName = "Melons";
                break;
            case 6:
                valName = "Bars";
                break;
        }
        return valName;
    }
}